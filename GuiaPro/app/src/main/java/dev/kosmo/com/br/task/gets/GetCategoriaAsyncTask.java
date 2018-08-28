package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.interfaces.GetCategoriaInterface;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class GetCategoriaAsyncTask extends AsyncTask<String, String, JSONArray> {

    private Context contexto;
    private GetCategoriaInterface getCategoriaInterface;
    private String token;
    private ProgressDialog progress;

    public GetCategoriaAsyncTask(Context contexto, GetCategoriaInterface getCategoriaInterface, String token) {
        this.contexto = contexto;
        this.getCategoriaInterface = getCategoriaInterface;
        this.token = token;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Buscando as categorias...");
        progress.show();
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;
        JSONArray response = new JSONArray();

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.addRequestProperty("x-access-token", token);
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = FerramentasBasicas.readStream(urlConnection.getInputStream());
                response = new JSONArray(responseString);
            }else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                return null;
            }else{
               return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return response;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        progress.dismiss();
        getCategoriaInterface.retornoCategoria(montaCategorias(jsonArray));
    }

    private List<Categoria> montaCategorias(JSONArray jsonArray){
        List<Categoria> categorias = new ArrayList<>();

        try {
            if(jsonArray != null && jsonArray.length() > 0 ){
                for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Categoria categoria = new Categoria();

                        categoria.setId(jsonObject.getLong("id"));
                        categoria.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
                        categoria.setSigla(jsonObject.has("sigla") ? jsonObject.getString("sigla") : "");
                        categoria.setUrlImg(jsonObject.has("urlimg") ? jsonObject.getString("urlimg") : "");

                        categorias.add(categoria);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}
