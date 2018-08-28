package dev.kosmo.com.br.models.sistema;

import android.graphics.Bitmap;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class ItemMenuNav  {

    private String rotulo;
    private Bitmap img;

    public ItemMenuNav() {
    }

    public ItemMenuNav(String rotulo, Bitmap img) {
        this.rotulo = rotulo;
        this.img = img;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

}
