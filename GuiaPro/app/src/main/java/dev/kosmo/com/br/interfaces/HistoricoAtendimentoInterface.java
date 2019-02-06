package dev.kosmo.com.br.interfaces;

import java.util.List;
import dev.kosmo.com.br.models.HistoricoAtendimento;

public interface HistoricoAtendimentoInterface {
    public void retornoCadastroHistoricoAtendimento(boolean cadastrou, long idHistoricoAtendimento);
    public void retornoBuscaHistoricosAtendimento(List<HistoricoAtendimento> historicosAtendimento);
}
