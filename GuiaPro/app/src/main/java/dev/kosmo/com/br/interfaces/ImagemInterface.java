package dev.kosmo.com.br.interfaces;

import android.graphics.Bitmap;

public interface ImagemInterface {
    public void getImagem(Bitmap imagem);
    public void setImagem(Bitmap imagem);
    public void retornoPostImagem(boolean cadastrou, String urlImagem);
}
