package dev.kosmo.com.br.task.posts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.kosmo.com.br.interfaces.NotificacaoProfissionalInterface;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class PostNotificacaoProfissionalAsyncTask extends AsyncTask<String, String, Boolean> {

    private Context contexto;
    private NotificacaoProfissionalInterface notificacaoProfissionalInterface;
    private ProgressDialog progress;

    public PostNotificacaoProfissionalAsyncTask(Context contexto, NotificacaoProfissionalInterface notificacaoProfissionalInterface) {
        this.contexto = contexto;
        this.notificacaoProfissionalInterface = notificacaoProfissionalInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
        progress.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        int httpResponse = 0;

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type","application/json");
            conn.addRequestProperty("x-access-token", VariaveisEstaticas.getUsuario().getToken());
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
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
                return false;
            }else{
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean validaResposta(JSONObject response){
        try {
            if(response.has("success") && response.getBoolean("success")){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean notificou) {
        super.onPostExecute(notificou);
        progress.dismiss();
        notificacaoProfissionalInterface.retornoNotificacao(notificou);
    }
}
