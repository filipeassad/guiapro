package dev.kosmo.com.br.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import dev.kosmo.com.br.enuns.SituacaoEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.task.put.PutAtendimentoAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class QuestionarioAtendimentoDialog {

    private Context context;
    private AtendimentoInterface atendimentoInterface;
    private PutAtendimentoAsyncTask putAtendimentoAsyncTask;
    private final String URL_ATENDIMENTO_CLIENTE_PUT = "mobile/atendimento_cliente/";
    private final String URL_ATENDIMENTO_PROFISSIONAL_PUT = "mobile/atendimento_profissional/";
    private final String FORMATO_DATA = "dd/MM/yyyy";


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
        final int situacao = Integer.parseInt(atendimento.getSitucaoId() + "");

        tvCategoria.setText("Categoria: " + atendimento.getCategoria().getDescricao());
        tvDataAtendimento.setText("Data: " + FerramentasBasicas.converterDataParaString(atendimento.getData(), "dd/MM/yyyy"));

        if(situacao == SituacaoEnum
                .AGUARDANDOATENDIMENTO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("O profissional te atendeu ?");
        }else if(situacao == SituacaoEnum
                .ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()
                || situacao == SituacaoEnum
                .ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()){
            tvNome.setText(atendimento.getCliente().getNome() + " " + atendimento.getCliente().getSobrenome());
            tvMensagem.setText("O cliente te ligou, você o atendeu ?");
        }else if(situacao == SituacaoEnum
                .ATENDIDO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o trabalho com o profissional ?");
        }else if(situacao == SituacaoEnum
                .CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue()
                || situacao == SituacaoEnum
                .CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o trabalho com o cliente ?");
        }else if(situacao == SituacaoEnum
                .TRABALHOFECHADO.getValue()) {
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("O profissional finalizou o serviço ?\n");
        }else if(situacao == SituacaoEnum
                .CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue()
                || situacao == SituacaoEnum
                .CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            tvNome.setText(atendimento.getProfisisonal().getNome() + " " + atendimento.getProfisisonal().getSobrenome());
            tvMensagem.setText("Você fechou o finalizou com o cliente ?");
        }

        LinearLayout btnSim = (LinearLayout) dialog.findViewById(R.id.btnSim);
        LinearLayout btnNao = (LinearLayout) dialog.findViewById(R.id.btnNao);

        atendimento.setData(new Date());

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atendimento.setSitucaoId(obterSituacaoSIM(situacao));
                putAtendimentoAsyncTask = new PutAtendimentoAsyncTask(context, montarJson(atendimento), atendimentoInterface);
                putAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() +
                        (VariaveisEstaticas.getUsuario().getPerfil().getTipoPerfilId() == 1 ?
                        URL_ATENDIMENTO_CLIENTE_PUT : URL_ATENDIMENTO_PROFISSIONAL_PUT)
                        + atendimento.getId());
                dialog.dismiss();
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atendimento.setSitucaoId(obterSituacaoNAO(situacao));
                putAtendimentoAsyncTask = new PutAtendimentoAsyncTask(context, montarJson(atendimento), atendimentoInterface);
                putAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() +
                        (VariaveisEstaticas.getUsuario().getPerfil().getTipoPerfilId() == 1 ?
                                URL_ATENDIMENTO_CLIENTE_PUT : URL_ATENDIMENTO_PROFISSIONAL_PUT)
                        + atendimento.getId());
                dialog.dismiss();
            }
        });

        dialog.show();

        return dialog;
    }

    private int obterSituacaoSIM(int situacao){
        if(situacao == SituacaoEnum.AGUARDANDOATENDIMENTO.getValue()){
            return SituacaoEnum
                    .ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum
                    .ATENDIDO.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum
                    .ATENDIMENTOCOMRESPOSTASDIVERGENTES.getValue();
        }else if(situacao == SituacaoEnum.ATENDIDO.getValue()){
            return SituacaoEnum
                    .CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue()){
            return SituacaoEnum
                    .TRABALHOFECHADO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue()){
            return SituacaoEnum
                    .FECHAMENTODETRABALHOCOMRESPOSTASDIVERGENTES.getValue();
        }else if(situacao == SituacaoEnum.TRABALHOFECHADO.getValue()){
            return SituacaoEnum
                    .CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            return SituacaoEnum
                    .TRABALHOFINALIZADO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            return SituacaoEnum
                    .FINALIZACAODOTRABALHOCOMRESPOSTASDIVERGENTES.getValue();
        }

        return 0;
    }

    private int obterSituacaoNAO(int situacao){
        if(situacao == SituacaoEnum.AGUARDANDOATENDIMENTO.getValue()){
            return SituacaoEnum
                    .ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum
                    .ATENDIMENTOCOMRESPOSTASDIVERGENTES.getValue();
        }else if(situacao == SituacaoEnum.ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()){
            return SituacaoEnum
                    .NAOATENDIDO.getValue();
        }else if(situacao == SituacaoEnum.ATENDIDO.getValue()){
            return SituacaoEnum
                    .CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue()){
            return SituacaoEnum
                    .FECHAMENTODETRABALHOCOMRESPOSTASDIVERGENTES.getValue();
        }else if(situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue()){
            return SituacaoEnum
                    .TRABALHONAOFECHADO.getValue();
        }else if(situacao == SituacaoEnum.TRABALHOFECHADO.getValue()){
            return SituacaoEnum
                    .CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue();
        }else if(situacao == SituacaoEnum.CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            return SituacaoEnum
                    .FINALIZACAODOTRABALHOCOMRESPOSTASDIVERGENTES.getValue();
        }else if(situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue()){
            return SituacaoEnum
                    .TRABALHONAOFINALIZADO.getValue();
        }

        return 0;
    }

    private JSONObject montarJson(Atendimento atendimento){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", atendimento.getId());
            jsonObject.put("data", FerramentasBasicas.converterDataParaString(atendimento.getData(), FORMATO_DATA));
            jsonObject.put("titulo", atendimento.getTitulo());
            jsonObject.put("descricao", atendimento.getDescricao());
            jsonObject.put("clienteId", atendimento.getClienteId());
            jsonObject.put("profissionalId", atendimento.getProfissionalId());
            jsonObject.put("tipoatendimentoId", atendimento.getTipoAtendimentoId());
            jsonObject.put("situacaoId", atendimento.getSitucaoId());
            jsonObject.put("categoriaId", atendimento.getCategoriaId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
