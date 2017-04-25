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
        TextView hora_inicial_baba = (TextView) v.findViewById(R.id.hora_inicial_baba);
        TextView hora_final_baba = (TextView) v.findViewById(R.id.hora_final_baba);
        TextView dia_semana_baba = (TextView) v.findViewById(R.id.dia_semana_baba);
        TextView id_baba = (TextView) v.findViewById(R.id.id_baba);
        TextView preco_hora_baba = (TextView) v.findViewById(R.id.preco_hora_baba);

        img_baba.setImageResource(baba.getImagem());
        hora_inicial_baba.setText(baba.getHoraInicio());
        hora_final_baba.setText(baba.getHoraFim());
        dia_semana_baba.setText(baba.getDiasDisponiveis());
        //id_baba.setText(baba.getIdBaba());
        preco_hora_baba.setText(baba.getPreco());

        /**Picasso.with(getContext())
                .load(baba.getImagem())
                .transform(new CircleTransform())
                .into(img_contato);**/

        return v;

    }
}

