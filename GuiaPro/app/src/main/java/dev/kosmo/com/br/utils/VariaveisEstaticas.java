package dev.kosmo.com.br.utils;

import dev.kosmo.com.br.interfaces.FragmentInterface;
import dev.kosmo.com.br.models.Cliente;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.models.Profissional;

/**
 * Created by Filipe on 11/03/2018.
 */

public class VariaveisEstaticas {

    private static FragmentInterface fragmentInterface;
    private static Profissional profissional;
    private static Especialidades especialidades;
    private static Cliente clienteLogado;

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

    public static Especialidades getEspecialidades() {
        return especialidades;
    }

    public static void setEspecialidades(Especialidades especialidades) {
        VariaveisEstaticas.especialidades = especialidades;
    }

    public static Cliente getClienteLogado() {
        return clienteLogado;
    }

    public static void setClienteLogado(Cliente clienteLogado) {
        VariaveisEstaticas.clienteLogado = clienteLogado;
    }
}
