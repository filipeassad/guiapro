package dev.kosmo.com.br.activitys;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.adapter.MenuNavAdapter;
import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.fragments.CategoriasFragment;
import dev.kosmo.com.br.fragments.ListagemAtendimentoProfissionalFragment;
import dev.kosmo.com.br.fragments.ListagemProfissionaisFragment;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.models.sistema.ItemMenuNav;
import dev.kosmo.com.br.utils.Animacao;
import dev.kosmo.com.br.utils.GerenciadorFragment;
import dev.kosmo.com.br.utils.PushNotificationService;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class PrincipalActivity extends FragmentActivity implements FragmentInterface {

    private Bundle savedInstanceState;
    private FragmentManager fm = getSupportFragmentManager();

    private LinearLayout llNavDraw;
    private LinearLayout llBgCinza;
    private ImageView ivMenu;
    private LinearLayout llFooterMenu;
    private LinearLayout llFooterMenuProfissional;

    private LinearLayout llPesquisar;
    private ImageView ivPesquisar;
    private TextView tvPesquisar;

    private LinearLayout llTopRanked;
    private ImageView ivTopRanked;
    private TextView tvTopRanked;

    private LinearLayout llOfertas;
    private ImageView ivOfertas;
    private TextView tvOfertas;

    private LinearLayout llHistorico;
    private ImageView ivHistorico;
    private TextView tvHistorico;
    private Usuario usuario;

    private LinearLayout llAtendimentoProfissional;
    private ImageView ivAtendimentoProfissional;
    private TextView tvAtendimentoProfissional;

    private LinearLayout llTopRankedProfissional;
    private ImageView ivTopRankedProfissional;
    private TextView tvTopRankedProfissional;

    private LinearLayout llOfertasProfissional;
    private ImageView ivOfertasProfissional;
    private TextView tvOfertasProfissional;

    private LinearLayout llHistoricoProfissional;
    private ImageView ivHistoricoProfissional;
    private TextView tvHistoricoProfissional;

    private GerenciadorFragment gerenciadorFragment  = new GerenciadorFragment();
    private Animacao animacao = new Animacao();
    private GuiaProDao guiaProDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        llNavDraw = (LinearLayout) findViewById(R.id.llNavDraw);
        llBgCinza = (LinearLayout) findViewById(R.id.llBgCinza);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        llFooterMenu = (LinearLayout) findViewById(R.id.llFooterMenu);
        llFooterMenuProfissional = (LinearLayout) findViewById(R.id.llFooterMenuProfissional);

        llPesquisar = (LinearLayout) findViewById(R.id.llPesquisar);
        ivPesquisar = (ImageView) findViewById(R.id.ivPesquisar);
        tvPesquisar = (TextView) findViewById(R.id.tvPesquisar);

        llTopRanked = (LinearLayout) findViewById(R.id.llTopRanked);
        ivTopRanked = (ImageView) findViewById(R.id.ivTopRanked);
        tvTopRanked = (TextView) findViewById(R.id.tvTopRanked);

        llOfertas = (LinearLayout) findViewById(R.id.llOfertas);
        ivOfertas = (ImageView) findViewById(R.id.ivOfertas);
        tvOfertas = (TextView) findViewById(R.id.tvOfertas);

        llHistorico = (LinearLayout) findViewById(R.id.llHistorico);
        ivHistorico = (ImageView) findViewById(R.id.ivHistorico);
        tvHistorico = (TextView) findViewById(R.id.tvHistorico);

        llAtendimentoProfissional = (LinearLayout) findViewById(R.id.llAtendimentoProfissional);
        ivAtendimentoProfissional = (ImageView) findViewById(R.id.ivAtendimentoProfissional);
        tvAtendimentoProfissional = (TextView) findViewById(R.id.tvAtendimentoProfissional);

        llTopRankedProfissional = (LinearLayout) findViewById(R.id.llTopRankedProfissional);
        ivTopRankedProfissional = (ImageView) findViewById(R.id.ivTopRankedProfissional);
        tvTopRankedProfissional = (TextView) findViewById(R.id.tvTopRankedProfissional);

        llOfertasProfissional = (LinearLayout) findViewById(R.id.llOfertasProfissional);
        ivOfertasProfissional = (ImageView) findViewById(R.id.ivOfertasProfissional);
        tvOfertasProfissional = (TextView) findViewById(R.id.tvOfertasProfissional);

        llHistoricoProfissional = (LinearLayout) findViewById(R.id.llHistoricoProfissional);
        ivHistoricoProfissional = (ImageView) findViewById(R.id.ivHistoricoProfissional);
        tvHistoricoProfissional = (TextView) findViewById(R.id.tvHistoricoProfissional);

        llPesquisar.setTag("Pesquisar");
        llTopRanked.setTag("TopRanked");
        llOfertas.setTag("Ofertas");
        llHistorico.setTag("Historico");

        llAtendimentoProfissional.setTag("Atendimento");
        llTopRankedProfissional.setTag("TopRanked");
        llOfertasProfissional.setTag("Ofertas");
        llHistoricoProfissional.setTag("Historico");

        llPesquisar.setOnClickListener(menuClickListener);
        llTopRanked.setOnClickListener(menuClickListener);
        llOfertas.setOnClickListener(menuClickListener);
        llHistorico.setOnClickListener(menuClickListener);

        llAtendimentoProfissional.setOnClickListener(menuClickListener);
        llTopRankedProfissional.setOnClickListener(menuClickListener);
        llOfertasProfissional.setOnClickListener(menuClickListener);
        llHistoricoProfissional.setOnClickListener(menuClickListener);

        guiaProDao = (GuiaProDao) getApplication();

        VariaveisEstaticas.setFragmentInterface(this);

        usuario = VariaveisEstaticas.getUsuario();

        insertFirstFragment();
        carregaNav();

        Intent serviceIntent = new Intent(this,PushNotificationService.class);
        this.startService(serviceIntent);

        acoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void carregaNav(){

        View nav_layout = getLayoutInflater().inflate(R.layout.nav_menu, llNavDraw);

        ListView lvNav = (ListView) nav_layout.findViewById(R.id.lvNav);
        TextView tvNomePerfil = (TextView) nav_layout.findViewById(R.id.tvNomePerfil);
        TextView tvEmailPerfil = (TextView) nav_layout.findViewById(R.id.tvEmailPerfil);
        ImageView ivImagemPerfil = (ImageView) nav_layout.findViewById(R.id.ivImagemPerfil);

        List<ItemMenuNav> lista = new ArrayList<>();
        lista.add(new ItemMenuNav("Informações Pessoais", BitmapFactory.decodeResource(this.getResources(), R.drawable.manuser)));
        lista.add(new ItemMenuNav("Notificações", BitmapFactory.decodeResource(this.getResources(), R.drawable.notification)));
        //lista.add(new ItemMenuNav("Relate um Problema", BitmapFactory.decodeResource(this.getResources(), R.drawable.attention)));
        //lista.add(new ItemMenuNav("Ajuda", BitmapFactory.decodeResource(this.getResources(), R.drawable.help)));
        lista.add(new ItemMenuNav("Sair", BitmapFactory.decodeResource(this.getResources(), R.drawable.sair)));

        MenuNavAdapter menuNavAdapter = new MenuNavAdapter(this, R.layout.adapter_menu_nav, lista);

        tvNomePerfil.setText(usuario.getPerfil().getNome() + " " + usuario.getPerfil().getSobrenome());
        tvEmailPerfil.setText(usuario.getEmail());

        if(usuario.getPerfil().getUrlImg() == null
                || usuario.getPerfil().getUrlImg().trim().equals("")){
            ivImagemPerfil.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manuserbranco));
        }

        lvNav.setAdapter(menuNavAdapter);

        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(((ItemMenuNav)adapterView.getItemAtPosition(i)).getRotulo().equals("Informações Pessoais")){
                    mudaTela("Perfil");
                }else if (((ItemMenuNav)adapterView.getItemAtPosition(i)).getRotulo().equals("Notificações")){
                    mudaTela("Notificacao");
                }else if(((ItemMenuNav)adapterView.getItemAtPosition(i)).getRotulo().equals("Sair")){
                    guiaProDao.getDaoSession().getUsuarioDao().delete(usuario);
                    finish();
                }
            }
        });

    }

    private void insertFirstFragment(){

        if(savedInstanceState == null){
            if(usuario.getPerfil().getTipoPerfil().getDescricao().equals("Cliente")){
                CategoriasFragment categoriasFragment = new CategoriasFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.contFragments, categoriasFragment, "Categorias");
                ft.commit();
            }else if(usuario.getPerfil().getTipoPerfil().getDescricao().equals("Profissional")){
                ListagemAtendimentoProfissionalFragment listagemAtendimentoProfissionalFragment = new ListagemAtendimentoProfissionalFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contFragments, listagemAtendimentoProfissionalFragment, "ListagemAtendimentoProfissional");
                ft.commit();
            }
        }
    }

    private void acoes(){

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llNavDraw.getVisibility() == View.VISIBLE){
                    animacao.animaSaida(llNavDraw);
                    animacao.fadeOutAnimation(llBgCinza);
                }else{
                    llNavDraw.setVisibility(View.VISIBLE);
                    llBgCinza.setVisibility(View.VISIBLE);
                    animacao.animaEntrada(llNavDraw);
                    animacao.fadeInAnimatio(llBgCinza);
                }
            }
        });

        llBgCinza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llNavDraw.getVisibility() == View.VISIBLE){
                    animacao.animaSaida(llNavDraw);
                    animacao.fadeOutAnimation(llBgCinza);
                }else{
                    llNavDraw.setVisibility(View.VISIBLE);
                    llBgCinza.setVisibility(View.VISIBLE);
                    animacao.animaEntrada(llNavDraw);
                    animacao.fadeInAnimatio(llBgCinza);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(llNavDraw.getVisibility() == View.VISIBLE){
            animacao.animaSaida(llNavDraw);
            if(llBgCinza.getVisibility() == View.VISIBLE)
                animacao.fadeOutAnimation(llBgCinza);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void mudaTela(String nome) {
        gerenciadorFragment.mudarTela(fm,llNavDraw,nome,llBgCinza);
    }

    @Override
    public void voltar() {
        onBackPressed();
    }

    @Override
    public void visibilidadeMenu(boolean visivel) {
        if(visivel){
            llFooterMenu.setVisibility(View.VISIBLE);
            llFooterMenuProfissional.setVisibility(View.GONE);
        }else{
            llFooterMenu.setVisibility(View.GONE);
            llFooterMenuProfissional.setVisibility(View.GONE);
        }
    }

    @Override
    public void visibilidadeMenuProfissional(boolean visivel) {
        if(visivel){
            llFooterMenu.setVisibility(View.GONE);
            llFooterMenuProfissional.setVisibility(View.VISIBLE);
        }else{
            llFooterMenu.setVisibility(View.GONE);
            llFooterMenuProfissional.setVisibility(View.GONE);
        }
    }

    @Override
    public void mudaActivity(Intent intent) {
        startActivity(intent);
    }

    private View.OnClickListener menuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            limpaMenu();

            if(view.getTag().equals("Pesquisar")){
                ivPesquisar.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.search));
                tvPesquisar.setTextColor(Color.parseColor("#e9a11c"));
                mudaTela("Categorias");
            }else if(view.getTag().equals("TopRanked")){
                ivTopRanked.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.star));
                tvTopRanked.setTextColor(Color.parseColor("#e9a11c"));
                ivTopRankedProfissional.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.star));
                tvTopRankedProfissional.setTextColor(Color.parseColor("#e9a11c"));
                mudaTela("TopRanked");
            }else if(view.getTag().equals("Ofertas")){
                ivOfertas.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.tags));
                tvOfertas.setTextColor(Color.parseColor("#e9a11c"));
                ivOfertasProfissional.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.tags));
                tvOfertasProfissional.setTextColor(Color.parseColor("#e9a11c"));
                mudaTela("Ofertas");
            }else if(view.getTag().equals("Historico")){
                ivHistorico.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.list));
                tvHistorico.setTextColor(Color.parseColor("#e9a11c"));
                ivHistoricoProfissional.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.list));
                tvHistoricoProfissional.setTextColor(Color.parseColor("#e9a11c"));
                mudaTela("Historico");
            }else if(view.getTag().equals("Atendimento")){
                ivAtendimentoProfissional.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.atendimento_laranja));
                tvAtendimentoProfissional.setTextColor(Color.parseColor("#e9a11c"));
                mudaTela("ListagemAtendimentoProfissional");
            }

        }
    };

    private void limpaMenu(){

        ivPesquisar.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.searchcinza));
        tvPesquisar.setTextColor(Color.parseColor("#868686"));

        ivTopRanked.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.starcinza));
        tvTopRanked.setTextColor(Color.parseColor("#868686"));

        ivOfertas.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.tagscinza));
        tvOfertas.setTextColor(Color.parseColor("#868686"));

        ivHistorico.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.listcinza));
        tvHistorico.setTextColor(Color.parseColor("#868686"));

        ivAtendimentoProfissional.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.atendimento_cinza));
        tvAtendimentoProfissional.setTextColor(Color.parseColor("#868686"));

    }


}
