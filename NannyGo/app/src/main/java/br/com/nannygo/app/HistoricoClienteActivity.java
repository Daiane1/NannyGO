package br.com.nannygo.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClienteActivity extends AppCompatActivity {

    ListView list_view_baba;
    static List<Transacao> lstHistorico = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_baba = (ListView) findViewById(R.id.list_view_babas);

        new ConfigurarHistoricoClienteTask().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter() {
        list_view_baba = (ListView) findViewById(R.id.list_view_baba);
        TransacaoEsperaAdapter adapter = new TransacaoEsperaAdapter(this, R.layout.list_view_historico_cliente, lstHistorico);
        list_view_baba.setAdapter(adapter);
    }

    private class ConfigurarHistoricoClienteTask extends AsyncTask<Void, Void, Void> {

        String retornoJson;
        ProgressDialog dialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Aguarde");
            dialog.setMessage("O app está carregando a lista de transações!");
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sconfigurarHistoricoCliente.php?id_transacao=%s", href, Transacao.getIdTransacao());
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();
            dialog.dismiss();
            if (retornoJson != null) {
                if (retornoJson.isEmpty() || retornoJson == null) {
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
                } else {
                    Log.d("json", retornoJson);
                    lstHistorico = gson.fromJson(retornoJson, new TypeToken<List<Transacao>>() {
                    }.getType());
                    configurarAdapter();
                }
            }
        }
    }
}

