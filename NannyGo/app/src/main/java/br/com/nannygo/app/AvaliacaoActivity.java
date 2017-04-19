package br.com.nannygo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoActivity extends AppCompatActivity {

    ListView list_view_contatos;
    List<Contato> lstContatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_contatos = (ListView) findViewById(R.id.list_view_contatos);


        //Criar lista de contatos
        /*
        lstContatos.add(new Contato());

        ContatoAdapter adapter = new ContatoAdapter(
                this, //contexto
                R.layout.list_view_item_contatos,
                lstContatos
        );

        list_view_contatos.setAdapter(adapter);
        */

    }

}
