package br.com.nannygo.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarUsuarioActivity extends AppCompatActivity {

    Integer id_usuario;
    EditText edit_nome, edit_idade, edit_telefone, edit_email;

    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_nome = (EditText) findViewById(R.id.edit_nome);
        edit_idade = (EditText) findViewById(R.id.edit_idade);
        edit_telefone = (EditText) findViewById(R.id.edit_telefone);
        edit_email = (EditText) findViewById(R.id.edit_email);


        Intent intent = getIntent();

        /*if (intent != null) {
            id_usuario = intent.getIntExtra("id_usuario", 0);

            buscarContatoBanco();

        }

        btn_salvar =(Button) findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEdicao();
            }
        });



    }

    private void salvarEdicao(){

        ContentValues valores = new ContentValues();
        valores.put("Nome", edit_nome.getText().toString());
        valores.put("Idade", edit_idade.getText().toString());
        valores.put("Telefone", edit_telefone.getText().toString());
        valores.put("Email", edit_email.getText().toString());

        db.nannygo("tbl_usuarios", valores, "_id = ?", new String[]{
                id_usuario.toString()
        });

        Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, AreaUsuarioActivity.class));

    }

    private class buscarContatoBanco extends AsyncTask<Void, Void, Void> {

        String retornoJson;

        @Override
        protected Void doInBackground(Void... params) {
            String href = getResources().getString(R.string.linkLocal);
            retornoJson = HttpConnection.get(link);
            return null;
        }

        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from tbl_usuarios where _id = ?;" , new String[] {id_usuario.toString()});

        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            String nomeContato = cursor.getString(1); //coluna nome
            String telefoneContato = cursor.getString(2); //coluna contato
            String emailContato = cursor.getString(3); //coluna email

            edit_nome.setText(nomeUsuario);
            edit_idade.setText(idadeUsuario);
            edit_telefone.setText(telefoneUsuario);
            edit_email.setText(emailUsuario);

            cursor.close();

        }*/
    }

    }


