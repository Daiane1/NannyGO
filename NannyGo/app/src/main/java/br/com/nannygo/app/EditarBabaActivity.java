package br.com.nannygo.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class EditarBabaActivity extends AppCompatActivity
{
    static int condicaoHora;
    static TextView text_view_hora_inicio, text_view_hora_fim;
    Spinner spinner_disponibilidade;
    ImageView img_hora_inicio, img_hora_fim;
    EditText edit_text_preco;
    Context context;
    String preco, horaInicio, horaFim, diasDisponiveis;
    boolean statusValidacao = true;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        pegarView();
        abrirDialogHora();
        preencherSpinner();
        configurarBotaoConfirma();
        pegarIntent();
        preencherCampos();

    }

    //Pega os dados da Intent provenientes da activity anterior
    private void pegarIntent()
    {
        intent = getIntent();
        preco = intent.getStringExtra("preco");
        horaInicio = intent.getStringExtra("horaInicio");
        horaFim = intent.getStringExtra("horaFim");
        diasDisponiveis = intent.getStringExtra("diasDisponiveis");
    }

    //Preenche os campos com os dados da babá
    private void preencherCampos()
    {
        edit_text_preco.setText(preco);
        text_view_hora_inicio.setText(horaInicio);
        text_view_hora_fim.setText(horaFim);
    }

    private void configurarBotaoConfirma()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validarCampos();
                //Verifica se a validação dos campos foi bem sucedida.
                if (statusValidacao)
                {
                    new EditarBabaTask().execute();
                } else
                {
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.ic_warning_black_24dp)
                            .setTitle("Houve um erro")
                            .setMessage("Preencha todos os campos!")
                            .setNeutralButton("OK", null)
                            .show();
                }

            }
        });
    }

    //Verifica se os campos forem nulos ou em branco para inserção no banco de dados
    private void validarCampos()
    {
        preco = edit_text_preco.getText().toString();
        horaInicio = text_view_hora_inicio.getText().toString();
        horaFim = text_view_hora_fim.getText().toString();
        diasDisponiveis = spinner_disponibilidade.getSelectedItem().toString();

        diasDisponiveis = diasDisponiveis.replaceAll(" ", "_");

        if (preco == null || preco.isEmpty())
        {
            statusValidacao = false;
        }
        if (horaInicio == null || horaInicio.isEmpty())
        {
            statusValidacao = false;
        }
        if (horaFim == null || horaFim.isEmpty())
        {
            statusValidacao = false;
        }

    }

    //Abre o fragmento de seleção de tempo
    //Variável condicaoHora especifica o campo a ser preenchido para garantir que só um fragment
    //precise ser criado.
    private void abrirDialogHora()
    {
        img_hora_inicio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment time = new TimePickerFragment();
                condicaoHora = 0;
                time.show(getFragmentManager(), "timePickerInicio");
            }
        });
        img_hora_fim.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment time = new TimePickerFragment();
                condicaoHora = 1;
                time.show(getFragmentManager(), "timePickerFim");
            }
        });
    }

    //Pega os dados do arquivo XML
    private void pegarView()
    {
        spinner_disponibilidade = (Spinner) findViewById(R.id.spinner_disponibilidade);
        img_hora_inicio = (ImageView) findViewById(R.id.img_hora_inicio);
        img_hora_fim = (ImageView) findViewById(R.id.img_hora_fim);
        text_view_hora_fim = (TextView) findViewById(R.id.text_view_hora_fim);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        edit_text_preco = (EditText) findViewById(R.id.edit_text_preco);
    }

    //Cria uma lista e preenche os spinners com as opções de disponibilidade da babá
    private void preencherSpinner()
    {
        List<String> lstDiasSemana = new ArrayList<>();
        lstDiasSemana.add("Semana");
        lstDiasSemana.add("Finais de semana");
        lstDiasSemana.add("Sempre");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstDiasSemana);
        spinner_disponibilidade.setAdapter(adapter);
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog
                    , this, 00, 00, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            String text_hora = String.format("%02d:%02d", hourOfDay, minute);
            if (condicaoHora == 0)
            {
                text_view_hora_inicio.setText(text_hora);
            } else if (condicaoHora == 1)
            {
                text_view_hora_fim.setText(text_hora);
            }
        }
    }

    private class EditarBabaTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setMessage("Editando seu perfil...");
            dialog.setIcon(R.drawable.ic_update_black_24dp);
            dialog.setTitle("Aguarde");
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%seditarBaba.php?id_usuario=%s&preco=%s&horaInicio=%s&horaFim=%s&diasDisponiveis=%s",
                    href,
                    UsuarioFinal.getIdUsuario(),
                    preco, horaInicio, horaFim, diasDisponiveis);
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            startActivity(new Intent(context, AreaBabaActivity.class));
        }
    }
}

