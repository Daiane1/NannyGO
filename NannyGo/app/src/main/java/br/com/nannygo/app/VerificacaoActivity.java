package br.com.nannygo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class VerificacaoActivity extends AppCompatActivity {

    TextView text_view_nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mostrarPrimeiroNome();
    }

    private void mostrarPrimeiroNome()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        String nome = UsuarioFinal.getNome();
        String primeiroNome[] = nome.split(" ");
        text_view_nome.setText(primeiroNome[0]);
    }

    public void abrirTelaBabas(View view) {
        startActivity(new Intent(this, BabasActivity.class));
    }

    public void abrirTelaAreaBabas(View view) {
        startActivity(new Intent(this, AreaBabaActivity.class));
    }
}
