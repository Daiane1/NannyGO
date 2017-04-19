package br.com.nannygo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BabasActivity extends AppCompatActivity {

    ListView list_view_babas;
    List<Baba> lstBabas = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_babas = (ListView) findViewById(R.id.list_view_babas);
        context = this;

        preencherLista();

        configurarAdapter();


        list_view_babas.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, DetalhesBabaActivity.class));
            }
        });
    }

    private void configurarAdapter() {
        BabaAdapter adapter = new BabaAdapter(this, R.layout.list_view_item_babas, lstBabas);

        list_view_babas.setAdapter(adapter);
    }

    private void preencherLista() {
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
        lstBabas.add(new Baba(R.drawable.logo, "Lalala", "Lalala", "R$ 20,00 /", "h"));
    }
}
