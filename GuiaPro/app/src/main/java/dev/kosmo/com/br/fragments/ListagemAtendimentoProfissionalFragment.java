package dev.kosmo.com.br.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import dev.kosmo.com.br.dialogs.EntrarContatoDialog;
import dev.kosmo.com.br.dialogs.QuestionarioAtendimentoDialog;
import dev.kosmo.com.br.dialogs.RetornarClienteDialog;
import dev.kosmo.com.br.enuns.SituacaoEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoAdapterInterface;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.AtendimentoDao;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetAtendimentoAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class ListagemAtendimentoProfissionalFragment extends Fragment implements AtendimentoAdapterInterface, AtendimentoInterface {

    private LinearLayout abaSolicitacoes;
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

    private final String API_ATENDIMENTO = "mobile/atendimento_profissional/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listagem_atendimento_profissional, container, false);

        abaSolicitacoes = (LinearLayout) view.findViewById(R.id.abaSolicitacoes);
        tvSolicitacoes = (TextView) view.findViewById(R.id.tvSolicitacoes);
        tvAtendidos = (TextView) view.findViewById(R.id.tvAtendidos);
        tvMensagens = (TextView) view.findViewById(R.id.tvMensagens);
        lvTelaInicial = (ListView) view.findViewById(R.id.lvTelaInicial);
        llDetalheAtendimento = (LinearLayout) view.findViewById(R.id.llDetalheAtendimento);

        VariaveisEstaticas.getFragmentInterface().visibilidadeMenuProfissional(true);

        abaSolicitacoes.setTag("Solicitacoes");

        guiaProDao = (GuiaProDao) getActivity().getApplication();
        usuario = VariaveisEstaticas.getUsuario();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarAtendimentos();
    }

    private void buscarAtendimentos(){
        if(FerramentasBasicas.isOnline(getContext())){
            GetAtendimentoAsyncTask getAtendimentoAsyncTask = new GetAtendimentoAsyncTask(getContext(), atendimentoInterface);
            getAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_ATENDIMENTO + usuario.getPerfilId());
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
        verificarAtendimentos();
    }

    private void verificarAtendimentos(){

        for(Atendimento aux :listaAtendimento){
            int situacao = Integer.parseInt(aux.getSitucaoId() + "");

            if(situacao == SituacaoEnum.AGUARDANDOLIGACAO.getValue()){
                RetornarClienteDialog retornarClienteDialog = new RetornarClienteDialog(getContext(), atendimentoInterface);
                retornarClienteDialog.gerarDialog(aux);
                return;
            }
        }

        for(Atendimento aux :listaAtendimento){
            int situacao = Integer.parseInt(aux.getSitucaoId() + "");

            if(situacao == SituacaoEnum.ATENDIMENTOCONFIRMADOPELOCLIENTE.getValue()
                    || situacao == SituacaoEnum.ATENDIMENTONAOCONFIRMADOPELOCLIENTE.getValue()
                    || situacao == SituacaoEnum.CLIENTECONFIRMOUFECHAMENTODETRABALHO.getValue()
                    || situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFECHAMENTODETRABALHO.getValue()
                    || situacao == SituacaoEnum.CLIENTECONFIRMOUFINALIZACAODOTRABALHO.getValue()
                    || situacao == SituacaoEnum.CLIENTENAOCONFIRMOUFINALIZACAODOTRABALHO.getValue()){
                QuestionarioAtendimentoDialog questionarioAtendimentoDialog = new QuestionarioAtendimentoDialog(getContext(), atendimentoInterface);
                questionarioAtendimentoDialog.gerarDialog(aux);
                return;
            }
        }

    }

    @Override
    public void acessarDetalhe(Atendimento atendimento) {
        VariaveisEstaticas.setAtendimento(atendimento);
        VariaveisEstaticas.getFragmentInterface().mudaTela("DetalheAtendimento");
    }

    @Override
    public void entrarEmContato(Atendimento atendimento) {
        EntrarContatoDialog entrarContatoDialog = new EntrarContatoDialog(getContext());
        entrarContatoDialog.gerarDialog(atendimento);
    }

    @Override
    public void retornoCadastroAtendimento(boolean cadastrou, long idAtendimento) {

    }

    @Override
    public void retornoBuscaAtendimentos(List<Atendimento> atendimentos) {
        listaAtendimento = atendimentos;
        carregaAtendimentos();
    }

    @Override
    public void retornoAlteracaoAtendimentos(boolean cadastrou) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    VariaveisEstaticas.getFragmentInterface().voltar();
                }
                return;
            }
        }
    }
}
