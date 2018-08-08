package dev.kosmo.com.br.models;

public class Autenticacao {

    private Integer id;
    private String hash;
    private String usuario;
    private boolean ativo;
    private String senha;
    private Integer userid;

    public Autenticacao(Integer id, String hash, String usuario, boolean ativo, String senha, Integer userid) {
        this.id = id;
        this.hash = hash;
        this.usuario = usuario;
        this.ativo = ativo;
        this.senha = senha;
        this.userid = userid;
    }

    public Autenticacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}
