package br.com.nannygo.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class RegistroActivity extends AppCompatActivity {
    EditText edit_text_nome, edit_text_login, edit_text_senha, edit_text_confirmar, edit_text_telefone, edit_text_email, edit_text_cidade, edit_text_logradouro;
    static TextView text_view_data_nascimento;
    RadioButton radio_masculino, radio_feminino;
    String nome, login, telefone, email, sexo, dataNascimentoBanco, senha, dataNascimento, idCidade, logradouro, imagem;
    FloatingActionButton fab;
    Context context;
    Intent intent;
    Intent intentCidade;
    ImageView img_upload;
    private static final int RESULT_LOAD_IMAGE = 1;

    Bitmap foto_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        encontrarObjetosView();
        configurarBotaoConfirmar();
        intent = getIntent();
        intentCidade = new Intent(context, SelecaoCidadesActivity.class);

        preencherCampos();

        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });
    }

    private void preencherCampos()
    {
        if (intent.getStringExtra("cidade") != null)
        {
            edit_text_cidade.setText(intent.getStringExtra("cidade"));
            edit_text_nome.setText(intent.getStringExtra("nome"));
            edit_text_login.setText(intent.getStringExtra("login"));
            edit_text_senha.setText(intent.getStringExtra("senha"));
            text_view_data_nascimento.setText(intent.getStringExtra("dtNasc"));
            edit_text_telefone.setText(intent.getStringExtra("telefone"));
            edit_text_email.setText(intent.getStringExtra("email"));
            edit_text_logradouro.setText(intent.getStringExtra("logradouro"));

            if (intent.getStringExtra("sexo").equals("F"))
            {
                radio_feminino.toggle();
            }
            else if (intent.getStringExtra("sexo").equals("M"))
            {
                radio_masculino.toggle();
            }
        }
    }

    private void configurarBotaoConfirmar()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inserirBanco();
            }
        });
    }

    private void inserirBanco()
    {
        pegarDados();

        verificarSexoSelecionado();
        formatarData();

        //Substitui todos ' ' para '_' para o funcionamento do link PhP
        nome = nome.replaceAll(" ", "_");
        logradouro = logradouro.replaceAll(" ", "_");

        idCidade = intent.getStringExtra("idcidade");

        new RegistroUsuarioTask().execute();
    }

    private void pegarDados()
    {
        nome = edit_text_nome.getText().toString();
        login = edit_text_login.getText().toString();
        senha = edit_text_senha.getText().toString();
        telefone = edit_text_telefone.getText().toString();
        email = edit_text_email.getText().toString();
        logradouro = edit_text_logradouro.getText().toString();
        dataNascimento = text_view_data_nascimento.getText().toString();
        //imagem = getStringImage(foto_perfil);
    }

    private void verificarSexoSelecionado()
    {
        if (radio_masculino.isChecked())
        {
            sexo = "M";
        }

        if (radio_feminino.isChecked())
        {
            sexo = "F";
        }
    }

    private void formatarData()
    {
        //Formata a data de 'dd/MM/yyyy' para 'yyyy-MM-dd'
        String data_nascimento[] = text_view_data_nascimento.getText().toString().split("-");
        dataNascimentoBanco = String.format("%s-%s-%s", data_nascimento[2], data_nascimento[1], data_nascimento[0]);
    }

    private void encontrarObjetosView()
    {
        edit_text_nome = (EditText) findViewById(R.id.edit_text_nome);
        edit_text_login = (EditText) findViewById(R.id.edit_text_login);
        edit_text_senha = (EditText) findViewById(R.id.edit_text_senha);
        edit_text_telefone = (EditText) findViewById(R.id.edit_text_telefone);
        edit_text_email = (EditText) findViewById(R.id.edit_text_email);
        radio_feminino = (RadioButton) findViewById(R.id.radio_feminino);
        radio_masculino = (RadioButton) findViewById(R.id.radio_masculino);
        edit_text_cidade = (EditText) findViewById(R.id.edit_text_cidade);
        text_view_data_nascimento = (TextView) findViewById(R.id.text_view_data_nascimento);
        text_view_data_nascimento.setKeyListener(null);
        edit_text_cidade = (EditText) findViewById(R.id.edit_text_cidade);
        edit_text_cidade.setKeyListener(null);
        edit_text_logradouro = (EditText) findViewById(R.id.edit_text_logradouro);
        img_upload = (ImageView)findViewById(R.id.img_upload);
    }

    public void abrirSelecaoData(View view)
    {
        DialogFragment cal = new RegistroActivity.DatePickerFragment();
        cal.show(getFragmentManager(), "calendario");
    }

    public void abrirSelecaoCidade(View view)
    {
        preencherIntent();
        startActivity(intentCidade);
    }

    private void preencherIntent()
    {
        nome = "";
        login = "";
        senha = "";
        sexo = "";
        verificarSexoSelecionado();
        dataNascimento = "";
        telefone = "";
        email = "";
        logradouro = "";

        pegarDados();

        intentCidade.putExtra("nome", nome);
        intentCidade.putExtra("login", login);
        intentCidade.putExtra("senha", senha);
        intentCidade.putExtra("sexo", sexo);
        intentCidade.putExtra("dtNasc", dataNascimento);
        intentCidade.putExtra("telefone", telefone);
        intentCidade.putExtra("email", email);
        intentCidade.putExtra("logradouro", logradouro);
    }

    private class RegistroUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog progress;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Aguarde", "Registrando...");
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/registroUsuario.php?nome=%s&login=%s&senha=%s&sexo=%s&telefone=%s&email=%s&data_nascimento=%s&cidade=%s&logradouro=%s&foto=%s",
                    nome, login, senha, sexo, telefone, email, dataNascimentoBanco, idCidade, logradouro, imagem);
            Log.d("link", link);
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            startActivity(new Intent(context, MainActivity.class));
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, ano, mes, dia);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            String dataSelecionada = String.format("%02d-%02d-%d", dayOfMonth, ++month, year);
            text_view_data_nascimento.setText(dataSelecionada);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){


            Uri imagem_selecionada = data.getData();

            try {

                foto_perfil = MediaStore.Images.Media.getBitmap(getContentResolver(), imagem_selecionada);

                // Log.d("onActivityResult", imagem_selecionada.toString());
                img_upload.setImageBitmap(foto_perfil);


                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {

                        String link = "http://10.0.2.2/20171sem/NannyGO/uploadImagem.php";

                        HashMap<String,String> p = new HashMap<String, String>();
                        p.put("imagem", getStringImage(foto_perfil));

                        imagem = HttpConnection.post(link,p);


                        Log.d("imagem", imagem);

                        return null;
                    }


                }.execute();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
