package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONArray;
import java.net.HttpURLConnection;
import java.net.URL;
import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.utils.CarregaDados;
import dev.kosmo.com.br.utils.FerramentasBasicas;

public class GetPerfisAsyncTask extends AsyncTask<String, String, JSONArray> {

    private Context contexto;
    private ProgressDialog progress;
    private PerfilInterface perfilInterface;
    private String token;

    public GetPerfisAsyncTask(Context contexto, PerfilInterface perfilInterface, String token) {
        this.contexto = contexto;
        this.perfilInterface = perfilInterface;
        this.token = token;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Carregando...");
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
        CarregaDados carregaDados = new CarregaDados();
        perfilInterface.getPerfis(carregaDados.montaPerfis(jsonArray));
        progress.dismiss();
    }
}
