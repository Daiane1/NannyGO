package br.com.nannygo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AreaBabaActivity extends AppCompatActivity
{
    TextView text_view_nome, text_view_sexo, text_view_telefone, text_view_email, text_view_idade;
    ImageView img_baba;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }

    private void inserirCampos()
    {
        text_view_nome.setText(UsuarioFinal.getNome());
        if (UsuarioFinal.getSexo().equals("M"))
        {
            text_view_sexo.setText("Masculino");
        } else if (UsuarioFinal.getSexo().equals("F"))
        {
            text_view_sexo.setText("Feminino");
        }
        text_view_telefone.setText(UsuarioFinal.getTelefone());
        text_view_email.setText(UsuarioFinal.getEmail());
        text_view_idade.setText(UsuarioFinal.getIdade());

        //TODO: implementar foto babá
    }


    public void abrirTelaRegistroBaba(View view)
    {
        startActivity(new Intent(this, RegistroBabaActivity.class));
    }
}
