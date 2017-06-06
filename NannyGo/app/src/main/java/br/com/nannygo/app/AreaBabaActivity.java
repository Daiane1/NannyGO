package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AreaBabaActivity extends AppCompatActivity
{
    TextView text_view_nome, text_view_sexo, text_view_telefone, text_view_email, text_view_idade,
            text_view_cidade, text_view_estado, text_view_status, text_view_preco,
            text_view_hora_inicio, text_view_hora_fim, text_view_disponibilidade;
    ImageView img_baba;
    Context context;
    Baba baba;
    Intent intent;

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
        inserirImagem();

        new PegarDadosBabaTask().execute();
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
                    .into(img_baba);
        }
        else if (UsuarioFinal.getSexo().equals("M"))
        {
            Picasso.with(context)
                    .load(link)
                    .error(R.drawable.babym)
                    .placeholder(R.drawable.babym)
                    .into(img_baba);
        }
    }


    //Método pega os objetos do arquivo XML
    private void pegarObjetosView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_sexo = (TextView) findViewById(R.id.text_view_sexo);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        img_baba = (ImageView) findViewById(R.id.img_baba);
        text_view_cidade = (TextView) findViewById(R.id.text_view_cidade);
        text_view_estado = (TextView) findViewById(R.id.text_view_estado);
        text_view_status = (TextView) findViewById(R.id.text_view_status);
        text_view_preco = (TextView) findViewById(R.id.text_view_preco);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_hora_fim = (TextView) findViewById(R.id.text_view_hora_fim);
        text_view_disponibilidade = (TextView) findViewById(R.id.text_view_disponibilidade);
    }

    //Insere os dados do usuário nos TextView
    private void inserirCampos()
    {
        formatarNome();
        text_view_telefone.setText(UsuarioFinal.getTelefone());
        text_view_email.setText(UsuarioFinal.getEmail());
        text_view_idade.setText(UsuarioFinal.getIdade());
        text_view_cidade.setText(UsuarioFinal.getCidade());
        text_view_estado.setText(UsuarioFinal.getEstado());
        if (UsuarioFinal.getStatusBaba().equals("0"))
        {
            text_view_status.setTextColor(getResources().getColor(R.color.vermelho, null));
            text_view_status.setText("Indisponível");
        }
        else if (UsuarioFinal.getStatusBaba().equals("1"))
        {
            text_view_status.setTextColor(getResources().getColor(R.color.verde, null));
            text_view_status.setText("Disponível");
        }
        text_view_disponibilidade.setText(baba.getDiasDisponiveis());
        text_view_hora_inicio.setText(baba.getHoraInicio());
        text_view_hora_fim.setText(baba.getHoraFim());
        text_view_preco.setText(String.format("R$ %.2f/hora",Double.parseDouble(baba.getPreco())));

    }

    //Mostra apenas o primeiro e último nome do usuário
    private void formatarNome()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        String nome = UsuarioFinal.getNome();
        String nomeFormatado[] = nome.split(" ");
        int len = nomeFormatado.length - 1;
        if (len > 1)
        {
            text_view_nome.setText(String.format("%s %s", nomeFormatado[0], nomeFormatado[len]));
        }
        else
        {
            text_view_nome.setText(nome);
        }
    }

    public void abrirTelaRegistroBaba(View view)
    {
        startActivity(new Intent(this, RegistroBabaActivity.class));
    }

    public void abrirTelaEditarBaba(View view)
    {
        preencherIntent();
        startActivity(intent);
    }

    //Preenche o Intent com dados para a página de edição
    private void preencherIntent()
    {
        intent = new Intent(context, EditarBabaActivity.class);
        intent.putExtra("preco", baba.getPreco());
        intent.putExtra("horaInicio", baba.getHoraInicio());
        intent.putExtra("horaFim", baba.getHoraFim());
        intent.putExtra("diasDisponiveis", baba.getDiasDisponiveis());
    }


    //Tarefa assíncrona que pega os dados da babá no banco de dados
    private class PegarDadosBabaTask extends AsyncTask<Void, Void, Void>
    {
        String retornoJson;
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.setTitle("Aguarde");
            dialog.setMessage("O app está carregando seus detalhes!");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%spegarBaba.php?id_usuario=%s",
                    href,
                    UsuarioFinal.getIdUsuario()
            );
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            Gson gson = new Gson();
            baba = gson.fromJson(retornoJson, Baba.class);

            inserirCampos();
            dialog.dismiss();
        }
    }
}
