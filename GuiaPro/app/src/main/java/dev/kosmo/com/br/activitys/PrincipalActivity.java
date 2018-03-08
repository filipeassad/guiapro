package dev.kosmo.com.br.activitys;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.adapter.MenuNavAdapter;
import dev.kosmo.com.br.fragments.CategoriasFragment;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.models.ItemMenuNav;
import dev.kosmo.com.br.utils.Animacao;
import dev.kosmo.com.br.utils.GerenciadorFragment;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class PrincipalActivity extends FragmentActivity implements FragmentInterface {

    private Bundle savedInstanceState;
    private FragmentManager fm = getSupportFragmentManager();

    private LinearLayout llNavDraw;
    private LinearLayout llBgCinza;
    private ImageView ivMenu;

    private GerenciadorFragment gerenciadorFragment  = new GerenciadorFragment();
    private Animacao animacao = new Animacao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        llNavDraw = (LinearLayout) findViewById(R.id.llNavDraw);
        llBgCinza = (LinearLayout) findViewById(R.id.llBgCinza);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);

        insertFirstFragment();
        carregaNav();

        acoes();
    }

    private void carregaNav(){
        View nav_layout = getLayoutInflater().inflate(R.layout.nav_menu, llNavDraw);
        ListView lvNav = (ListView) nav_layout.findViewById(R.id.lvNav);

        List<ItemMenuNav> lista = new ArrayList<>();
        lista.add(new ItemMenuNav("Perfil", BitmapFactory.decodeResource(this.getResources(), R.drawable.manuser)));
        lista.add(new ItemMenuNav("Notificação", BitmapFactory.decodeResource(this.getResources(), R.drawable.notification)));
        lista.add(new ItemMenuNav("Mensagem", BitmapFactory.decodeResource(this.getResources(), R.drawable.message)));
        lista.add(new ItemMenuNav("Sair", BitmapFactory.decodeResource(this.getResources(), R.drawable.logout)));

        MenuNavAdapter menuNavAdapter = new MenuNavAdapter(this, R.layout.adapter_menu_nav, lista);

        lvNav.setAdapter(menuNavAdapter);

    }

    private void insertFirstFragment(){

        if(savedInstanceState == null){
            CategoriasFragment categoriasFragment = new CategoriasFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.contFragments, categoriasFragment, "Categorias");
            ft.commit();
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
}
