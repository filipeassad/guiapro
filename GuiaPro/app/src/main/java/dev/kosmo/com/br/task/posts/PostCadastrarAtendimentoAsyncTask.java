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
import java.util.HashMap;

import dev.kosmo.com.br.interfaces.AtendimentoInterface;
import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class PostCadastrarAtendimentoAsyncTask extends AsyncTask<String, String, HashMap<String, Object>> {

    private Context contexto;
    private ProgressDialog progress;
    private Atendimento atendimento;
    private AtendimentoInterface atendimentoInterface;

    public PostCadastrarAtendimentoAsyncTask(Context contexto, Atendimento atendimento, AtendimentoInterface atendimentoInterface) {
        this.contexto = contexto;
        this.atendimento = atendimento;
        this.atendimentoInterface = atendimentoInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
        progress.show();
    }

    @Override
    protected HashMap<String, Object> doInBackground(String... strings) {
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

            JSONObject jsonParam = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jsonParam.put("titulo", atendimento.getTitulo());
            jsonParam.put("descricao", atendimento.getDescricao());
            jsonParam.put("profissionalId", atendimento.getProfissionalId());
            jsonParam.put("tipoatendimentoId", atendimento.getTipoAtendimentoId());
            jsonParam.put("situacaoId", atendimento.getSitucaoId());
            jsonParam.put("categoriaId", atendimento.getCategoriaId());
            jsonParam.put("clienteId", VariaveisEstaticas.getUsuario().getPerfil().getId());

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
                return null;
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HashMap<String, Object> validaResposta(JSONObject response){
        HashMap<String, Object> resultado = new HashMap<>();
        try {
            if(response.has("success") && response.getBoolean("success")){
                resultado.put("cadastrou", response.getBoolean("success"));
                resultado.put("idAtendimento", response.getLong("idAtendimento"));
            }else{
                resultado.put("cadastrou", response.getBoolean("success"));
                resultado.put("msg", response.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> resultado) {
        super.onPostExecute(resultado);
        progress.dismiss();

        boolean cadastrou = false;
        long idAtendimento = 0;

        if(resultado != null && resultado.get("cadastrou") != null){
            idAtendimento = (Boolean) resultado.get("cadastrou") ? (Long) resultado.get("idAtendimento") : 0;
            cadastrou = (Boolean) resultado.get("cadastrou");
        }

        atendimentoInterface.retornoCadastroAtendimento(cadastrou, idAtendimento);
    }
}
