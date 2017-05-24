package br.com.nannygo.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Teste2Activity extends AppCompatActivity implements  View.OnClickListener {

    ImageView img_upload;
    private static final int RESULT_LOAD_IMAGE = 1;
    private Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste2);

        img_upload = (ImageView)findViewById(R.id.img_upload);
        btn_salvar = (Button)findViewById(R.id.btn_salvar);

        img_upload.setOnClickListener(this);
        btn_salvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_salvar:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri imagem_selecionada = data.getData();

           // Log.d("onActivityResult", imagem_selecionada.toString());
            img_upload.setImageURI(imagem_selecionada);
        }
    }
}
