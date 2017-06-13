package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetalhesHistoricoBabaActivity extends AppCompatActivity
{

    String valor, horaInicio, dataTransacao, dataServico, metodoPagamento, nome;
    Integer idTransacao, idUsuario, idBaba, qntdHoras, statusAprovado;
    TextView text_view_nome, text_view_data_transacao, text_view_data_servico, text_view_hora_inicio, text_view_qntd_horas, text_view_metodo_pagamento, text_view_valor, text_view_status;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_historico_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        pegarIntent();
        pegarView();
        inserirCampos();
    }

    //Insere os dados da transação selecionada
    private void inserirCampos()
    {
        text_view_nome.setText(nome);
        formatarDatas();
        text_view_hora_inicio.setText(horaInicio);
        text_view_qntd_horas.setText(String.format("%d hora(s)", qntdHoras));
        text_view_metodo_pagamento.setText(metodoPagamento);
        text_view_valor.setText(String.format("R$ %.2f", Double.parseDouble(valor)));
        if (statusAprovado == 1)
        {
            text_view_status.setText("Aprovado");
        } else if (statusAprovado == -1)
        {
            text_view_status.setText("Rejeitado");
        }
    }

    //Formata as datas do formato yyyy-mm-dd para dd/mm/yyyy
    private void formatarDatas()
    {
        String dataServicoFormatado[] = dataServico.split("-");
        String dataTransacaoFormatado[] = dataTransacao.split("-");

        text_view_data_servico.setText(String.format("%s/%s/%s", dataServicoFormatado[2], dataServicoFormatado[1], dataServicoFormatado[0]));
        text_view_data_transacao.setText(String.format("%s/%s/%s", dataTransacaoFormatado[2], dataTransacaoFormatado[1], dataTransacaoFormatado[0]));
    }

    //Pega os campos do arquivo XML
    private void pegarView()
    {
        text_view_nome = (TextView) findViewById(R.id.text_view_nome);
        text_view_data_servico = (TextView) findViewById(R.id.text_view_data_servico);
        text_view_data_transacao = (TextView) findViewById(R.id.text_view_data_transacao);
        text_view_hora_inicio = (TextView) findViewById(R.id.text_view_hora_inicio);
        text_view_metodo_pagamento = (TextView) findViewById(R.id.text_view_metodo_pagamento);
        text_view_qntd_horas = (TextView) findViewById(R.id.text_view_qntd_horas);
        text_view_valor = (TextView) findViewById(R.id.text_view_valor);
        text_view_status = (TextView) findViewById(R.id.text_view_status);
    }

    //Pega os dados do intent provenientes da activity anterior
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
}
