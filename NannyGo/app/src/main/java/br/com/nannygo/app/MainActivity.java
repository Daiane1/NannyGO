package br.com.nannygo.app;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity
{

    Context context;
    EditText edit_text_login, edit_text_senha;
    String login, senha;

    TextView text_view_nome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        pegarView();
    }

    //Pega os dados do arquivo XML
    private void pegarView()
    {
        edit_text_login = (EditText) findViewById(R.id.edit_text_login);
        edit_text_senha = (EditText) findViewById(R.id.edit_text_senha);
    }

    public void abrirTelaRegistro(View view)
    {
        Intent intent = new Intent(context, RegistroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaBabas(View view)
    {
        login = edit_text_login.getText().toString();
        senha = edit_text_senha.getText().toString();

        new AutenticarUsuarioTask().execute();

    }

    public void abrirTelaSobre(View view)
    {
        startActivity(new Intent(context, FaleConoscoActivity.class));
    }

    //Notificação de Login - Uma aviso de login que é apresentado instantaneamente
    //na notificationbar quando o usuário loga.
    private void popup()
    {
        String texto;
        if (UsuarioFinal.getSexo().equals("F"))
        {
            texto = String.format("Bem vinda ao NannyGO %s", UsuarioFinal.getNome());
        } else
        {
            texto = String.format("Bem vindo ao NannyGO %s", UsuarioFinal.getNome());
        }

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("NannyGO")
                        .setContentText(texto);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }

    //  Criação de uma classe com atributos estáticos, para que os dados do usuário sejam utilizados
    //no aplicativo sejam iguais, uma vez que a classe implementada pelo Gson não implementa campos
    //estáticos.
    private void criarUsuarioFinal(Usuario usuario)
    {
        UsuarioFinal.setIdUsuario(usuario.getIdUsuario());
        UsuarioFinal.setNome(usuario.getNome());
        UsuarioFinal.setSexo(usuario.getSexo());
        UsuarioFinal.setTelefone(usuario.getTelefone());
        UsuarioFinal.setEmail(usuario.getEmail());
        UsuarioFinal.setLogin(usuario.getLogin());
        UsuarioFinal.setSenha(usuario.getSenha());
        UsuarioFinal.setDataNascimento(usuario.getDataNascimento());
        UsuarioFinal.setStatusBaba(usuario.getStatusBaba());
        UsuarioFinal.setCidade(usuario.getCidade());
        UsuarioFinal.setEstado(usuario.getEstado());
        UsuarioFinal.setUf(usuario.getUf());
        UsuarioFinal.setIdCidade(usuario.getIdCidade());
        UsuarioFinal.setLogradouro(usuario.getLogradouro());
    }

    private class AutenticarUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        String retornoJson;
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setTitle("Aguarde");
            dialog.setMessage("Tentando realizar seu login...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sautenticarUsuario.php?login=%s&senha=%s", href, login, senha);

            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();

            if (retornoJson == null)
            {
                //Criação de dialog caso o login não foi bem sucedido.
                new AlertDialog.Builder(context)
                        .setTitle("Erro de conexão")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Houve um erro ao tentar conectar com o servidor.\nVerifique se está com utilizando plano de dados ou Wi-fi.")
                        .show();
            } else if (retornoJson.isEmpty())
            {
                //Criação de dialog caso o login não foi bem sucedido.
                new AlertDialog.Builder(context)
                        .setTitle("Acesso Negado")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Login ou senha inválidos.")
                        .show();
            } else
            {
                Usuario usuario = gson.fromJson(retornoJson, Usuario.class);
                criarUsuarioFinal(usuario);
                popup();
                Intent intent = new Intent(context, VerificacaoActivity.class);
                startActivity(intent);
            }
        }
    }
}
