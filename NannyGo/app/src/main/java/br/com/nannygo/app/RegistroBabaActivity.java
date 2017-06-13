package br.com.nannygo.app;

import android.app.Dialog;
import android.app.DialogFragment;
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

public class RegistroBabaActivity extends AppCompatActivity
{
    static int condicaoHora;
    static TextView text_view_hora_inicio, text_view_hora_fim;
    Spinner spinner_disponibilidade;
    ImageView img_hora_inicio, img_hora_fim;
    EditText edit_text_preco;
    Context context;
    String preco, horaInicio, horaFim, diasDisponiveis;
    boolean statusValidacao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        pegarView();
        abrirDialogHora();
        preencherSpinner();
        configurarBotaoConfirma();

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
                if (statusValidacao)
                {
                    new RegistrarBabaTask().execute();
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

    //Verifica se os campos digitados estão nulos ou em branco
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

    //Abre o fragmento de seleção de hora
    //Variável condicaoHora especifica o campo a ser preenchido com a hora selecionada para utilização
    //de apenas um fragmento
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

    //Cria a lista e preenche o spinner com as opções de seleção de disponibilidade
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

    private class RegistrarBabaTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sregistrarBaba.php?id_usuario=%s&preco=%s&horaInicio=%s&horaFim=%s&diasDisponiveis=%s&id_cidade=%s",
                    href,
                    UsuarioFinal.getIdUsuario(),
                    preco, horaInicio, horaFim, diasDisponiveis,
                    UsuarioFinal.getIdCidade());
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            UsuarioFinal.setStatusBaba("1");
            startActivity(new Intent(context, MenuBabaActivity.class));
        }
    }

}
