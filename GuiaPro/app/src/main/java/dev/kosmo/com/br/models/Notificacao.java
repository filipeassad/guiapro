package dev.kosmo.com.br.models;

/**
 * Created by 0118431 on 20/04/2018.
 */

public class Notificacao {

    private Integer id;
    private Integer idProduto;
    private String titulo;
    private String descricao;

    public Notificacao() {
    }

    public Notificacao(Integer id, Integer idProduto, String titulo, String descricao) {
        this.id = id;
        this.idProduto = idProduto;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
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
