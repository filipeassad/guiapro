package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Filipe on 11/03/2018.
 */

public class Profissional {

    private Integer id;
    private String nome;
    private String celular;
    private String email;
    private String descricao1;
    private String descricao2;
    private Bitmap img;
    private Integer idEndereco;
    private String urlImg;

    private Endereco enderecoObj;

    public Profissional() {
    }

    public Profissional(Integer id, String nome, String celular, String email, String descricao1, String descricao2, Bitmap img, Integer idEndereco, String urlImg, Endereco enderecoObj) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.descricao1 = descricao1;
        this.descricao2 = descricao2;
        this.img = img;
        this.idEndereco = idEndereco;
        this.urlImg = urlImg;
        this.enderecoObj = enderecoObj;
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

    public String getDescricao1() {
        return descricao1;
    }

    public void setDescricao1(String descricao1) {
        this.descricao1 = descricao1;
    }

    public String getDescricao2() {
        return descricao2;
    }

    public void setDescricao2(String descricao2) {
        this.descricao2 = descricao2;
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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Endereco getEnderecoObj() {
        return enderecoObj;
    }

    public void setEnderecoObj(Endereco enderecoObj) {
        this.enderecoObj = enderecoObj;
    }

    public byte[] imgBlob(){

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap to byte[] stream
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            //close stream to save memory
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
       /*
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();*/
    }
}
