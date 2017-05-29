package br.com.nannygo.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetalhesTransacaoEsperaActivity extends AppCompatActivity
{
    String valor, horaInicio, dataTransacao, dataServico, metodoPagamento, nome;
    Integer idTransacao, idUsuario, idBaba, qntdHoras, statusAprovado;
    TextView text_view_nome, text_view_data_transacao, text_view_data_servico, text_view_hora_inicio, text_view_qntd_horas, text_view_metodo_pagamento, text_view_valor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_transacao_espera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        pegarIntent();
        pegarView();
        inserirCampos();
    }

    private void inserirCampos()
    {
        text_view_nome.setText(nome);
        formatarDatas();
        text_view_hora_inicio.setText(horaInicio);
        text_view_qntd_horas.setText(String.format("%d hora(s)", qntdHoras));
        text_view_metodo_pagamento.setText(metodoPagamento);
        text_view_valor.setText(String.format("R$ %.2f", Double.parseDouble(valor)));
    }

    private void formatarDatas()
    {
        String dataServicoFormatado[] = dataServico.split("-");
        String dataTransacaoFormatado[] = dataTransacao.split("-");

        text_view_data_servico.setText(String.format("%s/%s/%s", dataServicoFormatado[2], dataServicoFormatado[1], dataServicoFormatado[0]));
        text_view_data_transacao.setText(String.format("%s/%s/%s", dataTransacaoFormatado[2], dataTransacaoFormatado[1], dataTransacaoFormatado[0]));
    }

    private void pegarView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_data_servico = (TextView) findViewById(R.id.text_view_data_servico);
        text_view_data_transacao = (TextView) findViewById(R.id.text_view_data_transacao);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_metodo_pagamento = (TextView) findViewById(R.id.text_view_metodo_pagamento);
        text_view_qntd_horas = (TextView) findViewById(R.id.text_view_qntd_horas);
        text_view_valor = (TextView) findViewById(R.id.text_view_valor);
    }

    public void pegarIntent()
    {
        Intent intent;
        intent = getIntent();

        idTransacao = intent.getIntExtra("idTransacao", -1);
        idUsuario = intent.getIntExtra("idUsuario", -1);
        idBaba = intent.getIntExtra("idBaba", -1);
        statusAprovado = intent.getIntExtra("statusAprovado", -2);
        qntdHoras = intent.getIntExtra("qntdHoras", -1);
        valor = intent.getStringExtra("valor");
        dataTransacao = intent.getStringExtra("dataTransacao");
        dataServico = intent.getStringExtra("dataServico");
        metodoPagamento = intent.getStringExtra("metodoPagamento");
        nome = intent.getStringExtra("nome");
        horaInicio = intent.getStringExtra("horaInicio");
    }

    public void aprovarTransacao(View view)
    {
        new AlertDialog.Builder(context)
                .setTitle("Aprovar")
                .setMessage("Tem certeza que deseja aprovar essa transação?")
                .setIcon(R.drawable.done)
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .show();
    }

    public void rejeitarTransacao(View view)
    {
        new AlertDialog.Builder(context)
                .setTitle("Rejeitar")
                .setMessage("Tem certeza que deseja rejeitar essa transação?")
                .setIcon(android.R.drawable.ic_delete)
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .show();
    }

}