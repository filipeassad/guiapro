package dev.kosmo.com.br.task;

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
import dev.kosmo.com.br.models.Autenticacao;

public class PostLoginAsyncTask extends AsyncTask<String, String, HashMap<String,String>>{

    private Context contexto;
    private ProgressDialog progress;
    private Autenticacao autenticacao;
    private PostLoginInterface postLoginInterface;

    public PostLoginAsyncTask(Context contexto, Autenticacao autenticacao, PostLoginInterface postLoginInterface) {
        this.contexto = contexto;
        this.autenticacao = autenticacao;
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

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type","application/json");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            //jsonParam.put("id", 10);
            jsonParam.put("username", autenticacao.getUsuario());
            jsonParam.put("password", autenticacao.getSenha());

            jsonArray.put(jsonParam);
            Log.i("HTTP - URL ", strings[0]);
            Log.i("HTTP - JsonObject ", jsonParam.toString());
            Log.i("HTTP - JsonArray ", jsonArray.toString());

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            os.close();

            //conn.connect();

            httpResponse = conn.getResponseCode();
            JSONObject response = new JSONObject();
            HashMap<String,String> hash = new HashMap<>();

            if(httpResponse == HttpURLConnection.HTTP_OK || httpResponse == 201){
                String responseString = readStream(conn.getInputStream());
                Log.v("CatalogClient", responseString);
                response = new JSONObject(responseString);

                hash.put("token", response.getString("token") != null ? response.getString("token") : "");
                hash.put("usuario", response.getString("username") != null ? response.getString("username") : "");
                hash.put("nome", response.getString("name") != null ? response.getString("name") : "");
                hash.put("email", response.getString("email") != null ? response.getString("email") : "");
                //hash.put("userid", response.getString("userid") != null ? response.getString("userid") : "");

                return hash;
            }else if(httpResponse == HttpURLConnection.HTTP_UNAUTHORIZED){
                hash.put("erro", "sem credenciais");
                return hash;
            }else{
                hash.put("erro", "nao conectou");
                Log.v("CatalogClient", "Response code:" + httpResponse);
                return hash;
            }
            //Log.i("HTTP - Resposta", conn.getResponseMessage());
            // Log.i("HTTP - error", conn.getErrorStream().toString());

        } catch (IOException e) {
            Log.i("HTTP", e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("HTTP", e.getMessage());
            e.printStackTrace();
        }
        HashMap<String,String> hash = new HashMap<>();
        hash.put("erro", "nao conectou");
        return hash;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
        super.onPostExecute(stringStringHashMap);
        postLoginInterface.postLogin(stringStringHashMap);
        progress.dismiss();
    }
}
