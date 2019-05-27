package dev.kosmo.com.br.task.posts;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import okhttp3.internal.http.StatusLine;

public class PostImagemAsyncTask  extends AsyncTask<String, String, HashMap<String, String>> {

    private Context contexto;
    private ProgressDialog progress;
    private Bitmap imagem;
    private ImagemInterface imagemInterface;

    public PostImagemAsyncTask(Context contexto, Bitmap imagem, ImagemInterface imagemInterface) {
        this.contexto = contexto;
        this.imagem = imagem;
        this.imagemInterface = imagemInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
        progress.show();
    }

    @Override
    protected HashMap<String, String> doInBackground(String... strings) {

        int httpResponse = 0;

        try
        {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");

            conn.setReadTimeout(35000);
            conn.setConnectTimeout(35000);

            // directly let .compress write binary image data
            // to the output-stream
            OutputStream os = conn.getOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            httpResponse = conn.getResponseCode();
            JSONObject response;

            if(httpResponse == HttpURLConnection.HTTP_OK
                    || httpResponse == HttpURLConnection.HTTP_CREATED){
                String responseString = FerramentasBasicas.readStream(conn.getInputStream());
                response = new JSONObject(responseString);
                return validaResposta(response);
            }else if(httpResponse == HttpURLConnection.HTTP_UNAUTHORIZED){
                return null;
            }else {
                return null;
            }

        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HashMap<String, String> validaResposta(JSONObject response){
        HashMap<String, String> resultado = new HashMap<>();
        try {
            if(response.has("url")){
                resultado.put("url", response.getString("url"));
            }else{
                resultado.put("erro", "Não foi possível fazer upload da imagem.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> stringObjectHashMap) {
        super.onPostExecute(stringObjectHashMap);

        progress.dismiss();

        if(stringObjectHashMap == null)
            imagemInterface.retornoPostImagem(false, "Não foi possível salvar a imagem");
        else{
            boolean buscouCorretamente = stringObjectHashMap.containsKey("url");

            if(buscouCorretamente)
                imagemInterface.retornoPostImagem(true, stringObjectHashMap.get("url"));
            else
                imagemInterface.retornoPostImagem(false, "Não foi possível salvar a imagem");
        }
    }
}
