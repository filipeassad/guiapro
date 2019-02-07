package dev.kosmo.com.br.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoAdapterInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class AtendimentoAdapter extends ArrayAdapter<Atendimento> {

    private Context myContext;
    private int myResource;
    private String TIPO_ATENDIMENTO_LIGAR = "Ligar";
    private String TIPO_ATENDIMENTO_CONVERSAR = "Conversar";
    private final String ENTRAR_EM_CONTATO_COM_CLIENTE = "Entrar em contato";
    private String COR_LARANJA = "#e9a11c";
    private String COR_VERDE = "#2daa0a";

    private final long TIPO_ATENDIMENTO_LIGACAO = 1;
    private final long TIPO_ATENDIMENTO_WHATSAPP = 2;
    private final long TIPO_ATENDIMENTO_ME_LIGUE = 3;
    private final String FORMATO_DATA = "dd/MM/yyyy hh:mm:ss";

    private AtendimentoAdapterInterface atendimentoAdapterInterface;

    public AtendimentoAdapter(@NonNull Context context, int resource, @NonNull List<Atendimento> objects, AtendimentoAdapterInterface atendimentoAdapterInterface) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
        this.atendimentoAdapterInterface = atendimentoAdapterInterface;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        ImageView ivItem = (ImageView) convertView.findViewById(R.id.ivItem);
        TextView tvNomeCliente = (TextView) convertView.findViewById(R.id.tvNomeCliente);
        TextView tvCategoria = (TextView) convertView.findViewById(R.id.tvCategoria);
        TextView tvTipoAtendimento = (TextView) convertView.findViewById(R.id.tvTipoAtendimento);
        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        TextView tvSituacao = (TextView) convertView.findViewById(R.id.tvSituacao);
        LinearLayout llBotaoLateral = (LinearLayout) convertView.findViewById(R.id.llBotaoLateral);
        ImageView ivBotaoLateral = (ImageView) convertView.findViewById(R.id.ivBotaoLateral);
        TextView tvLateral = (TextView) convertView.findViewById(R.id.tvLateral);
        LinearLayout llUrgencia = (LinearLayout) convertView.findViewById(R.id.llUrgencia);
        LinearLayout llContainer = (LinearLayout) convertView.findViewById(R.id.llContainer);

        final Atendimento atendimento = getItem(position);

        /*ivItem.setImageBitmap(atendimento.getClienteObj().getImg() != null ?
                atendimento.getClienteObj().getImg() :
                BitmapFactory.decodeResource(myContext.getResources(), R.drawable.usuario_laranja));*/

        tvNomeCliente.setText(atendimento.getCliente().getNome());

        tvCategoria.setText("Categoria: " + atendimento.getCategoria().getDescricao());
        tvTipoAtendimento.setText(FerramentasBasicas.obterTipoAtendimento(Integer.parseInt(atendimento.getTipoAtendimentoId() + "")));
        tvData.setText("Data: " + FerramentasBasicas.converterDataParaString(atendimento.getData(), FORMATO_DATA ));
        tvSituacao.setText("Situação: " + FerramentasBasicas.obterSituacao(Integer.parseInt(atendimento.getSitucaoId() + "")));

        llBotaoLateral.setBackgroundResource(R.drawable.shape_btn_canto_laranja);
        ivBotaoLateral.setImageBitmap(BitmapFactory.decodeResource(myContext.getResources(),
                R.drawable.transfer));
        tvLateral.setText(ENTRAR_EM_CONTATO_COM_CLIENTE);
        tvLateral.setTextColor(Color.parseColor(COR_LARANJA));

        //llUrgencia.setVisibility(atendimento.getId() % 2 == 0 ? View.VISIBLE: View.GONE);

        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atendimentoAdapterInterface.entrarEmContato(atendimento);
            }
        });

        return convertView;
    }
}
