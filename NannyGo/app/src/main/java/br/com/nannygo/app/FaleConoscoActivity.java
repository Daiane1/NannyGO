package br.com.nannygo.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class FaleConoscoActivity extends AppCompatActivity
{

    EditText edit_comentario;
    Context context;
    String comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        edit_comentario = (EditText) findViewById(R.id.edit_comentario);

    }

    public void enviarComentario(View view)
    {
        comentario = edit_comentario.getText().toString();
        if (comentario != null || !comentario.isEmpty())
        {
            comentario = comentario.replaceAll(" ", "_");
            new EnviarFeedbackTask().execute();
        }
    }

    private class EnviarFeedbackTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog progress = new ProgressDialog(context);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress.setMessage("Enviando seu feedback, por favor aguarde...");
            progress.setIcon(R.drawable.ic_update_black_24dp);
            progress.setTitle("Aguarde");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String href = getResources().getString(R.string.linkLocal);
            String link = String.format("%sfaleconosco.php?comentario=%s",
                    href,
                    comentario);
            HttpConnection.get(link);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progress.dismiss();
            new AlertDialog.Builder(context)
                    .setIcon(R.drawable.done)
                    .setTitle("Sucesso!")
                    .setMessage("Seu feedback foi enviado com sucesso!")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            startActivity(new Intent(context, FaleConoscoActivity.class));
                        }
                    }).show();
        }
    }

}
