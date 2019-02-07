package dev.kosmo.com.br.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class EntrarContatoDialog {

    private Context context;

    public EntrarContatoDialog(Context context) {
        this.context = context;
    }

    public Dialog gerarDialog(final Atendimento atendimento){

        final Dialog dialog = new Dialog(this.context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_entrar_contato);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout btnLigar = (LinearLayout) dialog.findViewById(R.id.btnLigar);
        LinearLayout btnWhats = (LinearLayout) dialog.findViewById(R.id.btnWhats);

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FerramentasBasicas.fazerLigacao(atendimento.getCliente().getCelular());
                dialog.dismiss();

            }
        });

        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FerramentasBasicas.enviarWhats(context, atendimento.getCliente().getCelular());
                dialog.dismiss();

            }
        });

        dialog.show();

        return dialog;
    }

}
