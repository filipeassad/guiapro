package dev.kosmo.com.br.interfaces;

import android.content.Intent;

/**
 * Created by 0118431 on 08/03/2018.
 */

public interface FragmentInterface {
    public void mudaTela(String nome);
    public void voltar();
    public void visibilidadeMenu(boolean visivel);
    public void mudaActivity(Intent intent);
}
