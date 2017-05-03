package br.com.nannygo.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.GONE;

public class AreaBabaActivity extends AppCompatActivity
{
    TextView text_view_nome, text_view_sexo, text_view_telefone, text_view_email, text_view_idade;
    ImageView img_baba;
    Button btn_disponivel, btn_indisponivel;
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

        configurarBotaoRegistro();
    }

    private void configurarBotaoRegistro()
    {
        if(UsuarioFinal.getStatusBaba().equals("1"))
        {
            btn_disponivel.setVisibility(GONE);
            btn_indisponivel.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_disponivel.setVisibility(View.VISIBLE);
            btn_indisponivel.setVisibility(GONE);
        }
    }

    private void pegarObjetosView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_sexo = (TextView) findViewById(R.id.text_view_sexo);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        img_baba = (ImageView) findViewById(R.id.img_baba);
        btn_disponivel = (Button) findViewById(R.id.btn_disponivel);
        btn_indisponivel = (Button) findViewById(R.id.btn_indisponivel);
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

    public void removerRegistro(View view)
    {
        new AlertDialog.Builder(context).setTitle("Confirmação").setMessage("Deseja mesmo remover seu registro como babá?").setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                new RemoverBabaTask().execute();
            }
        }).setNegativeButton("Não", null).show();

    }

    private class RemoverBabaTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sremoverBaba.php?id_usuario=%s",
                    href,
                    UsuarioFinal.getIdUsuario());
            HttpConnection.get(link);
            Log.d("link", link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            UsuarioFinal.setStatusBaba("0");
            startActivity(new Intent(context, VerificacaoActivity.class));
        }
    }

}
