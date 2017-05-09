package br.com.nannygo.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static br.com.nannygo.app.R.id.img_user;

public class AreaUsuarioActivity extends AppCompatActivity {

    TextView text_view_nome, text_view_telefone, text_view_email, text_view_idade;
    ImageView img_usuario;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*configurarBotaoFlutuanteBack();*/
        configurarBotaoFlutuanteLogout();
        pegarObjetosView();
        inserirCampos();
    }

    private void pegarObjetosView() {
        img_usuario = (ImageView) findViewById(img_user);
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
    }

    private void inserirCampos() {
        text_view_nome.setText(UsuarioFinal.getNome());
        text_view_idade.setText(UsuarioFinal.getIdade());
        text_view_email.setText(UsuarioFinal.getEmail());
        text_view_telefone.setText(UsuarioFinal.getTelefone());

        //TODO: implementar foto babá
    }

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
}