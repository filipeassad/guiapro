package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.kosmo.com.br.interfaces.ImagemInterface;

public class GetImagemAsyncTask extends AsyncTask<String, String, Bitmap> {

    private Context contexto;
    private ImagemInterface imagemInterface;
    private ProgressDialog progress;

    public GetImagemAsyncTask(Context contexto, ImagemInterface imagemInterface) {
        this.contexto = contexto;
        this.imagemInterface = imagemInterface;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Buscando Imagens...");
        progress.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap img = null;
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            InputStream input = conexao.getInputStream();
            img = BitmapFactory.decodeStream(input);
            return img;
        }
        catch(IOException e){}

        return null;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imagemInterface.getImagem(bitmap);
        progress.dismiss();
    }
}
