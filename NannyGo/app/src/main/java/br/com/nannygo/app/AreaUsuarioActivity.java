package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class AreaUsuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout nav_header;
    TextView text_view_nome_usuario, text_view_idade_usuario, text_view_email_usuario, text_view_telefone_usuario;
    String retornoJson, login, senha;
    Context context;
    //ImageView img_baba;

    //Intent intent;
    //String jsonUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_usuario);

        Intent intent = getIntent();

        if (intent != null){
            login = intent.getStringExtra("login");
            senha = intent.getStringExtra("senha");
        }

        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        encontrarObjetosView();
        new inserirCampos().execute();

    }

    private void encontrarObjetosView() {
        /*
        text_view_nome_usuario = (TextView) findViewById(R.id.text_view_nome_usuario);
        text_view_idade_usuario = (TextView) findViewById(R.id.text_view_idade_usuario);
        text_view_email_usuario = (TextView) findViewById(R.id.text_view_email_usuario);
        text_view_telefone_usuario = (TextView) findViewById(R.id.text_view_telefone_usuario);
        nav_header = (LinearLayout) findViewById(R.id.nav_header);
        */
    }

    private class inserirCampos extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        String retornoJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String link = String.format("http://10.0.2.2/20171sem/NannyGO/autenticarUsuario.php?login=%s&senha=%s", login, senha);
            Log.d("link",link);
            retornoJson = HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE).create();

            if (retornoJson.isEmpty())
            {
                new AlertDialog.Builder(context)
                        .setTitle("Acesso Negado")
                        .setNeutralButton("OK", null)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Login ou senha inválidos.")
                        .show();
            }
            else
            {
                Usuario usuario = gson.fromJson(retornoJson, Usuario.class);
                criarUsuarioFinal(usuario);
                /*
                text_view_nome_usuario.setText(UsuarioFinal.getNome());
                text_view_idade_usuario.setText(UsuarioFinal.getIdade());
                text_view_email_usuario.setText(UsuarioFinal.getEmail());
                text_view_telefone_usuario.setText(UsuarioFinal.getTelefone());
                */

            }
        }
    }

    private void criarUsuarioFinal(Usuario usuario) {
        UsuarioFinal.setNome(usuario.getNome());
        UsuarioFinal.setSexo(usuario.getSexo());
        UsuarioFinal.setTelefone(usuario.getTelefone());
        UsuarioFinal.setEmail(usuario.getEmail());
        UsuarioFinal.setLogin(usuario.getLogin());
        UsuarioFinal.setSenha(usuario.getSenha());
        UsuarioFinal.setDataNascimento(usuario.getDataNascimento());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.area_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
