package dev.kosmo.com.br.task.posts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import dev.kosmo.com.br.interfaces.PostLoginInterface;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class PostLoginAsyncTask extends AsyncTask<String, String, HashMap<String,String>>{

    private Context contexto;
    private ProgressDialog progress;
    private Usuario usuario;
    private PostLoginInterface postLoginInterface;

    public PostLoginAsyncTask(Context contexto, Usuario usuario, PostLoginInterface postLoginInterface) {
        this.contexto = contexto;
        this.usuario = usuario;
        this.postLoginInterface = postLoginInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(contexto);
        progress.setMessage("Logando...");
        progress.show();
    }

    @Override
    protected HashMap<String, String> doInBackground(String... strings) {

        int httpResponse = 0;
        HashMap<String,String> hash = new HashMap<>();

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type","application/json");

            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jsonParam.put("email", usuario.getEmail());
            jsonParam.put("senha", usuario.getSenha());

            jsonArray.put(jsonParam);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            os.close();

            httpResponse = conn.getResponseCode();
            JSONObject response;

            if(httpResponse == HttpURLConnection.HTTP_OK || httpResponse == 201){

                String responseString = FerramentasBasicas.readStream(conn.getInputStream());
                response = new JSONObject(responseString);
                return validaResposta(response);

            }else if(httpResponse == HttpURLConnection.HTTP_UNAUTHORIZED){
                hash.put("erro", "sem credenciais");
                return hash;
            }else{
                hash.put("erro", "nao conectou");
                return hash;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        hash.put("erro", "nao conectou");
        return hash;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
        super.onPostExecute(stringStringHashMap);
        postLoginInterface.postLogin(stringStringHashMap);
        progress.dismiss();
    }

    private HashMap<String,String> validaResposta(JSONObject response){

        HashMap<String, String> resultado = new HashMap<>();

        try {
            boolean sucesso = response.getBoolean("success");
            if(sucesso){
                resultado.put("token", response.getString("token"));
            }else{
                resultado.put("erro",response.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
