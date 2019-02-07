package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class DetalheHistoricoFragment extends Fragment {

    private TextView txtTipoAtendimento;
    private TextView txtData;
    private TextView txtCategoria;
    private TextView txtCliente;
    private TextView txtProfissional;
    private TextView txtSituacao;

    private HistoricoAtendimento historicoAtendimento;

    private final String FORMATO_DATA = "dd/MM/yyyy hh:mm:ss";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        txtTipoAtendimento = (TextView) view.findViewById(R.id.txtTipoAtendimento);
        txtData = (TextView) view.findViewById(R.id.txtData);
        txtCategoria = (TextView) view.findViewById(R.id.txtCategoria);
        txtCliente = (TextView) view.findViewById(R.id.txtCliente);
        txtProfissional = (TextView) view.findViewById(R.id.txtProfissional);
        txtSituacao = (TextView) view.findViewById(R.id.txtSituacao);

        historicoAtendimento = VariaveisEstaticas.getHistoricoAtendimento();


        montarDetalheHistorico();

        return view;
    }

    private void montarDetalheHistorico(){

        txtTipoAtendimento.setText(obterTipoAtendimento(Integer.parseInt(historicoAtendimento.getTipoAtendimentoId() + "")));
        txtData.setText(FerramentasBasicas.converterDataParaString(historicoAtendimento.getData(), FORMATO_DATA ));
        txtCategoria.setText(historicoAtendimento.getCategoria().getDescricao());
        txtCliente.setText(historicoAtendimento.getCliente().getNome() + " " + historicoAtendimento.getCliente().getSobrenome());
        txtProfissional.setText(historicoAtendimento.getProfisisonal().getNome() + " " + historicoAtendimento.getProfisisonal().getSobrenome());
        txtSituacao.setText(obterSituacao(Integer.parseInt(historicoAtendimento.getSitucaoId() + "")));

    }

    private String obterTipoAtendimento(int codigo){

        switch (codigo){
            case 1:
                return "Ligação";
            case 2:
                return "Whatsapp";
            case 3:
                return "Me ligue";
        }
        return "";
    }

    private String obterSituacao(int codigo){

        switch (codigo){
            case 1:
                return "Aguardando Atendimento";
            case 2:
                return "Atendido";
            case 3:
                return "Trabalho Fechado";
            case 4:
                return "Trabalho Finalizado";
            case 5:
                return "Trabalho Não Foi Fechado";
        }
        return "";
    }
}
