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

import static br.com.nannygo.app.BabasActivity.lstBabas;

public class HistoricoClienteActivity extends AppCompatActivity {

    ListView list_view_babas;
    static List<Transacao> lstHistorico = new ArrayList<>();
    Context context;
    String retornoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_babas = (ListView) findViewById(R.id.list_view_babas);
        abrirDetalhesBaba();

        new ConfigurarListaBabasTask().execute();

    }

    private void abrirDetalhesBaba() {
        list_view_babas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DetalhesBabaActivity.class);
                intent.putExtra("nome", lstHistorico.get(position).getNome());
                intent.putExtra("idusuario", lstHistorico.get(position).getIdUsuario());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter() {
        TransacaoAdapter adapter = new TransacaoAdapter(this, R.layout.list_view_historico_cliente, lstHistorico);
        list_view_babas.setAdapter(adapter);
    }

    private class ConfigurarListaBabasTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setTitle("Aguarde");
            dialog.setMessage("O app está carregando o histórico de transação!");
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sconfigurarListaBaba.php?origem=%s", href, UsuarioFinal.getIdCidade());
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();
            dialog.dismiss();
            if (retornoJson != null) {
                if (retornoJson.isEmpty()) {
                    new AlertDialog.Builder(context)
                            .setTitle("Erro")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(context, AreaUsuarioActivity.class));
                                }
                            })
                            .setIcon(android.R.drawable.ic_delete)
                            .setMessage("Houve um erro em acessar o histórico de transação. Tente novamente.")
                            .show();
                } else {
                    Log.d("json", retornoJson);
                    lstBabas = gson.fromJson(retornoJson, new TypeToken<List<Baba>>() {
                    }.getType());
                    configurarAdapter();
                }
            }
        }

    }
}
