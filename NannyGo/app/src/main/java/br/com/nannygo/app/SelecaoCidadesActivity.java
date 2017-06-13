package br.com.nannygo.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SelecaoCidadesActivity extends AppCompatActivity
{
    ListView list_view_cidade;
    EditText edit_text_cidade;
    Context context;
    List<String> lstCidades = new ArrayList<>();
    List<Cidade> lstJson = new ArrayList<>();
    Gson gson = new Gson();
    ArrayAdapter<String> adapter;
    Intent intent;
    Intent intentRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_cidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        context = this;

        pegarView();
        intentRegistro = getIntent();

        //Verifica a página que o usuário veio, para após a seleção da cidade retornar a página original
        if (intentRegistro.getStringExtra("activity").equals("RegistroActivity"))
        {
            intent = new Intent(context, RegistroActivity.class);
        } else if (intentRegistro.getStringExtra("activity").equals("EditarUsuarioActivity"))
        {
            intent = new Intent(context, EditarUsuarioActivity.class);
        }

        preencherIntent();

        list_view_cidade.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Pega o id da cidade selecionada.
                //Ao realizar uma busca, a posição da cidade para de corresponder o id.
                String cidade = adapter.getItem(position);

                int i = 0;
                while (!cidade.equals(lstJson.get(i).getCidade()))
                {
                    i++;
                }

                intent.putExtra("cidade", cidade);
                intent.putExtra("idcidade", lstJson.get(i).getIdCidade());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new PreencherCidadeTask().execute();
    }

    //Preenche o Intent para retornar os dados à pagina original
    private void preencherIntent()
    {
        intent.putExtra("nome", intentRegistro.getStringExtra("nome"));
        intent.putExtra("login", intentRegistro.getStringExtra("login"));
        intent.putExtra("senha", intentRegistro.getStringExtra("senha"));
        intent.putExtra("sexo", intentRegistro.getStringExtra("sexo"));
        intent.putExtra("dtNasc", intentRegistro.getStringExtra("dtNasc"));
        intent.putExtra("telefone", intentRegistro.getStringExtra("telefone"));
        intent.putExtra("email", intentRegistro.getStringExtra("email"));
        intent.putExtra("logradouro", intentRegistro.getStringExtra("logradouro"));
    }

    //Pega os dados do arquivo XML
    private void pegarView()
    {
        list_view_cidade = (ListView) findViewById(R.id.list_view_cidade);
        edit_text_cidade = (EditText) findViewById(R.id.edit_text_cidade);
    }

    //Configura a busca da lista de cidades
    @NonNull
    private void configurarAdapter()
    {
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, lstCidades);
        list_view_cidade.setAdapter(adapter);

        edit_text_cidade.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private class PreencherCidadeTask extends AsyncTask<Void, Void, Void>
    {
        String retornoJson;

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%spreencherCidade.php", href);
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);


            if (retornoJson.isEmpty())
            {
                //Criação de dialog caso o login não foi bem sucedido.
                new AlertDialog.Builder(context)
                        .setTitle("Acesso Negado")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Login ou senha inválidos.")
                        .show();
            } else
            {

                preencherLista();
                configurarAdapter();
            }
        }

        //Método preenche a lista de cidades(String) pela lista json
        //para realização do filtro
        private void preencherLista()
        {
            lstJson = gson.fromJson(retornoJson, new TypeToken<List<Cidade>>()
            {
            }.getType());
            Integer len = lstJson.size();
            int i = 0;
            while (i < len - 1)
            {
                lstCidades.add(lstJson.get(i).getCidade());
                i++;
            }
        }


    }

}
