package dev.kosmo.com.br.models.sistema;

/**
 * Created by 0118431 on 20/04/2018.
 */

public class Notificacao {

    private Integer id;
    private String titulo;
    private String descricao;

    public Notificacao() {
    }

    public Notificacao(Integer id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
