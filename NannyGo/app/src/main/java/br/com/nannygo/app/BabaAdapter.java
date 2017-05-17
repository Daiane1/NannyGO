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

public class BabaAdapter extends ArrayAdapter<Baba>
{
    int resource;
    View view;
    ImageView img_baba;
    TextView hora_baba, dia_semana_baba, preco_hora_baba, id_baba, nome_item_baba, distancia_baba;
    String hora, preco;
    Baba baba;

    public BabaAdapter(Context context, int resource, List<Baba> objects)
    {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        view = convertView;
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        baba = getItem(position);
        pegarView();
        formatarStrings();
        inserirCamposLayout();

        return view;
    }

    private void inserirCamposLayout()
    {
        hora_baba.setText(hora);
        dia_semana_baba.setText(baba.getDiasDisponiveis());
        id_baba.setText(baba.getIdUsuario().toString());
        preco_hora_baba.setText(preco);
        nome_item_baba.setText(baba.getNome());
        img_baba.setImageResource(R.drawable.babym);

        String distancia = String.format("%.2f km", baba.getDistanciaKm());
        if (baba.getDistanciaKm() < 5)
        {
            distancia = "< 5 km";
        }
        distancia_baba.setText(distancia);
    }

    // Método formata as horas iniciais e finais em um só campo, e também coloca a moeda no preço.
    private void formatarStrings()
    {
        hora = String.format("%s-%s", baba.getHoraInicio(), baba.getHoraFim());
        preco = String.format("R$ %s", baba.getPreco());
    }

    public void pegarView()
    {
        img_baba = (ImageView) view.findViewById(R.id.img_item_baba);
        hora_baba = (TextView) view.findViewById(R.id.hora_baba);
        dia_semana_baba = (TextView) view.findViewById(R.id.dia_semana_baba);
        preco_hora_baba = (TextView) view.findViewById(R.id.preco_hora_baba);
        id_baba = (TextView) view.findViewById(R.id.id_baba);
        nome_item_baba = (TextView) view.findViewById(R.id.nome_item_baba);
        distancia_baba = (TextView) view.findViewById(R.id.distancia_baba);
    }

}

