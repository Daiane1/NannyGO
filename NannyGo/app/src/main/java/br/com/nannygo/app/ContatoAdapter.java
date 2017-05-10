package br.com.nannygo.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    int resource;
    public ContatoAdapter(Context context, int resource, List<Contato> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext())
                    .inflate(resource, null);
        }

        Contato contato = getItem(position);

        ImageView img_contato = (ImageView) v.findViewById(R.id.img_item_contato);
        TextView nome_contato = (TextView) v.findViewById(R.id.nome_item_contato);
        TextView status_contato = (TextView) v.findViewById(R.id.status_item_contato);

        nome_contato.setText(contato.getNome());
        //avaliacao_contato.setText(contato.getAvaliacao());
        img_contato.setImageResource(contato.getImagem());

        return v;
    }
}

