package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;

import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.utils.CarregaDados;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class GetAtendimentoPorClienteAsyncTask extends AsyncTask<String, String, JSONArray> {

    private Context contexto;
    private AtendimentoInterface atendimentoInterface;
    private ProgressDialog progress;
    private CarregaDados carregaDados;

    public GetAtendimentoPorClienteAsyncTask(Context contexto, AtendimentoInterface atendimentoInterface) {
        this.contexto = contexto;
        this.atendimentoInterface = atendimentoInterface;
        this.carregaDados = new CarregaDados();
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
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
            urlConnection.addRequestProperty("x-access-token", VariaveisEstaticas.getUsuario().getToken());
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
        atendimentoInterface.retornoBuscaAtendimentos(carregaDados.montaAtendimentos(jsonArray));
    }
}
