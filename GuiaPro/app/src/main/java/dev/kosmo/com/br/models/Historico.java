package dev.kosmo.com.br.models;

import java.util.Date;
import java.util.Locale;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class Historico {

    private Integer id;
    private Integer idCliente;
    private Integer idProfissional;
    private String descricao;
    private Date data;

    public Historico(Integer id, Integer idCliente, Integer idProfissional, String descricao, Date data) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProfissional = idProfissional;
        this.descricao = descricao;
        this.data = data;
    }

    public Historico() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String dataExtenso(){
        return this.data != null ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(this.data) : "";
    }
}
