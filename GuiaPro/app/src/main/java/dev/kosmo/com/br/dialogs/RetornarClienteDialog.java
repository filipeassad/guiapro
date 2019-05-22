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

import dev.kosmo.com.br.enuns.SituacaoEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.task.put.PutAtendimentoAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class RetornarClienteDialog {

    private Context context;
    private AtendimentoInterface atendimentoInterface;
    private final String URL_ATENDIMENTO_PROFISSIONAL_PUT = "mobile/atendimento_profissional/";
    private final String FORMATO_DATA = "dd/MM/yyyy";
    private PutAtendimentoAsyncTask putAtendimentoAsyncTask;

    public RetornarClienteDialog(Context context, AtendimentoInterface atendimentoInterface) {
        this.context = context;
        this.atendimentoInterface = atendimentoInterface;
    }

    public Dialog gerarDialog(final Atendimento atendimento){
        final Dialog dialog = new Dialog(this.context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_retornar_cliente);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvNome = (TextView) dialog.findViewById(R.id.tvNome);
        TextView tvCategoria = (TextView) dialog.findViewById(R.id.tvCategoria);
        TextView tvDataAtendimento = (TextView) dialog.findViewById(R.id.tvDataAtendimento);
        LinearLayout btnLigar = (LinearLayout) dialog.findViewById(R.id.btnLigar);
        LinearLayout btnWhats = (LinearLayout) dialog.findViewById(R.id.btnWhats);

        tvNome.setText(atendimento.getCliente().getNome() + " " + atendimento.getCliente().getSobrenome());
        tvCategoria.setText("Categoria: " + atendimento.getCategoria().getDescricao());
        tvDataAtendimento.setText("Data: " + FerramentasBasicas.converterDataParaString(atendimento.getData(), "dd/MM/yyyy"));

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putAtendimentoAsyncTask = new PutAtendimentoAsyncTask(context, montarJson(atendimento), atendimentoInterface);
                putAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI()
                                                    + URL_ATENDIMENTO_PROFISSIONAL_PUT
                                                    + atendimento.getId());
                FerramentasBasicas.fazerLigacao(atendimento.getCliente().getCelular());
                dialog.dismiss();
            }
        });

        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putAtendimentoAsyncTask = new PutAtendimentoAsyncTask(context, montarJson(atendimento), atendimentoInterface);
                putAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI()
                                                    + URL_ATENDIMENTO_PROFISSIONAL_PUT
                                                    + atendimento.getId());
                FerramentasBasicas.enviarWhats(context, atendimento.getCliente().getCelular());
                dialog.dismiss();
            }
        });

        dialog.show();

        return dialog;
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
            jsonObject.put("situacaoId", SituacaoEnum.AGUARDANDOATENDIMENTO.getValue());
            jsonObject.put("categoriaId", atendimento.getCategoriaId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
