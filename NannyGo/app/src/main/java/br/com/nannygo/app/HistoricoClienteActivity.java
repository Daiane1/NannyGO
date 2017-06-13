package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClienteActivity extends AppCompatActivity
{

    static List<Transacao> lstHistorico = new ArrayList<>();
    ListView list_view_historico_cliente;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new ConfigurarHistoricoClienteTask().execute();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter()
    {
        list_view_historico_cliente = (ListView) findViewById(R.id.list_view_historico_cliente);

        TransacaoEsperaAdapter adapter = new TransacaoEsperaAdapter(this, R.layout.list_view_item_transacao_espera, lstHistorico);
        list_view_historico_cliente.setAdapter(adapter);

        list_view_historico_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Preenche o intent com os dados para abertura da página de detalhes
                Intent intent = new Intent(context, DetalhesHistoricoClienteActivity.class);
                intent.putExtra("valor", lstHistorico.get(position).getValor());
                intent.putExtra("idTransacao", lstHistorico.get(position).getIdTransacao());
                intent.putExtra("idUsuario", lstHistorico.get(position).getIdUsuario());
                intent.putExtra("idBaba", lstHistorico.get(position).getIdBaba());
                intent.putExtra("dataTransacao", lstHistorico.get(position).getDataTransacao());
                intent.putExtra("dataServico", lstHistorico.get(position).getDataServico());
                intent.putExtra("statusAprovado", lstHistorico.get(position).getStatusAprovado());
                intent.putExtra("metodoPagamento", lstHistorico.get(position).getMetodoPagamento());
                intent.putExtra("nome", lstHistorico.get(position).getNome());
                intent.putExtra("horaInicio", lstHistorico.get(position).getHoraInicio());
                intent.putExtra("qntdHoras", lstHistorico.get(position).getQntdHoras());
                startActivity(intent);

            }
        });
    }

    private class ConfigurarHistoricoClienteTask extends AsyncTask<Void, Void, Void>
    {

        String retornoJson;
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setTitle("Aguarde");
            dialog.setMessage("O app está carregando a lista de transações!");
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sconfigurarHistoricoCliente.php?id_usuario=%s", href, UsuarioFinal.getIdUsuario());
            retornoJson = HttpConnection.get(link);
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
                if (retornoJson.isEmpty() || retornoJson == null)
                {
                    new android.support.v7.app.AlertDialog.Builder(context)
                            .setTitle("Erro")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(context, AreaUsuarioActivity.class));
                                }
                            })
                            .setIcon(android.R.drawable.ic_delete)
                            .setMessage("Houve um erro em acessar a lista de transações. Tente novamente.")
                            .show();
                } else
                {
                    //Preenche a lista de transações com os dados JSON vindos do banco de dados
                    lstHistorico = gson.fromJson(retornoJson, new TypeToken<List<Transacao>>()
                    {
                    }.getType());
                    configurarAdapter();
                }
            }
        }
    }
}

