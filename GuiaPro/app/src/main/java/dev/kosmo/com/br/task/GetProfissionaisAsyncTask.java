package dev.kosmo.com.br.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.interfaces.ProfissionaisInterface;
import dev.kosmo.com.br.models.Profissional;

/**
 * Created by 0118431 on 19/04/2018.
 */

public class GetProfissionaisAsyncTask extends AsyncTask<String, String, JSONArray> {

    private Context contexto;
    private ProfissionaisInterface profissionaisInterface;
    private ProgressDialog progress;

    public GetProfissionaisAsyncTask(Context contexto, ProfissionaisInterface profissionaisInterface) {
        this.contexto = contexto;
        this.profissionaisInterface = profissionaisInterface;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Buscando Profissionais...");
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
    protected JSONArray doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;
        JSONArray response = new JSONArray();

        try {
            url = new URL(strings[0]);

            Log.v("Url acessada", url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type","application/json");
            //urlConnection.addRequestProperty("Authorization","Token " + VariaveisEstaticas.getAutenticacao().getHash());
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = readStream(urlConnection.getInputStream());
                Log.v("CatalogClient", responseString);
                response = new JSONArray(responseString);
            }else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                return null;
            }else{
                Log.v("CatalogClient", "Response code:" + responseCode);
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

        List<Profissional> lista = new ArrayList<>();
        //jsonArray = jsonObject.getJSONArray("results");

        try {

            for(int i = 0; i < jsonArray.length() ; i++){

                JSONObject linha = jsonArray.getJSONObject(i);

                Profissional profissional = new Profissional();

                profissional.setId(linha.getInt("id"));
                profissional.setNome(linha.getString("nome"));
                profissional.setCelular(linha.getString("ddd_celular") + linha.getString("celular"));
                profissional.setUrlImg(linha.getString("foto"));

                lista.add(profissional);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progress.dismiss();
        profissionaisInterface.getProfissionais(lista);

    }
}
