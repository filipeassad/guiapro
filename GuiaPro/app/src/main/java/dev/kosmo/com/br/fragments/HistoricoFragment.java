package dev.kosmo.com.br.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.enuns.TipoPerfilEnum;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.HistoricoAtendimentoInterface;
import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.HistoricoAtendimentoDao;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.models.sistema.GrupoHistoricoAtendimento;
import dev.kosmo.com.br.task.gets.GetHistoricoAtendimentoAsyncTask;
import dev.kosmo.com.br.task.gets.GetImagemAsyncTask;
import dev.kosmo.com.br.utils.ConversaoTexto;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 26/03/2018.
 */

public class HistoricoFragment extends Fragment implements HistoricoAtendimentoInterface, ImagemInterface {

    private List<String> lista;
    private LinearLayout listaHistorico;
    private ConversaoTexto conversaoTexto;
    private GuiaProDao guiaProDao;
    private HistoricoAtendimentoInterface historicoAtendimentoInterface = this;
    private Usuario usuario;

    private final String URL_HISTORICO_ATENDIMENTO_CLIENTE =
            "mobile/historico_atendimento_cliente/";
    private final String URL_HISTORICO_ATENDIMENTO_PROFISSIONAL =
            "mobile/historico_atendimento_profissional/";
    private final long TIPO_PERFIL_PROFISSIONAL = 2;

    private ImagemInterface imagemInterface = this;
    private List<GrupoHistoricoAtendimento> gruposHistoricosAtendimentoBuscaImagem;
    private List<GrupoHistoricoAtendimento> gruposHistoricosAtendimento;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        if(VariaveisEstaticas.getUsuario()
                .getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL)
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

            GetHistoricoAtendimentoAsyncTask getHistoricoAtendimentoAsyncTask =
                    new GetHistoricoAtendimentoAsyncTask(getContext(),
                            historicoAtendimentoInterface);

