package dev.kosmo.com.br.dialogs;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Atendimento;

public class AtendimentoDialog {

    private Context context;

    private String TIPO_ATENDIMENTO_LIGAR = "Ligar";
    private String TIPO_ATENDIMENTO_CONVERSAR = "Conversar";
    private String COR_LARANJA = "#e9a11c";
    private String COR_VERDE = "#2daa0a";

    private final long TIPO_ATENDIMENTO_LIGACAO = 1;
    private final long TIPO_ATENDIMENTO_WHATSAPP = 2;
    private final long TIPO_ATENDIMENTO_ME_LIGUE = 3;

    public AtendimentoDialog(Context context) {
        this.context = context;
    }

    public Dialog gerarDialog(final Atendimento atendimento){

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_atendimento);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout llContainer = (LinearLayout) dialog.findViewById(R.id.llContainer);
        ImageView ivItem = (ImageView) dialog.findViewById(R.id.ivItem);
        TextView tvNomeCliente = (TextView) dialog.findViewById(R.id.tvNomeCliente);
        TextView tvDescricaoAtendimento = (TextView) dialog.findViewById(R.id.tvDescricaoAtendimento);
        LinearLayout llBotaoLateral = (LinearLayout) dialog.findViewById(R.id.llBotaoLateral);
        ImageView ivBotaoLateral = (ImageView) dialog.findViewById(R.id.ivBotaoLateral);
        TextView tvLateral = (TextView) dialog.findViewById(R.id.tvLateral);
        LinearLayout llUrgencia = (LinearLayout) dialog.findViewById(R.id.llUrgencia);

        /*ivItem.setImageBitmap(atendimento.getCliente().getUrlImg() != null ?
                atendimento.getClienteObj().getImg() :
                BitmapFactory.decodeResource(context.getResources(), R.drawable.usuario_laranja));*/

        tvNomeCliente.setText(atendimento.getCliente().getNome());
        tvDescricaoAtendimento.setText(atendimento.getDescricao());

        if(atendimento.getTipoAtendimentoId() == TIPO_ATENDIMENTO_LIGACAO){
            llBotaoLateral.setBackgroundResource(R.drawable.shape_btn_canto_laranja);
            ivBotaoLateral.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.smartphone));
            tvLateral.setText(TIPO_ATENDIMENTO_LIGAR);
            tvLateral.setTextColor(Color.parseColor(COR_LARANJA));
        }else{
            llBotaoLateral.setBackgroundResource(R.drawable.shape_btn_canto_verde);
            ivBotaoLateral.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.whatsapp));
            tvLateral.setText(TIPO_ATENDIMENTO_CONVERSAR);
            tvLateral.setTextColor(Color.parseColor(COR_VERDE));
        }

        llBotaoLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetalheAtendimentoDialog detalheAtendimentoDialog = new DetalheAtendimentoDialog(context);
                detalheAtendimentoDialog.gerarDialog(atendimento);
                dialog.dismiss();
            }
        });

        llUrgencia.setVisibility(atendimento.getId() % 2 == 0 ? View.VISIBLE: View.GONE);

        dialog.show();

        return dialog;
    }

}