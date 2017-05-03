package br.com.nannygo.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Teste2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int resultado_upload=1;
    ImageView img_upload;
    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste2);

        img_upload = (ImageView)findViewById(R.id.img_upload);
        btn_salvar = (Button)findViewById(R.id.btn_salvar);

        img_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.img_upload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, resultado_upload);
                break;
            case R.id.btn_salvar:
                Bitmap imagem = ((BitmapDrawable) img_upload.getDrawable()).getBitmap();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultado_upload && resultCode == RESULT_OK && data !=null){
            Uri imagem_selecionada= data.getData();
            img_upload.setImageURI(imagem_selecionada);
        }
    }

    private class UploadImagem extends AsyncTask<Void, Void, Void>{

        Bitmap imagem;

        public UploadImagem(Bitmap imagem){
            this.imagem = imagem;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImagem = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            return null;
        }
    }
}
