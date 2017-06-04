package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class DetalhesBabaActivity extends AppCompatActivity {
    Intent intent;
    String retornoJson;
    Integer idUsuario, idBaba;
    String preco;
    static TextView text_view_nome, text_view_sexo, text_view_email, text_view_preco, text_view_telefone, text_view_idade, text_view_estado, text_view_cidade;
    ImageView img_baba;
    Usuario usuario;

    Button btn;
    RatingBar ratingBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        /*btn = (Button) findViewById(R.id.btnAvaliacao);
        ratingBar = (RatingBar) findViewById(R.id.idrating);*/

        intent = getIntent();
        idUsuario = intent.getIntExtra("idusuario", -1);
        preco = intent.getStringExtra("preco");
        idBaba = intent.getIntExtra("idbaba", -1);
        pegarUsuarioView();
        new PegarUsuarioTask().execute();
    }

    public void abrirTelaConfirmacao(View view) {
        Intent intent = new Intent(this, ContratacaoActivity.class);
        intent.putExtra("preco", preco);
        intent.putExtra("idbaba", idBaba);
        startActivity(intent);
    }

    //Enviar Avaliação sobre a babá
    public void enviarAvaliacao(View view){
        float ratingvalor = ratingBar.getRating();
        Toast.makeText(context, "Avaliado em: "+ratingvalor, Toast.LENGTH_SHORT).show();
    }

    private void pegarUsuarioView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_sexo = (TextView) findViewById(R.id.text_view_sexo);
        text_view_email = (TextView) findViewById(R.id.text_view_email);
        text_view_telefone = (TextView) findViewById(R.id.text_view_telefone);
        text_view_idade = (TextView) findViewById(R.id.text_view_idade);
        text_view_preco = (TextView) findViewById(R.id.text_view_preco);
        text_view_cidade = (TextView) findViewById(R.id.text_view_cidade);
        text_view_estado = (TextView) findViewById(R.id.text_view_estado);
        img_baba = (ImageView) findViewById(R.id.img_baba);

    }

    public void inserirCampos()
    {
        text_view_nome.setText(usuario.getNome());

        if (usuario.getSexo().equals("M"))
        {
            text_view_sexo.setText("Masculino");
        } else if (usuario.getSexo().equals("F"))
        {
            text_view_sexo.setText("Feminino");
        }
        text_view_telefone.setText(usuario.getTelefone());
        text_view_email.setText(usuario.getEmail());
        text_view_idade.setText(usuario.getIdade());
        text_view_cidade.setText(usuario.getCidade());
        text_view_estado.setText(usuario.getEstado());
        text_view_preco.setText(String.format("R$ %s/hora", preco));

        inserirImagem();

    }

    private void inserirImagem()
    {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%s/usuario/%s.jpg", href, usuario.getLogin());
            if (usuario.getSexo().equals("F"))
            {
                Picasso.with(context)
                        .load(link)
                        .error(R.drawable.babyf)
                        .placeholder(R.drawable.babym)
                        .into(img_baba);
            }
            else if (usuario.getSexo().equals("M"))
            {
                Picasso.with(context)
                        .load(link)
                        .error(R.drawable.babym)
                        .placeholder(R.drawable.babym)
                        .into(img_baba);
            }
    }

    private class PegarUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        String retornoJson;
        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%spegarUsuario.php?id_usuario=%s",
                    href,
                    idUsuario);
            retornoJson = HttpConnection.get(link);
            Log.d("json", retornoJson);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Gson gson = new Gson();
            usuario = gson.fromJson(retornoJson, Usuario.class);
            inserirCampos();

        }
    }

}
