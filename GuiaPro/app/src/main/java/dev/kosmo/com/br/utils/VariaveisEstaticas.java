package dev.kosmo.com.br.utils;

import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.Usuario;

/**
 * Created by Filipe on 11/03/2018.
 */

public class VariaveisEstaticas {

    private static FragmentInterface fragmentInterface;
    private static Usuario usuario;
    private static Categoria categoria;

    public static FragmentInterface getFragmentInterface() {
        return fragmentInterface;
    }

    public static void setFragmentInterface(FragmentInterface fragmentInterface) {
        VariaveisEstaticas.fragmentInterface = fragmentInterface;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        VariaveisEstaticas.usuario = usuario;
    }

    public static Categoria getCategoria() {
        return categoria;
    }

    public static void setCategoria(Categoria categoria) {
        VariaveisEstaticas.categoria = categoria;
    }
}
