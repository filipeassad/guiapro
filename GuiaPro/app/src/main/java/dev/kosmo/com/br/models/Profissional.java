package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

/**
 * Created by Filipe on 11/03/2018.
 */

public class Profissional {

    private String nome;
    private String descricao1;
    private String descricao2;
    private Bitmap img;

    public Profissional() {
    }

    public Profissional(String nome, String descricao1, String descricao2, Bitmap img) {
        this.nome = nome;
        this.descricao1 = descricao1;
        this.descricao2 = descricao2;
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
