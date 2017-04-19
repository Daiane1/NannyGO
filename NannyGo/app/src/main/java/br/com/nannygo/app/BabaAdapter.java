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

public class BabaAdapter extends ArrayAdapter<Baba> {

    int resource;
    public BabaAdapter(Context context, int resource, List<Baba> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null){
            v = LayoutInflater.from(getContext())
                    .inflate(resource, null);
        }

        Baba baba = getItem(position);

        ImageView img_baba = (ImageView) v.findViewById(R.id.img_item_baba);
        TextView nome_baba = (TextView) v.findViewById(R.id.nome_item_baba);
        TextView avaliacao_baba = (TextView) v.findViewById(R.id.avaliacao_item_baba);
        TextView preco_baba = (TextView) v.findViewById(R.id.preco_item_baba);
        TextView hora_baba = (TextView) v.findViewById(R.id.hora_item_baba);

        nome_baba.setText(baba.getNome());
        img_baba.setImageResource(baba.getImagem());
        avaliacao_baba.setText(baba.getAvaliacao());
        preco_baba.setText(baba.getPreco());
        hora_baba.setText(baba.getHora());

        /**Picasso.with(getContext())
                .load(baba.getImagem())
                .transform(new CircleTransform())
                .into(img_contato);**/

        return v;

    }
}

