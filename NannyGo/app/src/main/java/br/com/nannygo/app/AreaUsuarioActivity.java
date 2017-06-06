package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static br.com.nannygo.app.R.id.img_user;

public class AreaUsuarioActivity extends AppCompatActivity {

    TextView text_view_nome, text_view_telefone, text_view_email, text_view_idade, text_view_cidade, text_view_estado, text_view_status, text_view_logradouro;
    ImageView img_usuario;
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pegarObjetosView();
        inserirCampos();
        inserirImagem();
    }

    //Método baixa a imagem do usuário no servidor Web e insere na ImageView
    private void inserirImagem()
    {
        String href = getResources().getString(R.string.linkLocal);
        String link = String.format("%s/usuario/%s.jpg", href, UsuarioFinal.getLogin());
        if (UsuarioFinal.getSexo().equals("F"))
        {
            Picasso.with(context)
                    .load(link)
                    .error(R.drawable.babyf)
                    .placeholder(R.drawable.babym)
                    .into(img_usuario);
        }
        else if (UsuarioFinal.getSexo().equals("M"))
        {
            Picasso.with(context)
                    .load(link)
                    .error(R.drawable.babym)
                    .placeholder(R.drawable.babym)
                    .into(img_usuario);
        }
    }

    //Pega os objetos do arquivo XML
    private void pegarObjetosView() {
        img_usuario = (ImageView) findViewById(img_user);
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
        text_view_cidade = (TextView) findViewById(R.id.text_view_cidade);
        text_view_estado = (TextView) findViewById(R.id.text_view_estado);
        text_view_status = (TextView) findViewById(R.id.text_view_status);
        text_view_logradouro = (TextView) findViewById(R.id.text_view_logradouro);
    }

    //Insere os dados do usuário nos TextView
    private void inserirCampos() {
        formatarNome();
        text_view_idade.setText(UsuarioFinal.getIdade());
        text_view_email.setText(UsuarioFinal.getEmail());
        text_view_telefone.setText(UsuarioFinal.getTelefone());
        text_view_cidade.setText(UsuarioFinal.getCidade());
        text_view_estado.setText(UsuarioFinal.getEstado());
        text_view_logradouro.setText(UsuarioFinal.getLogradouro());

        if (UsuarioFinal.getStatusBaba().equals("0")){
            text_view_status.setText("Indisponível");
        }
        else if (UsuarioFinal.getStatusBaba().equals("1")){
            text_view_status.setText("Disponível");
        }

    }

    //Mostra apenas o primeiro e último nome do usuário
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

    public void abrirTelaEditarUsuario(View view) {
        startActivity(new Intent(context, EditarUsuarioActivity.class));
    }
}