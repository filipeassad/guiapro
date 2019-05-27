package dev.kosmo.com.br.interfaces;

import java.util.List;

import dev.kosmo.com.br.models.Especialidades;

public interface EspecialidadesInterface {
    public void retornoGetEspecialidades(List<Especialidades> especialidades);
    public void retornoPostEspecialidades(boolean cadastrou);
    public void retornDeleteEspecialidade(boolean deletou);
}
