package br.com.nannygo.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FaleConoscoActivity extends AppCompatActivity {

    EditText edit_comentario;
    Button btn_enviar_comentario;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        edit_comentario = (EditText)findViewById(R.id.edit_comentario);
        btn_enviar_comentario = (Button)findViewById(R.id.btn_enviar_comentario);

        inserirCampos();
    }

    private void inserirCampos() {

        edit_comentario.setText(FaleConosco.getComentario());
    }


}
