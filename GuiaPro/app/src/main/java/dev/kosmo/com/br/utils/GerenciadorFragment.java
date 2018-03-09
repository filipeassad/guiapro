package dev.kosmo.com.br.utils;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import dev.kosmo.com.br.fragments.CategoriasFragment;
import dev.kosmo.com.br.fragments.PerfilFragment;
import dev.kosmo.com.br.guiapro.R;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class GerenciadorFragment {

    private Animacao animacao = new Animacao();

    public void mudarTela(FragmentManager fm, LinearLayout llNavDraw, String nomeTela, LinearLayout llBgCinza){

        FragmentTransaction ft = fm.beginTransaction();

        if(nomeTela.equals("Categorias")){
            CategoriasFragment categoriasFragment = new CategoriasFragment();
            ft.replace(R.id.contFragments, categoriasFragment, nomeTela);
        }if(nomeTela.equals("Perfil")){
            PerfilFragment perfilFragment = new PerfilFragment();
            ft.replace(R.id.contFragments, perfilFragment, nomeTela);
        }

        if(fm.findFragmentByTag(nomeTela) != null){
            fm.popBackStack(nomeTela,1);
        }

        ft.addToBackStack(nomeTela);
        ft.commit();
        animacao.animaSaida(llNavDraw);

        if(llBgCinza.getVisibility() == View.VISIBLE)
            animacao.fadeOutAnimation(llBgCinza);

    }

}
