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
import dev.kosmo.com.br.interfaces.AtendimentoAdapterInterface;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.AtendimentoDao;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetAtendimentoPorClienteAsyncTask;
import dev.kosmo.com.br.utils.ConversaoTexto;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 26/03/2018.
 */

public class HistoricoFragment extends Fragment implements AtendimentoInterface {

    private List<String> lista;
    private LinearLayout listaHistorico;
    private ConversaoTexto conversaoTexto;
    private GuiaProDao guiaProDao;
    private AtendimentoInterface atendimentoInterface = this;
    private Usuario usuario;

    private final String URL_ATENDIMENTO_CLIENTE = "mobile/atendimento_cliente/";
    private final String URL_ATENDIMENTO_PROFISSIONAL = "mobile/atendimento_profissional/";
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
            GetAtendimentoPorClienteAsyncTask getAtendimentoPorClienteAsyncTask = new GetAtendimentoPorClienteAsyncTask(getContext(), atendimentoInterface);
            if(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL)
                getAtendimentoPorClienteAsyncTask.execute(FerramentasBasicas.getURL() + URL_ATENDIMENTO_PROFISSIONAL + usuario.getPerfil().getId());
            else
                getAtendimentoPorClienteAsyncTask.execute(FerramentasBasicas.getURL() + URL_ATENDIMENTO_CLIENTE + usuario.getPerfil().getId());
        }else{
            AtendimentoDao.Properties propriedades = new AtendimentoDao.Properties();

            QueryBuilder<Atendimento> atendimentoQB = guiaProDao.getDaoSession()
                    .getAtendimentoDao().queryBuilder();
            atendimentoQB
                    .where(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL ?
                            propriedades.ProfissionalId.eq(usuario.getPerfil().getId())
                            : propriedades.ClienteId.eq(usuario.getPerfil().getId()));

            List<Atendimento> listaAtendimento = atendimentoQB.list();
            carregarAtendimentos(listaAtendimento);
        }
    }

    @Override
    public void retornoCadastroAtendimento(boolean cadastrou, long idAtendimento) {

    }

    @Override
    public void retornoBuscaAtendimentos(List<Atendimento> atendimentos) {
        for(Atendimento aux :atendimentos){
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getCliente());
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getProfisisonal());
            guiaProDao.getDaoSession().getCategoriaDao().insertOrReplace(aux.getCategoria());
            guiaProDao.getDaoSession().getAtendimentoDao().insertOrReplace(aux);
        }
        carregarAtendimentos(atendimentos);
    }

    private void carregarAtendimentos(List<Atendimento> atendimentos){
        for(Atendimento atendimento : atendimentos){
            LinearLayout llHistorico = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.adapter_historico_cliente,null);
            ImageView ivHistorico = (ImageView) llHistorico.findViewById(R.id.ivHistorico);
            TextView tvHistorico = (TextView) llHistorico.findViewById(R.id.tvHistorico);

            tvHistorico.setText(montarMensagemHistorico(atendimento));

            listaHistorico.addView(llHistorico);
        }
    }

    private String montarMensagemHistorico(Atendimento atendimento){
        String msg = "";

        switch ((int)atendimento.getTipoAtendimentoId()){
            case 1: //Ligação
                msg = montarMensagemLigacao(atendimento);
                break;
            case 2: //Whatsapp
                msg = montarMensagemWhatsapp(atendimento);
                break;
            case 3: //Me ligue
                msg = montarMensagemMeligue(atendimento);
                break;
        }
        return msg;
    }

    private String montarMensagemLigacao(Atendimento atendimento){
        String msg = "";
        switch ((int)atendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = atendimento.getCliente().getNome() + " entrou em contato por ligação com " + atendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = atendimento.getProfisisonal().getNome() + " retornou por ligação o chamado de " + atendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = atendimento.getCliente().getNome() + " fechou um trabalho com " + atendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = atendimento.getProfisisonal().getNome() + " finalizou trabalho com " + atendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + atendimento.getCliente().getNome() + " e " + atendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }

    private String montarMensagemWhatsapp(Atendimento atendimento){
        String msg = "";
        switch ((int)atendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = atendimento.getCliente().getNome() + " entrou em contato por whatsapp com " + atendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = atendimento.getProfisisonal().getNome() + " retornou por whatsapp o chamado de " + atendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = atendimento.getCliente().getNome() + " fechou um trabalho com " + atendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = atendimento.getProfisisonal().getNome() + " finalizou trabalho com " + atendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + atendimento.getCliente().getNome() + " e " + atendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }

    private String montarMensagemMeligue(Atendimento atendimento){
        String msg = "";
        switch ((int)atendimento.getSitucaoId()){
            case 1: //Aguardando Atendimento
                msg = atendimento.getCliente().getNome() + " solicitou retorno de chamada para " + atendimento.getProfisisonal().getNome();
                break;
            case 2: //Atendido
                msg = atendimento.getProfisisonal().getNome() + " retornou o chamado de " + atendimento.getCliente().getNome();
                break;
            case 3: //Trabalho Fechado
                msg = atendimento.getCliente().getNome() + " fechou um trabalho com " + atendimento.getProfisisonal().getNome();
                break;
            case 4: //Trabalho Finalizado
                msg = atendimento.getProfisisonal().getNome() + " finalizou trabalho com " + atendimento.getCliente().getNome();
                break;
            case 5: //Trabalho Não Foi Fechado
                msg = "O trabalho não foi fechado entre " + atendimento.getCliente().getNome() + " e " + atendimento.getProfisisonal().getNome();
                break;
        }
        return msg;
    }
}