            if(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL)
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI()
                        + URL_HISTORICO_ATENDIMENTO_PROFISSIONAL
                        + usuario.getPerfil().getId());
            else
                getHistoricoAtendimentoAsyncTask.execute(FerramentasBasicas.getURLAPI()
                        + URL_HISTORICO_ATENDIMENTO_CLIENTE + usuario.getPerfil().getId());
        }else{
            Toast.makeText(getContext(), "Sem acesso a internet!", Toast.LENGTH_SHORT).show();
            /*HistoricoAtendimentoDao.Properties propriedades
                    = new HistoricoAtendimentoDao.Properties();

            QueryBuilder<HistoricoAtendimento> historicoAtendimentoQB = guiaProDao.getDaoSession()
                    .getHistoricoAtendimentoDao().queryBuilder();
            historicoAtendimentoQB
                    .where(usuario.getPerfil().getTipoPerfilId() == TIPO_PERFIL_PROFISSIONAL ?
                            propriedades.ProfissionalId.eq(usuario.getPerfil().getId())
                            : propriedades.ClienteId.eq(usuario.getPerfil().getId()));

            List<HistoricoAtendimento> historicosAtendimento = historicoAtendimentoQB.list();
            carregarHistoricosAtendimento(historicosAtendimento);*/
        }
    }

    private void montarAdapterGrupoHistoricoAtendimento(List<GrupoHistoricoAtendimento> gruposHistorico){

        listaHistorico.removeAllViews();
        for(GrupoHistoricoAtendimento grupoHistoricoAtendimento : gruposHistorico){

            LinearLayout adapterGrupo = (LinearLayout) getActivity().getLayoutInflater()
                    .inflate(R.layout.adapter_grupo_historico_atendimento, null);

            TextView tvNomePerfilGrupo = (TextView) adapterGrupo.findViewById(R.id.tvNomePerfilGrupo);
            LinearLayout llMaisInformacoes = (LinearLayout) adapterGrupo.findViewById(R.id.llMaisInformacoes);
            ImageView ivPerfilGrupo = (ImageView) adapterGrupo.findViewById(R.id.ivPerfilGrupo);

            Perfil perfilGrupo = grupoHistoricoAtendimento.getPerfilGrupo();
            tvNomePerfilGrupo.setText(perfilGrupo.getNome() + " " + perfilGrupo.getSobrenome());
            ivPerfilGrupo.setImageBitmap(perfilGrupo.getImagemBaixada());

            for(HistoricoAtendimento historicoAtendimento : grupoHistoricoAtendimento.getHistoricosAtendimento()){
                llMaisInformacoes.addView(montarHistoricoAtendimento(historicoAtendimento));
            }

            adapterGrupo.animate()
                    .translationY(adapterGrupo.getHeight())
                    .setDuration(3000);

            adapterGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout llMaisInformacoes = (LinearLayout) view.findViewById(R.id.llMaisInformacoes);
                    Button btnAmpliar = (Button) view.findViewById(R.id.btnAmpliar);

                    if(llMaisInformacoes.getVisibility() == View.VISIBLE){
                        llMaisInformacoes.setVisibility(View.GONE);
                        btnAmpliar.setText("+");
                    }else{
                        llMaisInformacoes.setVisibility(View.VISIBLE);
                        btnAmpliar.setText("-");
                    }
                }
            });

            listaHistorico.addView(adapterGrupo);
        }
    }

    private LinearLayout montarHistoricoAtendimento(HistoricoAtendimento historicoAtendimento){

        LinearLayout llHistorico = (LinearLayout) getActivity()
                .getLayoutInflater()
                .inflate(R.layout.adapter_historico_cliente,null);

        TextView tvData = (TextView) llHistorico.findViewById(R.id.tvData);
        TextView tvCategoria = (TextView) llHistorico.findViewById(R.id.tvCategoria);
        TextView tvSituacao = (TextView) llHistorico.findViewById(R.id.tvSituacao);
        TextView tvTipoAtendimento =
                (TextView) llHistorico.findViewById(R.id.tvTipoAtendimento);

        tvData.setText("Data: "
                + FerramentasBasicas.converterDataParaString(historicoAtendimento.getData(),
                "dd/MM/yyyy hh:mm:ss"));

        tvCategoria.setText("Categoria: " + historicoAtendimento.getCategoria().getDescricao());
        tvSituacao.setText("Situação: "
                + FerramentasBasicas
                .obterSituacao(Integer.parseInt(historicoAtendimento.getSitucaoId() + "" )));
        tvTipoAtendimento.setText("Tipo Atendimento: "
                + FerramentasBasicas
                .obterTipoAtendimento(Integer.parseInt(historicoAtendimento.getTipoAtendimentoId() + "")));

        return llHistorico;
    }

    @Override
    public void retornoCadastroHistoricoAtendimento(boolean cadastrou,
                                                    long idHistoricoAtendimento) {

    }

    @Override
    public void retornoBuscaHistoricosAtendimento(
            List<HistoricoAtendimento> historicosAtendimento) {

        for(HistoricoAtendimento aux :historicosAtendimento){
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getCliente());
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(aux.getProfisisonal());
            guiaProDao.getDaoSession().getCategoriaDao().insertOrReplace(aux.getCategoria());
            guiaProDao.getDaoSession().getAtendimentoDao().insertOrReplace(aux.getAtendimento());
            guiaProDao.getDaoSession().getHistoricoAtendimentoDao().insertOrReplace(aux);
        }

        gruposHistoricosAtendimentoBuscaImagem = FerramentasBasicas.montarListaGruposHistoricosAtendimento(historicosAtendimento);
        gruposHistoricosAtendimento = new ArrayList<>();

        if(gruposHistoricosAtendimentoBuscaImagem != null
                && gruposHistoricosAtendimentoBuscaImagem.size() > 0){
            GrupoHistoricoAtendimento grupoSelecionado = gruposHistoricosAtendimentoBuscaImagem.remove(0);
            gruposHistoricosAtendimento.add(grupoSelecionado);
            GetImagemAsyncTask getImagemAsyncTask = new GetImagemAsyncTask(getContext(), imagemInterface);
            getImagemAsyncTask.execute(grupoSelecionado.getPerfilGrupo().getUrlImg());
        }
        //carregarHistoricosAtendimento(historicosAtendimento);
    }

    @Override
    public void getImagem(Bitmap imagem) {
        GrupoHistoricoAtendimento ultimoGrupo = gruposHistoricosAtendimento.get(gruposHistoricosAtendimento.size() -1);

        if(imagem != null)
            ultimoGrupo.getPerfilGrupo().setImagemBaixada(imagem);
        else
            ultimoGrupo.getPerfilGrupo().setImagemBaixada(BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.manuser));

        if(gruposHistoricosAtendimentoBuscaImagem.size() > 0){
            GrupoHistoricoAtendimento grupoSelecionado = gruposHistoricosAtendimentoBuscaImagem.remove(0);
            gruposHistoricosAtendimento.add(grupoSelecionado);
            GetImagemAsyncTask getImagemAsyncTask = new GetImagemAsyncTask(getContext(), imagemInterface);
            getImagemAsyncTask.execute(grupoSelecionado.getPerfilGrupo().getUrlImg());
        }else{
            montarAdapterGrupoHistoricoAtendimento(gruposHistoricosAtendimento);
        }
    }

    @Override
    public void setImagem(Bitmap imagem) {

    }

    @Override
    public void retornoPostImagem(boolean cadastrou, String urlImagem) {

    }
}