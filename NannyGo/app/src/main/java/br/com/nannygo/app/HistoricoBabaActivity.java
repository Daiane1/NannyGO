package br.com.nannygo.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoricoBabaActivity extends AppCompatActivity {

    ListView list_view_historico_baba;
    static List<Transacao> lstHistoricoBaba = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarAdapter();
    }

    private void configurarAdapter() {
        list_view_historico_baba = (ListView) findViewById(R.id.list_view_historico_baba);

        TransacaoEsperaAdapter adapter = new TransacaoEsperaAdapter(context, R.layout.list_view_item_transacao_espera, lstHistoricoBaba);
        list_view_historico_baba.setAdapter(adapter);
    }

}
