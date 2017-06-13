package br.com.nannygo.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class HistoricoBabaActivity extends AppCompatActivity
{

    static List<Transacao> lstHistoricoBaba = new ArrayList<>();
    ListView list_view_historico_baba;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        new ConfigurarListaHistoricoBabaTask().execute();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter()
    {
        list_view_historico_baba = (ListView) findViewById(R.id.list_view_historico_baba);

        TransacaoEsperaAdapter adapter = new TransacaoEsperaAdapter(context, R.layout.list_view_item_transacao_espera, lstHistoricoBaba);
        list_view_historico_baba.setAdapter(adapter);

        list_view_historico_baba.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Preenche o intent com os dados da transação para abertura da página de detalhes
                Intent intent = new Intent(context, DetalhesHistoricoBabaActivity.class);
                intent.putExtra("valor", lstHistoricoBaba.get(position).getValor());
                intent.putExtra("idTransacao", lstHistoricoBaba.get(position).getIdTransacao());
                intent.putExtra("idUsuario", lstHistoricoBaba.get(position).getIdUsuario());
                intent.putExtra("idBaba", lstHistoricoBaba.get(position).getIdBaba());
                intent.putExtra("dataTransacao", lstHistoricoBaba.get(position).getDataTransacao());
                intent.putExtra("dataServico", lstHistoricoBaba.get(position).getDataServico());
                intent.putExtra("statusAprovado", lstHistoricoBaba.get(position).getStatusAprovado());
                intent.putExtra("metodoPagamento", lstHistoricoBaba.get(position).getMetodoPagamento());
                intent.putExtra("nome", lstHistoricoBaba.get(position).getNome());
                intent.putExtra("horaInicio", lstHistoricoBaba.get(position).getHoraInicio());
                intent.putExtra("qntdHoras", lstHistoricoBaba.get(position).getQntdHoras());
                startActivity(intent);

            }
        });
    }

    private class ConfigurarListaHistoricoBabaTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog dialog = new ProgressDialog(context);
        String retornoJson;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            dialog.setTitle("Aguarde");
            dialog.setMessage("O app está carregando o seu histórico de transações!");
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sconfigurarHistoricoBaba.php?id_usuario=%s", href, UsuarioFinal.getIdUsuario());
            retornoJson = HttpConnection.get(link);
            Log.d("link", link);
            Log.d("json", retornoJson);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();
            dialog.dismiss();
            if (retornoJson != null)
            {
                if (retornoJson.isEmpty())
                {
                    //Alert Dialog caso o acesso a lista de babás não foi bem sucedido
                    new AlertDialog.Builder(context)
                            .setTitle("Erro")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(context, VerificacaoActivity.class));
                                }
                            })
                            .setIcon(android.R.drawable.ic_delete)
                            .setMessage("Houve um erro em acessar o histórico de transações. Tente novamente.")
                            .show();
                } else
                {
                    //Preenche a lista de babás com os dados JSON do banco de dados
                    lstHistoricoBaba = gson.fromJson(retornoJson, new TypeToken<List<Transacao>>()
                    {
                    }.getType());
                    configurarAdapter();
                }
            }
        }
    }
}

