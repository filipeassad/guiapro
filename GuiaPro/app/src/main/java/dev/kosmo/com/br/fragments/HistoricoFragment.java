package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
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
import dev.kosmo.com.br.enuns.TipoPerfilEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.HistoricoAtendimentoInterface;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.HistoricoAtendimentoDao;
import dev.kosmo.com.br.models.Usuario;
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
    private final String NOME_TELA_DETALHE_HISTORICO = "DetalheHistorico";

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
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI() + URL_HISTORICO_ATENDIMENTO_PROFISSIONAL + usuario.getPerfil().getId());
            else
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI() + URL_HISTORICO_ATENDIMENTO_CLIENTE + usuario.getPerfil().getId());
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

            TextView tvNome = (TextView) llHistorico.findViewById(R.id.tvNome);
            TextView tvData = (TextView) llHistorico.findViewById(R.id.tvData);
            TextView tvCategoria = (TextView) llHistorico.findViewById(R.id.tvCategoria);
            TextView tvSituacao = (TextView) llHistorico.findViewById(R.id.tvSituacao);
            TextView tvTipoAtendimento = (TextView) llHistorico.findViewById(R.id.tvTipoAtendimento);

            Usuario usuario = VariaveisEstaticas.getUsuario();

            tvNome.setText(usuario.getPerfil().getTipoPerfilId() == TipoPerfilEnum.CLIENTE.getValue() ?
                            aux.getProfisisonal().getNome() + " " + aux.getProfisisonal().getSobrenome() :
                            aux.getCliente().getNome() + " " + aux.getCliente().getSobrenome());
            tvData.setText("Data: " + FerramentasBasicas.converterDataParaString(aux.getData(), "dd/MM/yyyy hh:mm:ss"));
            tvCategoria.setText("Categoria: " + aux.getCategoria().getDescricao());
            tvSituacao.setText("Situação: " + FerramentasBasicas.obterSituacao(Integer.parseInt(aux.getSitucaoId() + "" )));
            tvTipoAtendimento.setText("Tipo Atendimento: " + FerramentasBasicas.obterTipoAtendimento(Integer.parseInt(aux.getTipoAtendimentoId() + "")));

            if(usuario.getPerfil().getTipoPerfilId() == TipoPerfilEnum.CLIENTE.getValue()){
                if(aux.getProfisisonal().getUrlImg() == null
                        || aux.getProfisisonal().getUrlImg().trim().equals("")){
                    ivHistorico.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manuser));
                }else{
                    ivHistorico.setImageBitmap(FerramentasBasicas.getBitmapFromURL(aux.getProfisisonal().getUrlImg()));
                }
            }else{
                if(aux.getCliente().getUrlImg() == null
                        || aux.getCliente().getUrlImg().trim().equals("")){
                    ivHistorico.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manuser));
                }else{
                    ivHistorico.setImageBitmap(FerramentasBasicas.getBitmapFromURL(aux.getCliente().getUrlImg()));
                }
            }

            llHistorico.setTag(aux);

            llHistorico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout llMaisInformacoes = (LinearLayout) view.findViewById(R.id.llMaisInformacoes);
                    if(llMaisInformacoes.getVisibility() == View.VISIBLE)
                        llMaisInformacoes.setVisibility(View.GONE);
                    else
                        llMaisInformacoes.setVisibility(View.VISIBLE);
                    /*VariaveisEstaticas.setHistoricoAtendimento((HistoricoAtendimento) view.getTag());
                    VariaveisEstaticas.getFragmentInterface().mudaTela(NOME_TELA_DETALHE_HISTORICO);*/
                }
            });

            listaHistorico.addView(llHistorico);
        }
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
