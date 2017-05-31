package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class FaleConoscoActivity extends AppCompatActivity {

    EditText edit_comentario;
    Context context;
    FloatingActionButton botao;
    String comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        edit_comentario = (EditText)findViewById(R.id.edit_comentario);

        inserirCampos();
    }

    private void inserirCampos() {

        edit_comentario.setText(FaleConosco.getComentario());
    }

    private void configurarBotaoConfirmar() {
        botao = (FloatingActionButton) findViewById(R.id.botao);
        botao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                inserirBanco();
            }
        });
    }

    private void inserirBanco() {
        pegarDados();

    }

    private void pegarDados() {
        comentario = edit_comentario.getText().toString();
    }

    private class RegistroUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog progress;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Aguarde", "Registrando...");
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/registroUsuario.php?comentario=%s",
                    comentario);
            Log.d("link", link);
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            startActivity(new Intent(context, VerificacaoActivity.class));
        }
    }

}
