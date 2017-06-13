package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class VerificacaoActivity extends AppCompatActivity
{

    TextView text_view_nome, text_view_bemvindo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        configurarBotaoFlutuante();
        mostrarPrimeiroNome();
        configurarBemVindo();
    }

    //Muda a mensagem de bem vindo de acordo com o sexo do usuário
    private void configurarBemVindo()
    {
        text_view_bemvindo = (TextView) findViewById(R.id.text_view_bemvindo);
        if (UsuarioFinal.getSexo().equals("F"))
        {
            text_view_bemvindo.setText("Bem Vinda");
        } else
        {
            text_view_bemvindo.setText("Bem Vindo");
        }
    }

    //Modifica a string para exibição de apenas o primeiro nome
    private void mostrarPrimeiroNome()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        String nome = UsuarioFinal.getNome();
        String primeiroNome[] = nome.split(" ");
        text_view_nome.setText(primeiroNome[0]);
    }

    public void abrirTelaBabas(View view)
    {
        startActivity(new Intent(this, BabasActivity.class));
    }

    public void abrirTelaAreaBabas(View view)
    {
        startActivity(new Intent(this, MenuBabaActivity.class));
    }

    private void configurarBotaoFlutuante()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(context, MenuUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
