package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;

import dev.kosmo.com.br.interfaces.HistoricoAtendimentoInterface;
import dev.kosmo.com.br.utils.CarregaDados;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class GetHistoricoAtendimentoAsyncTask extends AsyncTask<String, String, JSONArray> {

    private Context contexto;
    private HistoricoAtendimentoInterface historicoAtendimentoInterface;
    private ProgressDialog progress;
    private CarregaDados carregaDados;

    public GetHistoricoAtendimentoAsyncTask(Context contexto, HistoricoAtendimentoInterface historicoAtendimentoInterface) {
        this.contexto = contexto;
        this.historicoAtendimentoInterface = historicoAtendimentoInterface;
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
        historicoAtendimentoInterface.retornoBuscaHistoricosAtendimento(carregaDados.montaHistoricosAtendimento(jsonArray));
    }
}
