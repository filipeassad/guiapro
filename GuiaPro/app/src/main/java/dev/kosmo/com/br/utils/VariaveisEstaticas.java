package dev.kosmo.com.br.utils;

import android.graphics.Bitmap;

import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;

/**
 * Created by Filipe on 11/03/2018.
 */

public class VariaveisEstaticas {

    private static FragmentInterface fragmentInterface;
    private static Usuario usuario;
    private static Categoria categoria;
    private static Perfil profissional;
    private static Atendimento atendimento;
    private static HistoricoAtendimento historicoAtendimento;
    private static final int PICK_IMAGE = 24;
    private static ImagemInterface imagemInterface;
    private static Bitmap imagemPerfil;


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

    public static Perfil getProfissional() {
        return profissional;
    }

    public static void setProfissional(Perfil profissional) {
        VariaveisEstaticas.profissional = profissional;
    }

    public static Atendimento getAtendimento() {
        return atendimento;
    }

    public static void setAtendimento(Atendimento atendimento) {
        VariaveisEstaticas.atendimento = atendimento;
    }

    public static HistoricoAtendimento getHistoricoAtendimento() {
        return historicoAtendimento;
    }

    public static void setHistoricoAtendimento(HistoricoAtendimento historicoAtendimento) {
        VariaveisEstaticas.historicoAtendimento = historicoAtendimento;
    }

    public static int getPickImage() {
        return PICK_IMAGE;
    }

    public static ImagemInterface getImagemInterface() {
        return imagemInterface;
    }

    public static void setImagemInterface(ImagemInterface imagemInterface) {
        VariaveisEstaticas.imagemInterface = imagemInterface;
    }

    public static Bitmap getImagemPerfil() {
        return imagemPerfil;
    }

    public static void setImagemPerfil(Bitmap imagemPerfil) {
        VariaveisEstaticas.imagemPerfil = imagemPerfil;
    }
}
