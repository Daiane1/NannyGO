package br.com.nannygo.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.view.View.GONE;

public class MenuBabaActivity extends AppCompatActivity
{
    TextView text_view_nome;
    ImageView img_user;
    Button btn_disponivel, btn_indisponivel, btn_perfil, btn_historico, btn_espera;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        pegarObjetosView();
        formatarNome();
        configurarBotaoFlutuanteLogout();
        configurarBotaoRegistro();
        inserirImagem();
    }

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
                    .into(img_user);
        } else if (UsuarioFinal.getSexo().equals("M"))
        {
            Picasso.with(context)
                    .load(link)
                    .error(R.drawable.babym)
                    .placeholder(R.drawable.babym)
                    .into(img_user);
        }
    }


    private void configurarBotaoRegistro()
    {
        //Muda a visibilidade dos botões caso o usuário estiver cadastrado como babá
        if (UsuarioFinal.getStatusBaba().equals("1"))
        {
            btn_disponivel.setVisibility(GONE);
            btn_indisponivel.setVisibility(View.VISIBLE);

        } else
        {
            btn_disponivel.setVisibility(View.VISIBLE);
            btn_indisponivel.setVisibility(GONE);
            btn_espera.setVisibility(GONE);
            btn_perfil.setVisibility(GONE);
            btn_historico.setVisibility(GONE);
        }
    }

    //Pega os objetos do arquivo XML
    private void pegarObjetosView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        img_user = (ImageView) findViewById(R.id.img_user);
        btn_disponivel = (Button) findViewById(R.id.btn_disponivel);
        btn_indisponivel = (Button) findViewById(R.id.btn_indisponivel);
        btn_historico = (Button) findViewById(R.id.btn_historico);
        btn_espera = (Button) findViewById(R.id.btn_espera);
        btn_perfil = (Button) findViewById(R.id.btn_perfil);
    }

    //Formata a string de nome para exibição de apenas o primeiro e o último nome
    private void formatarNome()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        String nome = UsuarioFinal.getNome();
        String nomeFormatado[] = nome.split(" ");
        int len = nomeFormatado.length - 1;
        if (len > 1)
        {
            text_view_nome.setText(String.format("%s %s", nomeFormatado[0], nomeFormatado[len]));
        } else
        {
            text_view_nome.setText(nome);
        }
    }


    public void abrirTelaRegistroBaba(View view)
    {
        startActivity(new Intent(this, RegistroBabaActivity.class));
    }

    //Codigo desenvolvido para o funcionamento do botão "Logout"
    private void configurarBotaoFlutuanteLogout()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.logout);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mostrarAlertaLogout();
            }
        });

    }

    private void mostrarAlertaLogout()
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sair")
                .setMessage("Deseja mesmo sair?")
                .setIcon(R.drawable.ic_directions_run_black_24dp)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent intent = new Intent(context, MainActivity.class);
                        UsuarioFinal.logout();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", null);
        alertDialog.show();
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
        }).setNegativeButton("Não", null).show().setIcon(android.R.drawable.ic_delete);

    }

    public void abrirTelaPerfil(View view)
    {
        startActivity(new Intent(context, AreaBabaActivity.class));
    }

    public void abrirTelaTransacoesEspera(View view)
    {
        startActivity(new Intent(context, TransacoesEsperaActivity.class));
    }

    public void abrirTelaHistoricoBaba(View view)
    {
        startActivity(new Intent(context, HistoricoBabaActivity.class));
    }

    private class RemoverBabaTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sremoverBaba.php?id_usuario=%s",
                    href,
                    UsuarioFinal.getIdUsuario());
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            UsuarioFinal.setStatusBaba("0");
            startActivity(new Intent(context, MenuBabaActivity.class));
        }
    }
}
