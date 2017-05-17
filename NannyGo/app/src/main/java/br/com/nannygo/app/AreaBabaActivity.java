package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AreaBabaActivity extends AppCompatActivity
{
    TextView text_view_nome, text_view_sexo, text_view_telefone, text_view_email, text_view_idade, text_view_cidade, text_view_estado, text_view_status;
    ImageView img_baba;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        pegarObjetosView();
        inserirCampos();

    }


    private void pegarObjetosView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_sexo = (TextView) findViewById(R.id.text_view_sexo);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        img_baba = (ImageView) findViewById(R.id.img_baba);
        text_view_cidade = (TextView) findViewById(R.id.text_view_cidade);
        text_view_estado = (TextView) findViewById(R.id.text_view_estado);
        text_view_status = (TextView) findViewById(R.id.text_view_status);

    }

    private void inserirCampos()
    {
        formatarNome();
        text_view_telefone.setText(UsuarioFinal.getTelefone());
        text_view_email.setText(UsuarioFinal.getEmail());
        text_view_idade.setText(UsuarioFinal.getIdade());
        text_view_cidade.setText(UsuarioFinal.getCidade());
        text_view_estado.setText(UsuarioFinal.getEstado());
        if (UsuarioFinal.getStatusBaba().equals("0")){
            text_view_status.setText("Indisponível");
        }
        else if (UsuarioFinal.getStatusBaba().equals("1")){
            text_view_status.setText("Disponível");
        }


        //TODO: implementar foto babá
    }

    private void formatarNome()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        String nome = UsuarioFinal.getNome();
        String nomeFormatado[] = nome.split(" ");
        int len = nomeFormatado.length-1;
        if (len > 1)
        {
            text_view_nome.setText(String.format("%s %s", nomeFormatado[0], nomeFormatado[len]));
        }
        else
        {
            text_view_nome.setText(nome);
        }
    }

    public void abrirTelaRegistroBaba(View view)
    {
        startActivity(new Intent(this, RegistroBabaActivity.class));
    }
}
