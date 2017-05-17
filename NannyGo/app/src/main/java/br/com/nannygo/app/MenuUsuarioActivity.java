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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static br.com.nannygo.app.R.id.img_user;

public class MenuUsuarioActivity extends AppCompatActivity {

    TextView text_view_nome;
    ImageView img_usuario;
    ListView lista_menu_usuario;
    List<String> lstMenuUsuario = new ArrayList<>();

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
        inserirCampos();
        configurarBotaoFlutuanteLogout();

       /* lista_menu_usuario = (ListView) findViewById(R.id.lista_menu_usuario);
        lstMenuUsuario.add("Meu Perfil");
        lstMenuUsuario.add("Histórico");
        lstMenuUsuario.add("Sair");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, lstMenuUsuario);
        lista_menu_usuario.setAdapter(adapter );*/
    }

    /* Codigo desenvolvido para pegar campos do banco */
    private void pegarObjetosView() {
        img_usuario = (ImageView) findViewById(img_user);
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);

    }

    /* Codigo desenvolvido para pegar inserir do banco */
    private void inserirCampos() {
        text_view_nome.setText(UsuarioFinal.getNome());

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
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", null)
                ;
        alertDialog.show();
    }

    /*public void sair(View view){
        mostrarAlertaLogout();
    }

    private void mostrarAlertaLogout() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sair")
                .setMessage("Deseja mesmo sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", null)
                ;
        alertDialog.show();
    }*/



}
