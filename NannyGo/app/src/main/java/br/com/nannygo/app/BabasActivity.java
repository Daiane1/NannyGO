package br.com.nannygo.app;

import android.app.AlertDialog;
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

public class BabasActivity extends AppCompatActivity
{

    ListView list_view_babas;
    static List<Baba> lstBabas = new ArrayList<>();
    Context context;
    String retornoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        list_view_babas = (ListView) findViewById(R.id.list_view_babas);
        abrirDetalhesBaba();

        new ConfigurarListaBabasTask().execute();
    }

    private void abrirDetalhesBaba()
    {
        list_view_babas.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                startActivity(new Intent(context, DetalhesBabaActivity.class));
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter()
    {
        BabaAdapter adapter = new BabaAdapter(this, R.layout.list_view_item_babas, lstBabas);
        list_view_babas.setAdapter(adapter);
    }

    private class ConfigurarListaBabasTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            String link = "http://10.0.2.2/20171sem/NannyGO/configurarListaBaba.php";
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();

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
                        .setMessage("Houve um erro em acessar a lista de babás. Tente novamente.")
                        .show();
            } else
            {
                Log.d("json", retornoJson);
                lstBabas = gson.fromJson(retornoJson, new TypeToken<List<Baba>>()
                {
                }.getType());
                configurarAdapter();
            }
        }
    }
}
