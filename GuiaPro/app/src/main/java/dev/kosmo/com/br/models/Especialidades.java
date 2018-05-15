package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by 0118431 on 19/03/2018.
 */

public class Especialidades {

    private Integer id;
    private String nome;
    private Bitmap imagem;
    private String descricao;

    public Especialidades() {
    }

    public Especialidades(Integer id, String nome, Bitmap imagem, String descricao) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
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

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public byte[] imgBlob(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }
}
