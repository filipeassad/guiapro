package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import dev.kosmo.com.br.adapter.ProfissionalAdapter;
import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.CategoriaPerfil;
import dev.kosmo.com.br.models.CategoriaPerfilDao;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetPerfisAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.StatusAplicativo;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class ListagemProfissionaisFragment extends Fragment implements PerfilInterface{

    private ListView lvProfissionais;
    private LinearLayout abaQualificacoes;
    private LinearLayout abaProximidade;
    private LinearLayout abaUrgencia;
    private ImageView ivQualificacao;
    private TextView tvQualificacao;
    private ImageView ivProximidade;
    private TextView tvProximidade;
    private TextView tvUrgencia1;
    private TextView tvUrgencia2;

    private StatusAplicativo statusAplicativo;
    private PerfilInterface perfilInterface = this;

    private Perfil perfilBusca;
    private Usuario usuario;
    private Categoria categoriaSelecionada;
    private GuiaProDao guiaProDao;

    private final String API_PERFIL_CATEGORIA = "perfil_categoria/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listagem_profissionais, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        lvProfissionais = (ListView) view.findViewById(R.id.lvProfissionais);
        abaQualificacoes = (LinearLayout) view.findViewById(R.id.abaQualificacoes);
        abaProximidade = (LinearLayout) view.findViewById(R.id.abaProximidade);
        abaUrgencia = (LinearLayout) view.findViewById(R.id.abaUrgencia);
        ivQualificacao = (ImageView) view.findViewById(R.id.ivQualificacao);
        tvQualificacao = (TextView) view.findViewById(R.id.tvQualificacao);
        ivProximidade = (ImageView) view.findViewById(R.id.ivProximidade);
        tvProximidade = (TextView) view.findViewById(R.id.tvProximidade);
        tvUrgencia1 = (TextView) view.findViewById(R.id.tvUrgencia1);
        tvUrgencia2 = (TextView) view.findViewById(R.id.tvUrgencia2);

        guiaProDao = (GuiaProDao) getActivity().getApplication();
        abaQualificacoes.setTag("Qualificacoes");
        abaProximidade.setTag("Proximidade");
        abaUrgencia.setTag("Urgencia");

        abaUrgencia.setOnClickListener(abaOnCLickListener);
        abaQualificacoes.setOnClickListener(abaOnCLickListener);
        abaProximidade.setOnClickListener(abaOnCLickListener);

        usuario = VariaveisEstaticas.getUsuario();
        categoriaSelecionada = VariaveisEstaticas.getCategoria();

        statusAplicativo = new StatusAplicativo(getContext());

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        if(statusAplicativo.isOnline()){
            GetPerfisAsyncTask getPerfisAsyncTask = new GetPerfisAsyncTask(getContext(), perfilInterface, usuario.getToken());
            getPerfisAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_PERFIL_CATEGORIA + categoriaSelecionada.getId());
        }else{
            buscaDB();
        }

    }

    private void buscaDB(){
        QueryBuilder<Perfil> perfilQueryBuilder = guiaProDao.getDaoSession()
                .getPerfilDao().queryBuilder();
        perfilQueryBuilder
                .join(CategoriaPerfil.class, CategoriaPerfilDao.Properties.PerfilId)
                .where(CategoriaPerfilDao.Properties.CategoriaId.eq(categoriaSelecionada.getId()));
        carregaPerfis(perfilQueryBuilder.list());
    }

    private void carregaPerfis(List<Perfil> perfis){

        ProfissionalAdapter profissionalAdapter = new ProfissionalAdapter(getContext(), R.layout.adapter_list_prof, perfis);
        lvProfissionais.setAdapter(profissionalAdapter);

        lvProfissionais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VariaveisEstaticas.setProfissional((Perfil) adapterView.getItemAtPosition(i));
                VariaveisEstaticas.getFragmentInterface().mudaTela("DetalheProfissional");
            }
        });

    }

    private View.OnClickListener abaOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setBackGroundLinearLayout();
            ((LinearLayout)view).setBackgroundResource(R.drawable.menutopocinza);

            if(((String)view.getTag()).equals("Qualificacoes")){
                ivQualificacao.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.trophycinza));
                tvQualificacao.setTextColor(Color.parseColor("#bfbfbf"));

            }else if(((String)view.getTag()).equals("Proximidade")){
                ivProximidade.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.locationcinza));
                tvProximidade.setTextColor(Color.parseColor("#bfbfbf"));

            }else if(((String)view.getTag()).equals("Urgencia")){
                tvUrgencia1.setTextColor(Color.parseColor("#bfbfbf"));
                tvUrgencia2.setTextColor(Color.parseColor("#bfbfbf"));
            }
        }
    };


    private void setBackGroundLinearLayout(){

        abaQualificacoes.setBackgroundResource(R.drawable.menutopolaranja);
        ivQualificacao.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.trophylaranja));
        tvQualificacao.setTextColor(Color.parseColor("#fddeb3"));

        abaProximidade.setBackgroundResource(R.drawable.menutopolaranja);
        ivProximidade.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.locationlaranja));
        tvProximidade.setTextColor(Color.parseColor("#fddeb3"));

        abaUrgencia.setBackgroundResource(R.drawable.menutopolaranja);
        tvUrgencia1.setTextColor(Color.parseColor("#fddeb3"));
        tvUrgencia2.setTextColor(Color.parseColor("#fddeb3"));
    }

    @Override
    public void getPerfil(Perfil perfil) {

    }

    @Override
    public void getPerfis(List<Perfil> perfis) {
        for(Perfil perfil : perfis){
            List<CategoriaPerfil> categoriasPerfil = guiaProDao.getDaoSession()
                    .getCategoriaPerfilDao().queryBuilder()
                    .where(CategoriaPerfilDao.Properties.PerfilId.eq(perfil.getId())).list();

            for(CategoriaPerfil categoriaPerfil : categoriasPerfil){
                guiaProDao.getDaoSession().getCategoriaPerfilDao().delete(categoriaPerfil);
            }
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(perfil);
            guiaProDao.getDaoSession().getEnderecoDao().insertOrReplace(perfil.getEndereco());
            guiaProDao.getDaoSession().getTipoPerfilDao().insertOrReplace(perfil.getTipoPerfil());

            for(Categoria categoria: perfil.getCategoriasInsercao()){
                guiaProDao.getDaoSession().getCategoriaDao().insertOrReplace(categoria);

                CategoriaPerfil categoriaPerfil = new CategoriaPerfil();
                categoriaPerfil.setCategoriaId(categoria.getId());
                categoriaPerfil.setPerfilId(perfil.getId());

                guiaProDao.getDaoSession().getCategoriaPerfilDao().insert(categoriaPerfil);
            }
        }
        carregaPerfis(perfis);
    }
}
