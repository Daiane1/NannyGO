package br.com.nannygo.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContratacaoActivity extends AppCompatActivity
{
    Context context;
    ImageView img_data, img_hora_inicio, img_hora_fim;
    Spinner spinner_forma_pagamento, spinner_horas;
    List<String> lstFormaPagamento = new ArrayList<>();
    List<String> lstHoras = new ArrayList<>();
    static int condicaoHora = 0;
    static TextView text_view_hora_fim, text_view_hora_inicio, text_view_data;
    double valor, preco;
    Integer idBaba;
    String metodoPagamento, dataServico, dataFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        Intent intent = getIntent();
        valor = Double.parseDouble(intent.getStringExtra("preco"));
        Log.d("valor", String.valueOf(valor));
        idBaba = intent.getIntExtra("idbaba", -1);

        preencherSpinnerPagamento();
        preencherSpinnerHoras();
        configurarSelecaoHora();
        configurarBotaoConfirmar();
        configurarBotaoCalendario();

        pegarView();

    }

    private void pegarView()
    {
        text_view_data = (TextView) findViewById(R.id.text_view_data);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_hora_fim = (TextView) findViewById(R.id.text_view_hora_fim);
    }

    private void configurarSelecaoHora()
    {
        img_hora_inicio = (ImageView) findViewById(R.id.img_hora_inicio);
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
    }

    private void preencherSpinnerPagamento()
    {
        spinner_forma_pagamento = (Spinner) findViewById(R.id.spinner_forma_pagamento);
        lstFormaPagamento.add("Crédito");
        lstFormaPagamento.add("Débito");
        lstFormaPagamento.add("Paypal");
        lstFormaPagamento.add("Boleto");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lstFormaPagamento);
        spinner_forma_pagamento.setAdapter(adapter);
    }

    private void preencherSpinnerHoras()
    {
        spinner_horas = (Spinner) findViewById(R.id.spinner_horas);
        int i = 1;
        while (i <= 24)
        {
            lstHoras.add(String.valueOf(i));
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lstHoras);
        spinner_horas.setAdapter(adapter);
    }


    private void configurarBotaoCalendario()
    {
        img_data = (ImageView) findViewById(R.id.img_data);
        img_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment cal = new DatePickerFragment();
                cal.show(getFragmentManager(), "calendario");
            }
        });
    }

    private void configurarBotaoConfirmar()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder dialogFinalizar = new AlertDialog.Builder(context);
                dialogFinalizar.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        metodoPagamento = spinner_forma_pagamento.getSelectedItem().toString();
                        metodoPagamento = metodoPagamento.replaceAll("é", "e");
                        dataServico = text_view_data.getText().toString();
                        String data[] = dataServico.split("/");
                        dataFinal = String.format("%s-%s-%s", data[2], data[1], data[0]);


                        int horas = Integer.parseInt(spinner_horas.getSelectedItem().toString());
                        preco = horas*valor;

                        new InserirHistoricoTask().execute();
                        startActivity(new Intent(context, BabasActivity.class));
                    }
                })
                        .setTitle("Contratação finalizada!")
                        .setIcon(R.drawable.done)
                        .setMessage("Aguarde a aprovação da babá.")
                        .show();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            //Implementa o dialog de calendário com a data do sistema
            final Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, ano, mes, dia);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            String dataSelecionada = String.format("%02d/%02d/%d", dayOfMonth, ++month, year);
            text_view_data.setText(dataSelecionada);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, this, 00, 00, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            String text_hora = String.format("%02d:%02d", hourOfDay, minute);
            if (condicaoHora == 0)
            {
                text_view_hora_inicio.setText(text_hora);
            }
            else if (condicaoHora == 1)
            {
                text_view_hora_fim.setText(text_hora);
            }
        }
    }

    private class InserirHistoricoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sregistrarTransacao.php?id_usuario=%s&id_baba=%d&metodo_pagamento=%s&valor=%f&data_servico=%s",
                    href,
                    UsuarioFinal.getIdUsuario(),
                    idBaba,
                    metodoPagamento,
                    preco,
                    dataFinal
                    );
            Log.d("link", link);
            HttpConnection.get(link);
            return null;
        }
    }

}
