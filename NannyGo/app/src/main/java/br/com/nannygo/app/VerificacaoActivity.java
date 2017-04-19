package br.com.nannygo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class VerificacaoActivity extends AppCompatActivity {

    Intent intent;
    String jsonUsuario;
    TextView text_view_nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_view_nome = (TextView) findViewById(R.id.text_view_nome);

        String nome = UsuarioFinal.getNome();

        String primeiroNome[] = nome.split(" ");
        text_view_nome.setText(primeiroNome[0]);

    }

    public void abrirTelaBabas(View view) {
        intent = new Intent(this, BabasActivity.class);
        intent.putExtra("jsonUsuario", jsonUsuario);
        startActivity(intent);
    }

    public void abrirTelaAreaBabas(View view) {
        intent = new Intent(this, AreaBabaActivity.class);
        startActivity(intent);
    }
}
