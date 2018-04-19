package dev.kosmo.com.br.models;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class Avaliacao {

    private Integer id;
    private Integer idCliente;
    private Integer idProfissional;
    private Integer nEstrelas;
    private String comentario;

    private Cliente clienteObj;
    private Profissional profissionalObj;

    public Avaliacao(Integer id, Integer idCliente, Integer idProfissional, Integer nEstrelas, String comentario, Cliente clienteObj, Profissional profissionalObj) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProfissional = idProfissional;
        this.nEstrelas = nEstrelas;
        this.comentario = comentario;
        this.clienteObj = clienteObj;
        this.profissionalObj = profissionalObj;
    }

    public Avaliacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Integer getnEstrelas() {
        return nEstrelas;
    }

    public void setnEstrelas(Integer nEstrelas) {
        this.nEstrelas = nEstrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Cliente getClienteObj() {
        return clienteObj;
    }

    public void setClienteObj(Cliente clienteObj) {
        this.clienteObj = clienteObj;
    }

    public Profissional getProfissionalObj() {
        return profissionalObj;
    }

    public void setProfissionalObj(Profissional profissionalObj) {
        this.profissionalObj = profissionalObj;
    }
}
