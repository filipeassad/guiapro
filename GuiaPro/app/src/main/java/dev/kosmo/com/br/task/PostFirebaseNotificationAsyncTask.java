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

import dev.kosmo.com.br.interfaces.NotificacaoPostInterface;
import dev.kosmo.com.br.models.Notificacao;

/**
 * Created by 0118431 on 20/04/2018.
 */

public class PostFirebaseNotificationAsyncTask extends AsyncTask<String, String, HashMap<String,String>> {

    private Context contexto;
    private ProgressDialog progress;
    private NotificacaoPostInterface notificacaoPostInterface;
    private Notificacao notificacao;

    public PostFirebaseNotificationAsyncTask(Context contexto, NotificacaoPostInterface notificacaoPostInterface, Notificacao notificacao) {
        this.contexto = contexto;
        this.notificacaoPostInterface = notificacaoPostInterface;
        this.notificacao = notificacao;
    }

    @Override
    protected void onPreExecute() {

        progress = new ProgressDialog(contexto);
        progress.setMessage("Enviando notificação...");
        progress.show();

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
    protected HashMap<String, String> doInBackground(String... strings) {
        int httpResponse = 0;

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Authorization","Bearer AAAA9bVZ6Jw:APA91bHjesK4WZJBqrbECtSarjOvGyzseLEiZltlovZCXMu3PM7wbI4PYC62VB6_p54Ha2pCgaCm7aH_r_b40AGWw636gEg0pTaiv6ZW5-XIPWd-2B3Reil3OdGikqUQKYM9gC5_BGmd");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            JSONObject msgParam = new JSONObject();
            JSONObject notificationParam = new JSONObject();
            //JSONArray jsonArray = new JSONArray();

            notificationParam.put("body", notificacao.getDescricao());
            notificationParam.put("title",notificacao.getTitulo());

            msgParam.put("token", "\n" +
                    "AIzaSyCtunRR3xddXe33ifC7qUaj4yPwjhbcjkM");
            msgParam.put("notification", notificationParam);

            //jsonParam.put("id", 10);
            jsonParam.put("message", msgParam);

            //jsonArray.put(jsonParam);
            Log.i("HTTP - URL ", strings[0]);
            Log.i("HTTP - JsonObject ", jsonParam.toString());
            //Log.i("HTTP - JsonArray ", jsonArray.toString());

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

                hash.put("name", response.getString("name") != null ? response.getString("name") : "");

                /*hash.put("token", response.getString("token") != null ? response.getString("token") : "");
                hash.put("usuario", response.getString("username") != null ? response.getString("username") : "");
                hash.put("nome", response.getString("name") != null ? response.getString("name") : "");
                hash.put("email", response.getString("email") != null ? response.getString("email") : "");*/

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

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
        progress.dismiss();
        notificacaoPostInterface.retornoPost(stringStringHashMap);
    }
}
