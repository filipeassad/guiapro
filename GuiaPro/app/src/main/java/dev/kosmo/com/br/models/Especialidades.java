package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

/**
 * Created by 0118431 on 19/03/2018.
 */

public class Especialidades {

    private String nome;
    private Bitmap imagem;
    private String descricao;

    public Especialidades() {
    }

    public Especialidades(String nome, Bitmap imagem, String descricao) {
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
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

}
