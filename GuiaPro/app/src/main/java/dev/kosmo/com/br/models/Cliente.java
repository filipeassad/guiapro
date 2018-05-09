package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class Cliente {

    private Integer id;
    private Integer idEndereco;
    private String nome;
    private String celular;
    private String email;
    private Bitmap img;

    private Endereco enderecoObj;

    public Cliente(Integer id, String nome, String celular, String email, Bitmap img, Integer idEndereco, Endereco enderecoObj) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.img = img;
        this.idEndereco = idEndereco;
        this.enderecoObj = enderecoObj;
    }

    public Cliente() {
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Endereco getEnderecoObj() {
        return enderecoObj;
    }

    public void setEnderecoObj(Endereco enderecoObj) {
        this.enderecoObj = enderecoObj;
    }

    public byte[] imgBlob(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

}
