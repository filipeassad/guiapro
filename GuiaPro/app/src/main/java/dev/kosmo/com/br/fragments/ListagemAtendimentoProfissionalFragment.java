package dev.kosmo.com.br.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import dev.kosmo.com.br.adapter.AtendimentoAdapter;
import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.AtendimentoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoAdapterInterface;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.AtendimentoDao;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetAtendimentoPorProfissionalAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class ListagemAtendimentoProfissionalFragment extends Fragment implements AtendimentoAdapterInterface, AtendimentoInterface {

    private LinearLayout abaSolicitacoes;
    private LinearLayout abaAtendidos;
    private LinearLayout abaMensagens;
    private TextView tvSolicitacoes;
    private TextView tvAtendidos;
    private TextView tvMensagens;

    private ListView lvTelaInicial;
    private LinearLayout llDetalheAtendimento;

    private List<Atendimento> listaAtendimento;
    private Usuario usuario;

    private AtendimentoAdapterInterface atendimentoAdapterInterface = this;
    private AtendimentoInterface atendimentoInterface = this;

    private GuiaProDao guiaProDao;

    private String ATENDIMENTO_NAO_ATENDIDO = "Não Atendido";
    private final long SITUACAO_ATENDIMENTO_AGUARDANDO = 1;
    private final String API_ATENDIMENTO = "atendimento_profissional";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listagem_atendimento_profissional, container, false);

        abaSolicitacoes = (LinearLayout) view.findViewById(R.id.abaSolicitacoes);
        abaAtendidos = (LinearLayout) view.findViewById(R.id.abaAtendidos);
        abaMensagens = (LinearLayout) view.findViewById(R.id.abaMensagens);
        tvSolicitacoes = (TextView) view.findViewById(R.id.tvSolicitacoes);
        tvAtendidos = (TextView) view.findViewById(R.id.tvAtendidos);
        tvMensagens = (TextView) view.findViewById(R.id.tvMensagens);
        lvTelaInicial = (ListView) view.findViewById(R.id.lvTelaInicial);
        llDetalheAtendimento = (LinearLayout) view.findViewById(R.id.llDetalheAtendimento);

        VariaveisEstaticas.getFragmentInterface().visibilidadeMenuProfissional(true);

        abaSolicitacoes.setTag("Solicitacoes");
        abaAtendidos.setTag("Atendidos");
        abaMensagens.setTag("Mensagens");

        abaSolicitacoes.setOnClickListener(abaOnCLickListener);
        abaAtendidos.setOnClickListener(abaOnCLickListener);
        abaMensagens.setOnClickListener(abaOnCLickListener);

        guiaProDao = (GuiaProDao) getActivity().getApplication();
        usuario = VariaveisEstaticas.getUsuario();

        buscarAtendimentos();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //verificaSolicitacoes();
    }

    private void buscarAtendimentos(){
        if(FerramentasBasicas.isOnline(getContext())){
            GetAtendimentoPorProfissionalAsyncTask getAtendimentoPorProfissionalAsyncTask = new GetAtendimentoPorProfissionalAsyncTask(getContext(), atendimentoInterface);
            getAtendimentoPorProfissionalAsyncTask.execute(FerramentasBasicas.getURL() + API_ATENDIMENTO);
        }else{
            AtendimentoDao.Properties propriedades = new AtendimentoDao.Properties();

            QueryBuilder<Atendimento> atendimentoQB = guiaProDao.getDaoSession()
                    .getAtendimentoDao().queryBuilder();
            atendimentoQB
                    .where(propriedades.ProfissionalId.eq(usuario.getPerfilId()));

            listaAtendimento = atendimentoQB.list();
            carregaAtendimentos();
        }
    }

    private void carregaAtendimentos(){
        AtendimentoAdapter atendimentoAdapter = new AtendimentoAdapter(getContext(),
                R.layout.adapter_atendimento,listaAtendimento,atendimentoAdapterInterface);

        lvTelaInicial.setAdapter(atendimentoAdapter);
    }

    private View.OnClickListener abaOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setBackGroundLinearLayout();

            ((LinearLayout)view).setBackgroundResource(R.drawable.menutopocinza);

            if(((String)view.getTag()).equals("Solicitacoes")){
                tvSolicitacoes.setTextColor(Color.parseColor("#bfbfbf"));
                /*listaAtendimento = populaAtendimentos.criaAtendimentosNaoAtendidos();
                AtendimentosAdapter atendimentosAdapter = new AtendimentosAdapter(getContext(),
                        R.layout.adapter_atendimento,listaAtendimento, atendimentoAdapterInterface);

                lvTelaInicial.setAdapter(atendimentosAdapter);*/

            }else if(((String)view.getTag()).equals("Atendidos")){
                tvAtendidos.setTextColor(Color.parseColor("#bfbfbf"));

            }else if(((String)view.getTag()).equals("Mensagens")){
                tvMensagens.setTextColor(Color.parseColor("#bfbfbf"));
            }
        }
    };

    private void setBackGroundLinearLayout(){

        abaSolicitacoes.setBackgroundResource(R.drawable.menutopolaranja);
        tvSolicitacoes.setTextColor(Color.parseColor("#fddeb3"));

        abaAtendidos.setBackgroundResource(R.drawable.menutopolaranja);
        tvAtendidos.setTextColor(Color.parseColor("#fddeb3"));

        abaMensagens.setBackgroundResource(R.drawable.menutopolaranja);
        tvMensagens.setTextColor(Color.parseColor("#fddeb3"));

    }

    private void carregaDetalhe(Atendimento atendimento){
        llDetalheAtendimento.setVisibility(View.VISIBLE);
        View view = View.inflate(getContext(), R.layout.fragment_detalhe_atendimento, null);
        llDetalheAtendimento.addView(view);
    }

    @Override
    public void acessarDetalhe(Atendimento atendimento) {
        VariaveisEstaticas.setAtendimento(atendimento);
        VariaveisEstaticas.getFragmentInterface().mudaTela("DetalheAtendimento");
    }

    private void verificaSolicitacoes(){
        if(!listaAtendimento.isEmpty()){
            Atendimento atendimento = buscarAtendimantoNaoAtendido(listaAtendimento);
            if(atendimento != null){
                AtendimentoDialog atendimentoDialog = new AtendimentoDialog(getContext());
                atendimentoDialog.gerarDialog(atendimento);
            }
        }
    }

    private Atendimento buscarAtendimantoNaoAtendido(List<Atendimento> listaAtendimentos){

        for(Atendimento atendimento : listaAtendimentos){
            if(atendimento.getSitucaoId() == SITUACAO_ATENDIMENTO_AGUARDANDO ){
                return atendimento;
            }
        }
        return null;
    }

    @Override
    public void retornoCadastroAtendimento(boolean cadastrou, long idAtendimento) {

    }

    @Override
    public void retornoBuscaAtendimentos(List<Atendimento> atendimentos) {
        listaAtendimento = atendimentos;
        carregaAtendimentos();
    }
}
