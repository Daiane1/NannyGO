package br.com.nannygo.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity
{
    EditText edit_text_nome, edit_text_login, edit_text_senha, edit_text_confirmar, edit_text_telefone, edit_text_email, edit_text_cidade;
    static TextView text_view_data_nascimento;
    RadioButton radio_masculino, radio_feminino;
    String nome, login, telefone, email, sexo, dataNascimentoBanco;
    String senha = "";
    String confirmar = "-";
    FloatingActionButton fab;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        text_view_data_nascimento = (TextView) findViewById(R.id.text_view_data_nascimento);
        text_view_data_nascimento.setKeyListener(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configurarBotaoConfirmar();
    }

    private void configurarBotaoConfirmar()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                encontrarObjetosView();
                inserirBanco();
            }
        });
    }



    private void inserirBanco()
    {
        nome = edit_text_nome.getText().toString();
        login = edit_text_login.getText().toString();
        senha = edit_text_senha.getText().toString();
        confirmar = edit_text_confirmar.getText().toString();
        telefone = edit_text_telefone.getText().toString();
        email = edit_text_email.getText().toString();

        verificarSexoSelecionado();
        formatarData();

        //Substitui todos ' ' para '_' para o funcionamento do link PhP
        nome = nome.replaceAll(" ", "_");

        //Verifica se a senha em ambos os campos estiver semelhante(senha e confirmar senha)
        if (senha.equals(confirmar))
        {
            new RegistroUsuarioTask().execute();
        }
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
        edit_text_confirmar = (EditText) findViewById(R.id.edit_text_confirmar);
        edit_text_telefone = (EditText) findViewById(R.id.edit_text_telefone);
        edit_text_email = (EditText) findViewById(R.id.edit_text_email);
        radio_feminino = (RadioButton) findViewById(R.id.radio_feminino);
        radio_masculino = (RadioButton) findViewById(R.id.radio_masculino);
        edit_text_cidade = (EditText) findViewById(R.id.edit_text_cidade);
    }

    public void abrirSelecaoData(View view)
    {
        DialogFragment cal = new RegistroActivity.DatePickerFragment();
        cal.show(getFragmentManager(), "calendario");
    }

    public void abrirSelecaoCidade(View view)
    {
        //startActivity(new Intent(context, SelecaoCidadeActivity.class));
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
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/registroUsuario.php?nome=%s&login=%s&senha=%s&sexo=%s&telefone=%s&email=%s&data_nascimento=%s",
                    nome, login, senha, sexo, telefone, email, dataNascimentoBanco);
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

}
