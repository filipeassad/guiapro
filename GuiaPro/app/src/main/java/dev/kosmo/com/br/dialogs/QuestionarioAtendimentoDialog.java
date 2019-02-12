package dev.kosmo.com.br.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import dev.kosmo.com.br.enuns.SituacaoEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class QuestionarioAtendimentoDialog {

    private Context context;
    private AtendimentoInterface atendimentoInterface;
    private final String URL_ATENDIMENTO_PUT = "atendimento/";

    public QuestionarioAtendimentoDialog(Context context, AtendimentoInterface atendimentoInterface) {
        this.context = context;
        this.atendimentoInterface = atendimentoInterface;
    }

    public Dialog gerarDialog(final Atendimento atendimento){

        final Dialog dialog = new Dialog(this.context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_questionario_atendimento);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvNome = (TextView) dialog.findViewById(R.id.tvNome);
        TextView tvCategoria = (TextView) dialog.findViewById(R.id.tvCategoria);
        TextView tvDataAtendimento = (TextView) dialog.findViewById(R.id.tvDataAtendimento);
        TextView tvMensagem = (TextView) dialog.findViewById(R.id.tvMensagem);
        int situacao = Integer.parseInt(atendimento.getSitucaoId() + "");

        tvCategoria.setText("Categoria: " + atendimento.getCategoria().getDescricao());
        tvDataAtendimento.setText("Data: " + FerramentasBasicas.converterDataParaString(atendimento.getData(), "dd/MM/yyyy"));

        if(situacao == SituacaoEnum.AGUARDANDOATENDIMENTO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("O profissional te atendeu ?");
        }else if(situacao == SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()
                || situacao == SituacaoEnum.ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()){
            tvNome.setText(atendimento.getCliente().getNome() + " " + atendimento.getCliente().getSobrenome());
            tvMensagem.setText("O cliente te ligou, você o atendeu ?");
        }else if(situacao == SituacaoEnum.ATENDIDO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o trabalho com o profissional ?");
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue()
                || situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o trabalho com o cliente ?");
        }else if(situacao == SituacaoEnum.TRABALHOFECHADO.getValue()) {
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("O profissional finalizou o serviço ?\n");
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue()
                || situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o finalizou com o cliente ?");
        }

        LinearLayout btnSim = (LinearLayout) dialog.findViewById(R.id.btnSim);
        LinearLayout btnNao = (LinearLayout) dialog.findViewById(R.id.btnNao);

        atendimento.setData(new Date());

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if()

                dialog.dismiss();
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();

        return dialog;
    }

    private int obterSituacaoSIM(int situacao){
        if(situacao == SituacaoEnum.AGUARDANDOATENDIMENTO.getValue()){
            return SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum.ATENDIDO.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum.ATENDIMENTOCOMRESPOSTASDIVERGENTES.getValue();
        }else{

        }
    }
}
