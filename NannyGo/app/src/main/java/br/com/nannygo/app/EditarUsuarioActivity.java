package br.com.nannygo.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarUsuarioActivity extends AppCompatActivity
{
    static TextView text_view_data_nascimento;
    EditText edit_text_nome, edit_text_telefone, edit_text_email, edit_text_cidade, edit_text_logradouro;
    RadioButton radio_masculino, radio_feminino;
    String nome, login, telefone, email, sexo, dataNascimentoBanco, senha, dataNascimento, idCidade, logradouro, imagem;
    FloatingActionButton fab;
    Context context;
    Intent intent;
    Intent intentCidade;

    boolean statusValidacao = true;
    boolean statusData = true;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        encontrarObjetosView();
        configurarBotaoConfirmar();
        context = this;
        intent = getIntent();
        intentCidade = new Intent(context, SelecaoCidadesActivity.class);

        preencherCampos();
    }

    //Preenche os campos com os dados do usuário
    private void preencherCampos()
    {
        edit_text_nome.setText(UsuarioFinal.getNome());
        edit_text_cidade.setText(UsuarioFinal.getCidade());
        idCidade = UsuarioFinal.getIdCidade();
        edit_text_email.setText(UsuarioFinal.getEmail());
        edit_text_logradouro.setText(UsuarioFinal.getLogradouro());
        edit_text_telefone.setText(UsuarioFinal.getTelefone());

        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat formatoUI = new SimpleDateFormat("dd-mm-yyyy");
        try
        {
            Date dataBanco = formatoBanco.parse(UsuarioFinal.getDataNascimento());
            text_view_data_nascimento.setText(formatoUI.format(dataBanco));
        } catch (ParseException e)
        {
            Log.e("data", e.toString());
        }

        if (UsuarioFinal.getSexo().equals("F"))
        {
            radio_feminino.toggle();
        } else if (UsuarioFinal.getSexo().equals("M"))
        {
            radio_masculino.toggle();
        }

        pegarIntent();
    }

    //Pega a Intent da página de seleção de imagens
    private void pegarIntent()
    {
        if (intent.getStringExtra("cidade") != null)
        {
            edit_text_nome.setText(intent.getStringExtra("nome"));
            edit_text_cidade.setText(intent.getStringExtra("cidade"));
            text_view_data_nascimento.setText(intent.getStringExtra("dtNasc"));
            edit_text_telefone.setText(intent.getStringExtra("telefone"));
            edit_text_email.setText(intent.getStringExtra("email"));
            edit_text_logradouro.setText(intent.getStringExtra("logradouro"));

            if (intent.getStringExtra("sexo").equals("F"))
            {
                radio_feminino.toggle();
            } else if (intent.getStringExtra("sexo").equals("M"))
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


        //Substitui todos ' ' para '_' para o funcionamento do link PhP
        nome = nome.replaceAll(" ", "_");
        nome = nome.replaceAll("ã", "a");
        logradouro = logradouro.replaceAll(" ", "_");
        logradouro = logradouro.replaceAll("ã", "a");

        if (intent.getStringExtra("idcidade") != null)
        {
            idCidade = intent.getStringExtra("idcidade");
        }


        validarCampos();
        //Verifica se a validação dos campos foi bem sucedida
        if (!statusData)
        {
            new AlertDialog.Builder(context).setTitle("Data inválida!").setIcon(R.drawable.ic_warning_black_24dp).setMessage("Selecione uma data de nascimento válida").setPositiveButton("OK", null).show();
        }
        if (!statusValidacao)
        {
            new AlertDialog.Builder(context).setTitle("Registro inválido!").setIcon(R.drawable.ic_warning_black_24dp).setMessage("Preencha todos os campos!").setPositiveButton("OK", null).show();
        }

        if (statusValidacao && statusData)
        {
            formatarData();
            new EditarUsuarioTask().execute();
        }
    }

    //Verifica se os campos estão nulos ou vazios
    private void validarCampos()
    {
        if (nome.isEmpty() || nome == null)
        {
            statusValidacao = false;
        }

        if (!radio_feminino.isChecked() && !radio_masculino.isChecked())
        {
            statusValidacao = false;
        }
        if (text_view_data_nascimento == null || text_view_data_nascimento.getText().toString().isEmpty())
        {
            statusValidacao = false;
        } else
        {
            Calendar cal = Calendar.getInstance();
            Date hoje = cal.getTime();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                Date dataSelecionada = formato.parse(text_view_data_nascimento.getText().toString());
                if (hoje.getTime() <= dataSelecionada.getTime())
                {
                    statusData = false;
                }
            } catch (ParseException e)
            {
                Log.e("erro data", e.toString());
            }
        }

        if (logradouro.isEmpty() || logradouro == null)
        {
            statusValidacao = false;
        }

        if (edit_text_cidade == null)
        {
            statusValidacao = false;
        }

        if (telefone.isEmpty() || telefone == null)
        {
            statusValidacao = false;
        }

        if (email.isEmpty() || email == null || !email.contains("@"))
        {
            statusValidacao = false;
        }
    }

    //Pega os dados inseridos nos campos
    private void pegarDados()
    {
        nome = edit_text_nome.getText().toString();
        telefone = edit_text_telefone.getText().toString();
        email = edit_text_email.getText().toString();
        logradouro = edit_text_logradouro.getText().toString();
        dataNascimento = text_view_data_nascimento.getText().toString();
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

    //Pega os objetos do arquivo XML
    private void encontrarObjetosView()
    {
        edit_text_nome = (EditText) findViewById(R.id.edit_text_nome);
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
    }

    public void abrirSelecaoData(View view)
    {
        DialogFragment cal = new EditarUsuarioActivity.DatePickerFragment();
        cal.show(getFragmentManager(), "calendario");
    }

    public void abrirSelecaoCidade(View view)
    {
        preencherIntent();
        startActivity(intentCidade);
    }

    //Preenche o intent para salvar os dados preenchidos até o momento na abertura da página de seleção de cidades
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
        intentCidade.putExtra("activity", "EditarUsuarioActivity");
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

    private class EditarUsuarioTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog progress = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress.setMessage("Registrando...");
            progress.setIcon(R.drawable.ic_update_black_24dp);
            progress.setTitle("Aguarde");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/editarUsuario.php?id_usuario=%s&nome=%s&sexo=%s&telefone=%s&email=%s&data_nascimento=%s&cidade=%s&logradouro=%s",
                    UsuarioFinal.getIdUsuario(), nome, sexo, telefone, email, dataNascimentoBanco, idCidade, logradouro);
            Log.d("link", link);
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progress.dismiss();
            new AlertDialog.Builder(context)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setTitle("Sucesso!")
                    .setMessage("Seus dados foram editados com sucesso.\nPor favor, efetue o login novamente.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            UsuarioFinal.logout();
                            startActivity(new Intent(context, MainActivity.class));
                        }
                    }).show();
        }
    }

}


