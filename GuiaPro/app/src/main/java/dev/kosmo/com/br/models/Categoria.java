package dev.kosmo.com.br.models;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "categoria")
public class Categoria {

    @Id private Long id;
    private String descricao;
    private String sigla;
    private String urlImg;

    @Transient private Bitmap imagem;

    public Categoria() {
    }

    @Generated(hash = 1663022233)
    public Categoria(Long id, String descricao, String sigla, String urlImg) {
        this.id = id;
        this.descricao = descricao;
        this.sigla = sigla;
        this.urlImg = urlImg;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getUrlImg() {
        return this.urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
