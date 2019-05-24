package dev.kosmo.com.br.models.sistema;

import java.util.List;

import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.Perfil;

public class GrupoHistoricoAtendimento {

    private List<HistoricoAtendimento> historicosAtendimento;
    private Perfil perfilGrupo;

    public GrupoHistoricoAtendimento() {
    }

    public List<HistoricoAtendimento> getHistoricosAtendimento() {
        return historicosAtendimento;
    }

    public void setHistoricosAtendimento(List<HistoricoAtendimento> historicosAtendimento) {
        this.historicosAtendimento = historicosAtendimento;
    }

    public Perfil getPerfilGrupo() {
        return perfilGrupo;
    }

    public void setPerfilGrupo(Perfil perfilGrupo) {
        this.perfilGrupo = perfilGrupo;
    }
}
