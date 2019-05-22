package dev.kosmo.com.br.task.posts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import okhttp3.internal.http.StatusLine;

public class PostImagemAsyncTask  extends AsyncTask<String, String, HashMap<String, String>> {

    private Context contexto;
    private ProgressDialog progress;
    private File arquivo;
    private ImagemInterface imagemInterface;

    public PostImagemAsyncTask(Context contexto, File arquivo, ImagemInterface imagemInterface) {
        this.contexto = contexto;
        this.arquivo = arquivo;
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

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(strings[0]);

        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            entity.addPart("image", new FileBody(arquivo));

            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpPost, localContext);

            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            JSONObject response;
            if(statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED){

                String responseString = FerramentasBasicas.readStream(httpResponse.getEntity().getContent());
                response = new JSONObject(responseString);

                return validaResposta(response);

            }else if(statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                return null;
            }else{
                return null;
            }
        } catch (IOException | JSONException e) {
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

        boolean buscouCorretamente = stringObjectHashMap.containsKey("url");

        if(buscouCorretamente)
            imagemInterface.retornoPostImagem(true, stringObjectHashMap.get("url"));
        else
            imagemInterface.retornoPostImagem(false, stringObjectHashMap.get("erro"));
    }
}
