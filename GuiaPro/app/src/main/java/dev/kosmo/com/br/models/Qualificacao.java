package dev.kosmo.com.br.models;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class Qualificacao {

    private Integer id;
    private String nome;

    public Qualificacao(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Qualificacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
