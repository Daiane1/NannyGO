package br.com.nannygo.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TransacaoEsperaAdapter extends ArrayAdapter<Transacao>
{
    int resource;
    View view;
    Transacao transacao;
    TextView text_view_id_transacao, text_view_id_baba, text_view_id_usuario, text_view_preco, text_view_nome_cliente;
    LinearLayout linear_layout_transacao_espera;

    public TransacaoEsperaAdapter(Context context, int resource, List<Transacao> objects)
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

        transacao = getItem(position);

        pegarView();
        inserirCamposLayout();

        //Muda a cor de fundo da transação de acordo com o estado da transação
        if (transacao.getStatusAprovado() == 1)
        {
            linear_layout_transacao_espera.setBackgroundColor(view.getResources().getColor(R.color.verde, null));
        } else if (transacao.getStatusAprovado() == -1)
        {
            linear_layout_transacao_espera.setBackgroundColor(view.getResources().getColor(R.color.vermelho, null));
        }

        return view;
    }

    //Insere os campos do item da lista
    private void inserirCamposLayout()
    {
        text_view_id_baba.setText(transacao.getIdBaba().toString());
        text_view_id_usuario.setText(transacao.getIdUsuario().toString());
        text_view_id_transacao.setText(transacao.getIdTransacao().toString());
        text_view_preco.setText(String.format("R$ %.2f", Double.parseDouble(transacao.getValor())));
        text_view_nome_cliente.setText(transacao.getNome());
    }

    //Pega os dados do arquivo XML
    public void pegarView()
    {
        text_view_id_transacao = (TextView) view.findViewById(R.id.text_view_id_transacao);
        text_view_id_usuario = (TextView) view.findViewById(R.id.text_view_id_usuario);
        text_view_id_baba = (TextView) view.findViewById(R.id.text_view_id_baba);
        text_view_nome_cliente = (TextView) view.findViewById(R.id.text_view_nome_cliente);
        text_view_preco = (TextView) view.findViewById(R.id.text_view_preco);
        linear_layout_transacao_espera = (LinearLayout) view.findViewById(R.id.linear_layout_transacao_espera);

    }

}