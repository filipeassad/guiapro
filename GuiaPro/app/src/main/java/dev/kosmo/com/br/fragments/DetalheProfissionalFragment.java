package dev.kosmo.com.br.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dev.kosmo.com.br.adapter.CategoriaProfissionalAdapter;
import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.interfaces.NotificacaoPostInterface;
import dev.kosmo.com.br.interfaces.NotificacaoProfissionalInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.AtendimentoDao;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.CategoriaPerfil;
import dev.kosmo.com.br.models.CategoriaPerfilDao;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.PerfilDao;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetPushNotificationNodeAsyncTask;
import dev.kosmo.com.br.task.posts.PostCadastrarAtendimentoAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class DetalheProfissionalFragment extends Fragment implements OnMapReadyCallback, AtendimentoInterface, NotificacaoProfissionalInterface{

    private TextView tvAtendeLocalidade;
    private TextView tvNomeProfissional;
    private ImageView ivImagem;
    private MapView map;
    private GoogleMap googleMap;
    private LinearLayout llEspecialidades;
    private LinearLayout llUrgencia;
    private LinearLayout llQualificacoes;
    private LinearLayout btnLigar;
    private LinearLayout btnWhats;
    private LinearLayout btnMeLigue;
    private AtendimentoInterface atendimentoInterface = this;
    private NotificacaoProfissionalInterface notificacaoProfissionalInterface = this;

    private Perfil profissional;
    private Categoria categoria;
    private Usuario usuario;
    private GuiaProDao guiaProDao;

    private final long SITUACAO_AGUARDANDO = 2;
    private final long ATENDIMENTO_LIGACAO = 1;
    private final long ATENDIMENTO_WHATS = 2;
    private final long ATENDIMENTO_MELIGUE = 3;
    private final String API_ATENDIMENTO = "atendimento_cliente";
    private final String API_NOTIFICACAO = "notificao_atendimento";

    private int acao = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_prof, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        tvAtendeLocalidade = (TextView) view.findViewById(R.id.tvAtendeLocalidade);
        tvNomeProfissional = (TextView) view.findViewById(R.id.tvNomeProfissional);
        ivImagem = (ImageView) view.findViewById(R.id.ivImagem);
        llEspecialidades = (LinearLayout) view.findViewById(R.id.llEspecialidades);
        llUrgencia = (LinearLayout) view.findViewById(R.id.llUrgencia);
        llQualificacoes = (LinearLayout) view.findViewById(R.id.llQualificacoes);
        btnLigar = (LinearLayout) view.findViewById(R.id.btnLigar);
        btnWhats = (LinearLayout) view.findViewById(R.id.btnWhats);
        btnMeLigue = (LinearLayout) view.findViewById(R.id.btnMeLigeu);
        map = (MapView) view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.onResume();

        profissional = VariaveisEstaticas.getProfissional();
        categoria = VariaveisEstaticas.getCategoria();
        usuario = VariaveisEstaticas.getUsuario();

        guiaProDao = (GuiaProDao) getActivity().getApplication();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        carregarDadosProfissional();
        acoes();

        return view;
    }

    private void carregarDadosProfissional(){
        tvNomeProfissional.setText(profissional.getNome() + " " + profissional.getSobrenome());
        tvAtendeLocalidade.setText(profissional.getNome() + " atende a partir desta localidade");
        carregaEspecialidades();
    }

    private void acoes(){

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acao = 1;

                if(clienteJaPossuiAtendimentoComProfissional()){
                    FerramentasBasicas.fazerLigacao(profissional.getCelular());
                }else{
                    Atendimento atendimento = criarAtendimento(
                            usuario.getPerfil().getNome() + " ligou para o profissinal " + profissional.getNome(),
                            "Ligação - " + categoria.getDescricao(),
                            ATENDIMENTO_LIGACAO);

                    guiaProDao.getDaoSession().getAtendimentoDao().insert(atendimento);

                    if(FerramentasBasicas.isOnline(getContext())){
                        PostCadastrarAtendimentoAsyncTask postCadastrarAtendimentoAsyncTask = new PostCadastrarAtendimentoAsyncTask(getContext(),
                                atendimento, atendimentoInterface);
                        postCadastrarAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() + API_ATENDIMENTO);
                    }else{
                        FerramentasBasicas.fazerLigacao(profissional.getCelular());
                    }
                }
            }
        });

        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acao = 2;

                if(clienteJaPossuiAtendimentoComProfissional()){
                    FerramentasBasicas.enviarWhats(getContext(), profissional.getCelular());
                }else{
                    Atendimento atendimento = criarAtendimento(
                            usuario.getPerfil().getNome() + " mndou o whats para o profissinal " + profissional.getNome(),
                            "Whats - " + categoria.getDescricao(),
                            ATENDIMENTO_WHATS);

                    guiaProDao.getDaoSession().getAtendimentoDao().insert(atendimento);

                    if(FerramentasBasicas.isOnline(getContext())){
                        PostCadastrarAtendimentoAsyncTask postCadastrarAtendimentoAsyncTask = new PostCadastrarAtendimentoAsyncTask(getContext(),
                                atendimento, atendimentoInterface);
                        postCadastrarAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() + API_ATENDIMENTO);
                    }else{
                        FerramentasBasicas.enviarWhats(getContext(), profissional.getCelular());
                    }
                }
                FerramentasBasicas.enviarWhats(getContext(), profissional.getCelular());
            }
        });

        btnMeLigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acao = 3;
                Atendimento atendimento = criarAtendimento(
                        usuario.getPerfil().getNome() + " solicitou liguação para o profissinal " + profissional.getNome(),
                        "Me Ligue - " + categoria.getDescricao(),
                        ATENDIMENTO_WHATS);

                guiaProDao.getDaoSession().getAtendimentoDao().insert(atendimento);

                if(FerramentasBasicas.isOnline(getContext())){
                    PostCadastrarAtendimentoAsyncTask postCadastrarAtendimentoAsyncTask = new PostCadastrarAtendimentoAsyncTask(getContext(),
                            atendimento, atendimentoInterface);
                    postCadastrarAtendimentoAsyncTask.execute(FerramentasBasicas.getURL() + API_ATENDIMENTO);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng latLng = new LatLng(-20.460918,-54.611032);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).build();

        this.googleMap.clear();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        this.googleMap.addCircle(new CircleOptions()
                .center(new LatLng(-20.460918, -54.611032))
                .radius(100)
                .strokeColor(Color.parseColor("#f15109"))
                .fillColor(Color.parseColor("#32f15109")));
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

    private void carregaEspecialidades(){
        llEspecialidades.removeAllViews();
        for(Categoria categoria : profissional.getCategorias()){

            LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.adapter_categoria_profissional,null);
            TextView tvCategoria = (TextView) linearLayout.findViewById(R.id.tvCategoria);
            tvCategoria.setText(categoria.getDescricao());
            llEspecialidades.addView(linearLayout);

        }
    }

    private Atendimento criarAtendimento(String descricao, String titulo, long tipoAtendimentoId){

        Atendimento atendimento = new Atendimento();

        atendimento.setCategoriaId(categoria.getId());
        atendimento.setClienteId(usuario.getPerfil().getId());
        atendimento.setProfissionalId(profissional.getId());
        atendimento.setData(new Date());
        atendimento.setDescricao(descricao);
        atendimento.setTitulo(titulo);
        atendimento.setTipoAtendimentoId(tipoAtendimentoId);
        atendimento.setSitucaoId(1);

        return atendimento;
    }

    private boolean clienteJaPossuiAtendimentoComProfissional(){

        AtendimentoDao.Properties propriedades = new AtendimentoDao.Properties();

        QueryBuilder<Atendimento> atendimentoQB = guiaProDao.getDaoSession()
                .getAtendimentoDao().queryBuilder();

        atendimentoQB
                .where(propriedades.ClienteId.eq(usuario.getPerfilId()),
                        propriedades.ProfissionalId.eq(profissional.getId()),
                        propriedades.SitucaoId.eq(SITUACAO_AGUARDANDO));

        List<Atendimento> atendimentos = atendimentoQB.list();
        if(atendimentos.isEmpty() == false)
            return true;

        return false;
    }

    @Override
    public void retornoCadastroAtendimento(boolean cadastrou) {

    }

    @Override
    public void retornoNotificacao(boolean enviou) {

    }
}
