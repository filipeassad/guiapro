package dev.kosmo.com.br.task.posts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import dev.kosmo.com.br.interfaces.PostValidaTokenInterface;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class PostValidaTokenAsyncTask extends AsyncTask<String, String, Boolean> {

    private Context contexto;
    private ProgressDialog progress;
    private String token;
    private PostValidaTokenInterface postValidaTokenInterface;

    public PostValidaTokenAsyncTask(Context contexto, String token, PostValidaTokenInterface postValidaTokenInterface) {
        this.contexto = contexto;
        this.token = token;
        this.postValidaTokenInterface = postValidaTokenInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(contexto);
        progress.setMessage("Logando...");
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
            conn.addRequestProperty("x-access-token", token);
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            httpResponse = conn.getResponseCode();
            JSONObject response;
            HashMap<String,String> hash = new HashMap<>();

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

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progress.dismiss();
        postValidaTokenInterface.postValidaToken(aBoolean);
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
}
