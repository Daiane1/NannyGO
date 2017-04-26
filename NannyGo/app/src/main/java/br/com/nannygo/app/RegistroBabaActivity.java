package br.com.nannygo.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class RegistroBabaActivity extends AppCompatActivity
{
    static Spinner spinner_disponibilidade;
    static int condicaoHora;
    static ImageView img_hora_inicio, img_hora_fim;
    static TextView text_view_hora_inicio, text_view_hora_fim;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pegarView();
        abrirDialogHora();
        preencherSpinner();

        context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BabasActivity.class));
            }
        });

    }

    private void abrirDialogHora() {
        img_hora_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment time = new TimePickerFragment();
                condicaoHora = 0;
                time.show(getFragmentManager(), "timePickerInicio");
            }
        });
        img_hora_fim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment time = new TimePickerFragment();
                condicaoHora = 1;
                time.show(getFragmentManager(), "timePickerFim");
            }
        });
    }

    private void pegarView() {
        spinner_disponibilidade = (Spinner) findViewById(R.id.spinner_disponibilidade);
        img_hora_inicio = (ImageView) findViewById(R.id.img_hora_inicio);
        img_hora_fim = (ImageView) findViewById(R.id.img_hora_fim);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_hora_fim = (TextView) findViewById(R.id.text_view_hora_fim);
    }

    private void preencherSpinner() {
        List<String> lstDiasSemana = new ArrayList<>();
        lstDiasSemana.add("Semana");
        lstDiasSemana.add("Final de semana");
        lstDiasSemana.add("Sempre");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstDiasSemana);
        spinner_disponibilidade.setAdapter(adapter);
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog
                    , this, 00, 00, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String text_hora = String.format("%02d:%02d", hourOfDay, minute);
            if (condicaoHora == 0) {
                text_view_hora_inicio.setText(text_hora);
            } else if (condicaoHora == 1) {
                text_view_hora_fim.setText(text_hora);
            }
        }
    }

}
