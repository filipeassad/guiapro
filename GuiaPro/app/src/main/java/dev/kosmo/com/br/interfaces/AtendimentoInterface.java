package dev.kosmo.com.br.interfaces;

import java.util.List;

import dev.kosmo.com.br.models.Atendimento;

public interface AtendimentoInterface {
    public void retornoCadastroAtendimento(boolean cadastrou, long idAtendimento);
    public void retornoBuscaAtendimentos(List<Atendimento> atendimentos);
}
