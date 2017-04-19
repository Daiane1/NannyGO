package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity {

    Context context;
    EditText edit_text_login, edit_text_senha;
    String login, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        edit_text_login = (EditText) findViewById(R.id.edit_text_login);
        edit_text_senha = (EditText) findViewById(R.id.edit_text_senha);

    }

    public void abrirTelaRegistro(View view) {
        Intent intent = new Intent(context, RegistroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaBabas(View view) {
        login = edit_text_login.getText().toString();
        senha = edit_text_senha.getText().toString();

        new AutenticarUsuarioTask().execute();
    }

    private class AutenticarUsuarioTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        String retornoJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/autenticarUsuario.php?login=%s&senha=%s", login, senha);
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();

            if (retornoJson.isEmpty())
            {
                new AlertDialog.Builder(context)
                        .setTitle("Acesso Negado")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Login ou senha inválidos.")
                        .show();
            }
            else
            {
                Usuario usuario = gson.fromJson(retornoJson, Usuario.class);
                criarUsuarioFinal(usuario);
                Intent intent = new Intent(context, VerificacaoActivity.class);
                startActivity(intent);
            }
        }
    }

    private void criarUsuarioFinal(Usuario usuario) {
        UsuarioFinal.setNome(usuario.getNome());
        UsuarioFinal.setSexo(usuario.getSexo());
        UsuarioFinal.setTelefone(usuario.getTelefone());
        UsuarioFinal.setEmail(usuario.getEmail());
        UsuarioFinal.setLogin(usuario.getLogin());
        UsuarioFinal.setSenha(usuario.getSenha());
        UsuarioFinal.setDataNascimento(usuario.getDataNascimento());
    }
}
