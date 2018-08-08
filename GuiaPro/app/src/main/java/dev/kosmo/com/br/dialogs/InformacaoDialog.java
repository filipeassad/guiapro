package dev.kosmo.com.br.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import dev.kosmo.com.br.guiapro.R;

public class InformacaoDialog {
    private Context context;
    private int contador = 0;

    public InformacaoDialog(Context context) {
        this.context = context;
    }

    public void gerarDialog(String texto){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_informacao);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvTexto = (TextView) dialog.findViewById(R.id.tvTexto);
        tvTexto.setText(texto);

        dialog.show();
        contador = 0;

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(contador == 1){
                    dialog.dismiss();
                }
                contador++;
            }
        }, 800, 800);
    }
}
