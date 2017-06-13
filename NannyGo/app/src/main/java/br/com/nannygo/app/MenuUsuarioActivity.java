package br.com.nannygo.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static br.com.nannygo.app.R.id.img_user;

public class MenuUsuarioActivity extends AppCompatActivity {

    TextView text_view_nome;
    ImageView img_usuario;


    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pegarObjetosView();
        formatarNome();
        configurarBotaoFlutuanteLogout();
        inserirImagem();

    }

    //Insere a imagem vinda do banco de dados na ImageView
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

    // Codigo desenvolvido para pegar campos do banco
    private void pegarObjetosView() {
        img_usuario = (ImageView) findViewById(img_user);
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);

    }

    //Formata a string do nome do usuário para exibição de apenas o primeiro e o último nome
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

    /* Codigo desenvolvido para Entrar no Perfil do Usuário */
    public void abrirTelaPerfil(View view){
        Intent intent = new Intent(context, AreaUsuarioActivity.class);
        startActivity(intent);
    }

    /* Codigo desenvolvido para Entrar no Histórico de Transições */
    public void abrirTelaHistorico(View view){
        Intent intent = new Intent(context, HistoricoClienteActivity.class);
        startActivity(intent);
    }

    /* Codigo desenvolvido para o funcionamento do botão "Logout" */
    private void configurarBotaoFlutuanteLogout() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.logout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlertaLogout();
            }
        });

    }

    private void mostrarAlertaLogout() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sair")
                .setMessage("Deseja mesmo sair?")
                .setIcon(R.drawable.ic_directions_run_black_24dp)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, MainActivity.class);
                        UsuarioFinal.logout();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", null)
                ;
        alertDialog.show();
    }
}
