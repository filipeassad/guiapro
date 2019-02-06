package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.interfaces.HistoricoAtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.AtendimentoDao;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.HistoricoAtendimentoDao;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetAtendimentoAsyncTask;
import dev.kosmo.com.br.task.gets.GetHistoricoAtendimentoAsyncTask;
import dev.kosmo.com.br.utils.ConversaoTexto;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 26/03/2018.
 */

public class HistoricoFragment extends Fragment implements HistoricoAtendimentoInterface {

    private List<String> lista;
    private LinearLayout listaHistorico;
    private ConversaoTexto conversaoTexto;
    private GuiaProDao guiaProDao;
    private HistoricoAtendimentoInterface historicoAtendimentoInterface = this;
    private Usuario usuario;

    private final String URL_HISTORICO_ATENDIMENTO_CLIENTE = "mobile/historico_atendimento_cliente/";
    private final String URL_HISTORICO_ATENDIMENTO_PROFISSIONAL = "mobile/historico_atendimento_profissional/";
    private final long TIPO_PERFIL_PROFISSIONAL = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        if(VariaveisEstaticas.getUsuario().getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL)
            VariaveisEstaticas.getFragmentInterface().visibilidadeMenuProfissional(true);
        else
            VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        listaHistorico = (LinearLayout) view.findViewById(R.id.listaHistorico);
        conversaoTexto = new ConversaoTexto();

        usuario = VariaveisEstaticas.getUsuario();
        guiaProDao = (GuiaProDao) getActivity().getApplication();

        carregaHistorico();

        return view;
    }

    private void carregaHistorico(){
        if(FerramentasBasicas.isOnline(getContext())){
            GetHistoricoAtendimentoAsyncTask getHistoricoAtendimentoAsyncTask = new GetHistoricoAtendimentoAsyncTask(getContext(), historicoAtendimentoInterface);
            if(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL)
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() + URL_HISTORICO_ATENDIMENTO_PROFISSIONAL + usuario.getPerfil().getId());
            else
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() + URL_HISTORICO_ATENDIMENTO_CLIENTE + usuario.getPerfil().getId());
        }else{
            HistoricoAtendimentoDao.Properties propriedades = new HistoricoAtendimentoDao.Properties();

            QueryBuilder<HistoricoAtendimento> historicoAtendimentoQB = guiaProDao.getDaoSession()
                    .getHistoricoAtendimentoDao().queryBuilder();
            historicoAtendimentoQB
                    .where(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL ?
                            propriedades.ProfissionalId.eq(usuario.getPerfil().getId())
                            : propriedades.ClienteId.eq(usuario.getPerfil().getId()));

            List<HistoricoAtendimento> historicosAtendimento = historicoAtendimentoQB.list();
            carregarHistoricosAtendimento(historicosAtendimento);
        }
    }

    private void carregarHistoricosAtendimento(List<HistoricoAtendimento> historicoAtendimentos){
        for(HistoricoAtendimento aux : historicoAtendimentos){
            LinearLayout llHistorico = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.adapter_historico_cliente,null);
            ImageView ivHistorico = (ImageView) llHistorico.findViewById(R.id.ivHistorico);
            TextView tvHistorico = (TextView) llHistorico.findViewById(R.id.tvHistorico);

            tvHistorico.setText(montarMensagemHistorico(aux));

            listaHistorico.addView(llHistorico);
        }
    }

    private String montarMensagemHistorico(HistoricoAtendimento historicoAtendimento){
        String msg = "";

        switch ((int)historicoAtendimento.getTipoAtendimentoId()){
            case 1: //Ligação
                msg = montarMensagemLigacao(historicoAtendimento);
                break;
            case 2: //Whatsapp
                msg = montarMensagemWhatsapp(historicoAtendimento);
                break;
            case 3: //Me ligue
                msg = montarMensagemMeligue(historicoAtendimento);
                break;
        }
        return msg;
    }

    private String montarMensagemLigacao(HistoricoAtendimento historicoAtendimento){
        String msg = "";
        switch ((int)historicoAtendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = historicoAtendimento.getCliente().getNome() + " entrou em contato por ligação com " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = historicoAtendimento.getProfisisonal().getNome() + " retornou por ligação o chamado de " + historicoAtendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = historicoAtendimento.getCliente().getNome() + " fechou um trabalho com " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = historicoAtendimento.getProfisisonal().getNome() + " finalizou trabalho com " + historicoAtendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + historicoAtendimento.getCliente().getNome() + " e " + historicoAtendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }

    private String montarMensagemWhatsapp(HistoricoAtendimento historicoAtendimento){
        String msg = "";
        switch ((int)historicoAtendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = historicoAtendimento.getCliente().getNome() + " entrou em contato por whatsapp com " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = historicoAtendimento.getProfisisonal().getNome() + " retornou por whatsapp o chamado de " + historicoAtendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = historicoAtendimento.getCliente().getNome() + " fechou um trabalho com " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = historicoAtendimento.getProfisisonal().getNome() + " finalizou trabalho com " + historicoAtendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + historicoAtendimento.getCliente().getNome() + " e " + historicoAtendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }

    private String montarMensagemMeligue(HistoricoAtendimento historicoAtendimento){
        String msg = "";
        switch ((int)historicoAtendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = historicoAtendimento.getCliente().getNome() + " solicitou retorno de chamada para " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = historicoAtendimento.getProfisisonal().getNome() + " retornou o chamado de " + historicoAtendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = historicoAtendimento.getCliente().getNome() + " fechou um trabalho com " + historicoAtendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = historicoAtendimento.getProfisisonal().getNome() + " finalizou trabalho com " + historicoAtendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + historicoAtendimento.getCliente().getNome() + " e " + historicoAtendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }

    @Override
    public void retornoCadastroHistoricoAtendimento(boolean cadastrou, long idHistoricoAtendimento) {

    }

    @Override
    public void retornoBuscaHistoricosAtendimento(List<HistoricoAtendimento> historicosAtendimento) {
        for(HistoricoAtendimento aux :historicosAtendimento){
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getCliente());
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getProfisisonal());
            guiaProDao.getDaoSession().getCategoriaDao().insertOrReplace(aux.getCategoria());
            guiaProDao.getDaoSession().getAtendimentoDao().insertOrReplace(aux.getAtendimento());
            guiaProDao.getDaoSession().getHistoricoAtendimentoDao().insertOrReplace(aux);
        }
        carregarHistoricosAtendimento(historicosAtendimento);
    }
}
