package br.com.nannygo.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;


public class DetalhesBabaActivity extends AppCompatActivity {
    Intent intent;
    String retornoJson;
    Integer idUsuario;
    String preco;
    static TextView text_view_sexo, text_view_email, text_view_preco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        idUsuario = intent.getIntExtra("idusuario", -1);
        preco = intent.getStringExtra("preco");

        pegarUsuarioView();
        new PegarUsuarioTask().execute();
    }

    public void abrirTelaConfirmacao(View view) {
        startActivity(new Intent(this, ContratacaoActivity.class));
    }

    private void pegarUsuarioView()
    {
        text_view_sexo = (TextView) findViewById(R.id.text_view_sexo);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_preco = (TextView) findViewById(R.id.preco_hora_baba);
    }

    private class PegarUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/pegarUsuario.php?id_usuario=%s",
                    idUsuario);
            retornoJson = HttpConnection.get(link);
            Log.d("json", retornoJson);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(retornoJson, Usuario.class);

        }
    }
}
