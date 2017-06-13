package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class TransacoesEsperaActivity extends AppCompatActivity
{
    static List<Transacao> lstTransacoesEspera = new ArrayList<>();
    ListView list_view_transacoes_espera;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes_espera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        new PegarTransacoesEsperaTask().execute();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        configurarAdapter();
    }

    //Configura a lista de transações
    private void configurarAdapter()
    {
        list_view_transacoes_espera = (ListView) findViewById(R.id.list_view_transacoes_espera);
        TransacaoEsperaAdapter adapter = new TransacaoEsperaAdapter(this, R.layout.list_view_item_transacao_espera, lstTransacoesEspera);
        list_view_transacoes_espera.setAdapter(adapter);
    }


    private class PegarTransacoesEsperaTask extends AsyncTask<Void, Void, Void>
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
            String link = String.format("%sconfigurarListaTransacoesEspera.php?id_usuario=%s", href, UsuarioFinal.getIdUsuario());
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
                    //Alert Dialog caso o acesso a lista de transações não foi bem sucedido
                    new AlertDialog.Builder(context)
                            .setTitle("Erro")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    startActivity(new Intent(context, MenuBabaActivity.class));
                                }
                            })
                            .setIcon(android.R.drawable.ic_delete)
                            .setMessage("Houve um erro em acessar a lista de transações. Tente novamente.")
                            .show();
                } else
                {
                    //Cria e preenche a lista de transações com os dados JSON do banco de dados
                    lstTransacoesEspera = gson.fromJson(retornoJson, new TypeToken<List<Transacao>>()
                    {
                    }.getType());
                    configurarAdapter();

                    //Preenche o intent para abrir a activity de detalhe da posição selecionada
                    list_view_transacoes_espera.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                        {
                            Intent intent = new Intent(context, DetalhesTransacaoEsperaActivity.class);
                            intent.putExtra("valor", lstTransacoesEspera.get(position).getValor());
                            intent.putExtra("idTransacao", lstTransacoesEspera.get(position).getIdTransacao());
                            intent.putExtra("idUsuario", lstTransacoesEspera.get(position).getIdUsuario());
                            intent.putExtra("idBaba", lstTransacoesEspera.get(position).getIdBaba());
                            intent.putExtra("dataTransacao", lstTransacoesEspera.get(position).getDataTransacao());
                            intent.putExtra("dataServico", lstTransacoesEspera.get(position).getDataServico());
                            intent.putExtra("statusAprovado", lstTransacoesEspera.get(position).getStatusAprovado());
                            intent.putExtra("metodoPagamento", lstTransacoesEspera.get(position).getMetodoPagamento());
                            intent.putExtra("nome", lstTransacoesEspera.get(position).getNome());
                            intent.putExtra("horaInicio", lstTransacoesEspera.get(position).getHoraInicio());
                            intent.putExtra("qntdHoras", lstTransacoesEspera.get(position).getQntdHoras());
                            intent.putExtra("logradouro", lstTransacoesEspera.get(position).getLogradouro());
                            intent.putExtra("cidade", lstTransacoesEspera.get(position).getCidade());
                            intent.putExtra("estado", lstTransacoesEspera.get(position).getEstado());
                            startActivity(intent);
                        }
                    });
                }
            }
        }

    }

}
