package dev.kosmo.com.br.utils;

import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.models.Profissional;

/**
 * Created by Filipe on 11/03/2018.
 */

public class VariaveisEstaticas {

    private static FragmentInterface fragmentInterface;
    private static Profissional profissional;

    public static Profissional getProfissional() {
        return profissional;
    }

    public static void setProfissional(Profissional profissional) {
        VariaveisEstaticas.profissional = profissional;
    }

    public static FragmentInterface getFragmentInterface() {
        return fragmentInterface;
    }

    public static void setFragmentInterface(FragmentInterface fragmentInterface) {
        VariaveisEstaticas.fragmentInterface = fragmentInterface;
    }

}
