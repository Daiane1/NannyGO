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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContratacaoActivity extends AppCompatActivity
{
    static int condicaoHora = 0;
    static TextView text_view_hora_fim, text_view_hora_inicio, text_view_data;
    Context context;
    ImageView img_data, img_hora_inicio;
    Spinner spinner_forma_pagamento, spinner_horas;
    List<String> lstFormaPagamento = new ArrayList<>();
    List<String> lstHoras = new ArrayList<>();
    double valor, preco;
    Integer idBaba, horas;
    String metodoPagamento, dataServico, dataFinal, horaInicio;
    boolean statusValidacao, statusData;

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

    //Pega os dados do arquivo XML
    private void pegarView()
    {
        text_view_data = (TextView) findViewById(R.id.text_view_data);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_hora_fim = (TextView) findViewById(R.id.text_view_hora_fim);
    }

    //Abre um fragmento de seleção de hora
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

    //Cria uma lista e preenche o spinner com os métodos de pagamento disponíveis
    private void preencherSpinnerPagamento()
    {
        spinner_forma_pagamento = (Spinner) findViewById(R.id.spinner_forma_pagamento);
        lstFormaPagamento.add("Crédito");
        lstFormaPagamento.add("Débito");
        lstFormaPagamento.add("Paypal");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lstFormaPagamento);
        spinner_forma_pagamento.setAdapter(adapter);
    }

    //Cria uma lista e preenche o spinner com a seleção de horas de trabalho
    private void preencherSpinnerHoras()
    {
        spinner_horas = (Spinner) findViewById(R.id.spinner_horas);
        int i = 1;
        while (i <= 8)
        {
            lstHoras.add(String.valueOf(i));
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lstHoras);
        spinner_horas.setAdapter(adapter);
    }


    //Cria um fragmento de seleção de datas
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

    //Configura o botão de confirmação
    private void configurarBotaoConfirmar()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                statusValidacao = true;
                statusData = true;
                validarCampos();

                //Verificação se a data e os campos estão preenchidos corretamente
                if (!statusData)
                {
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.ic_warning_black_24dp)
                            .setTitle("Houve um erro")
                            .setMessage("Selecione uma data válida!")
                            .setNeutralButton("OK", null)
                            .show();

                }

                if (!statusValidacao)
                {
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.ic_warning_black_24dp)
                            .setTitle("Houve um erro")
                            .setMessage("Preencha todos os campos!")
                            .setNeutralButton("OK", null)
                            .show();
                }

                //Caso os campos estejam preenchidos corretamente
                if (statusValidacao && statusData)
                {
                    metodoPagamento = spinner_forma_pagamento.getSelectedItem().toString();
                    metodoPagamento = metodoPagamento.replaceAll("é", "e");

                    horas = Integer.parseInt(spinner_horas.getSelectedItem().toString());
                    preco = horas * valor;

                    horaInicio = text_view_hora_inicio.getText().toString();
                    //Cria um AlertDialog para alertar o usuário da condição atual da transação
                    AlertDialog.Builder dialogFinalizar = new AlertDialog.Builder(context);
                    dialogFinalizar.setTitle("Contratação finalizada!")
                            .setIcon(R.drawable.done)
                            .setMessage("Aguarde a aprovação da babá.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    new InserirHistoricoTask().execute();
                                    startActivity(new Intent(context, BabasActivity.class));
                                }
                            })
                            .show();
                }
            }
        });
    }

    //Verifica se os campos estão vazios ou nulos, para inserção no banco de dados.
    private void validarCampos()
    {
        if (text_view_data == null || text_view_data.getText().toString().isEmpty())
        {
            statusValidacao = false;
        } else
        {
            dataServico = text_view_data.getText().toString();
            String data[] = dataServico.split("/");
            dataFinal = String.format("%s-%s-%s", data[2], data[1], data[0]);

            Calendar cal = Calendar.getInstance();
            Date hoje = cal.getTime();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try
            {
                Date dataSelecionada = formato.parse(dataServico);
                if (dataSelecionada.before(hoje))
                {
                    statusData = false;
                }
            } catch (ParseException e)
            {
                Log.e("erro data", e.toString());
            }
        }

        if (text_view_hora_inicio == null || text_view_hora_inicio.getText().toString().isEmpty())
        {
            statusValidacao = false;
        }
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

            //Variável condicaoHora indica o ícone selecionado para implementação de apenas um
            //fragmento de seleção de tempo
            if (condicaoHora == 0)
            {
                text_view_hora_inicio.setText(text_hora);
            } else if (condicaoHora == 1)
            {
                text_view_hora_fim.setText(text_hora);
            }
        }
    }

    private class InserirHistoricoTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sregistrarTransacao.php?id_usuario=%s&id_baba=%d&metodo_pagamento=%s&valor=%f&data_servico=%s&hora_inicio=%s&qntd_horas=%s",
                    href,
                    UsuarioFinal.getIdUsuario(),
                    idBaba,
                    metodoPagamento,
                    preco,
                    dataFinal,
                    horaInicio,
                    horas
            );
            HttpConnection.get(link);
            return null;
        }
    }

}
