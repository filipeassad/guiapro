package dev.kosmo.com.br.task.gets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.Endereco;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.TipoPerfil;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class GetPerfilAsyncTask extends AsyncTask<String, String, JSONObject> {

    private Context contexto;
    private ProgressDialog progress;
    private PerfilInterface perfilInterface;
    private String token;

    public GetPerfilAsyncTask(Context contexto, PerfilInterface perfilInterface, String token) {
        this.contexto = contexto;
        this.perfilInterface = perfilInterface;
        this.token = token;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Logando...");
        progress.show();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;
        JSONObject response = new JSONObject();

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.addRequestProperty("x-access-token", token);

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = FerramentasBasicas.readStream(urlConnection.getInputStream());
                response = new JSONObject(responseString);
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
    protected void onPostExecute(JSONObject jsonObject) {
        perfilInterface.getPerfil(montaPerfil(jsonObject));
        progress.dismiss();
    }

    private Perfil montaPerfil(JSONObject jsonObject){

        Perfil perfil = new Perfil();

        try {

            if(jsonObject.has("success"))
                return perfil;

            perfil.setId(jsonObject.getLong("id"));
            perfil.setNome(jsonObject.has("nome") ? jsonObject.getString("nome") : "");
            perfil.setSobrenome(jsonObject.has("sobrenome") ? jsonObject.getString("sobrenome") : "");
            perfil.setDataNascimentoByString(jsonObject.has("datanascimento") ? jsonObject.getString("datanascimento") : "");
            perfil.setCpf(jsonObject.has("cpf") ? jsonObject.getString("cpf") : "");
            perfil.setSexo(jsonObject.has("sexo") ? jsonObject.getString("sexo") : "");
            perfil.setCelular(jsonObject.has("celular") ? jsonObject.getString("celular") : "");
            perfil.setUrlImg(jsonObject.has("urlimg") ? jsonObject.getString("urlimg") : "");

            if(jsonObject.has("tipoperfil"))
                montaTipoPerfil(perfil, jsonObject.getJSONObject("tipoperfil"));

            if(jsonObject.has("endereco"))
                montaEndereco(perfil, jsonObject.getJSONObject("tipoperfil"));

            if(jsonObject.has("categorias"))
                montaCategorias(perfil, jsonObject.getJSONArray("categorias"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return perfil;
    }

    private void montaTipoPerfil(Perfil perfil, JSONObject jsonObject){

        TipoPerfil tipoPerfil = new TipoPerfil();

        try {

            tipoPerfil.setId(jsonObject.getLong("id"));
            tipoPerfil.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
            tipoPerfil.setSigla(jsonObject.has("sigla") ? jsonObject.getString("sigla") : "");

            perfil.setTipoPerfilId(tipoPerfil.getId());
            perfil.setTipoPerfil(tipoPerfil);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void montaEndereco(Perfil perfil, JSONObject jsonObject){

        Endereco endereco = new Endereco();

        try {

            endereco.setId(jsonObject.getLong("id"));
            endereco.setCep(jsonObject.has("cep") ? jsonObject.getString("cep") : "");
            endereco.setNumero(jsonObject.has("numero") ? jsonObject.getString("numero") : "");
            endereco.setLogradouro(jsonObject.has("logradouro") ? jsonObject.getString("logradouro") : "");
            endereco.setComplemento(jsonObject.has("complemento") ? jsonObject.getString("complemento") : "");
            endereco.setBairro(jsonObject.has("bairro") ? jsonObject.getString("bairro") : "");
            endereco.setCidade(jsonObject.has("cidade") ? jsonObject.getString("cidade") : "");
            endereco.setUf(jsonObject.has("uf") ? jsonObject.getString("uf") : "");
            endereco.setPais(jsonObject.has("pais") ? jsonObject.getString("pais") : "");
            endereco.setLatitude(jsonObject.has("latitude") ? jsonObject.getString("latitude") : "");
            endereco.setLongitude(jsonObject.has("longitude") ? jsonObject.getString("longitude") : "");

            perfil.setEnderecoId(endereco.getId());
            perfil.setEndereco(endereco);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void montaCategorias(Perfil perfil, JSONArray jsonArray){

        if(jsonArray != null && jsonArray.length() > 0){
            try {

                for(int i = 0 ; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Categoria categoria = new Categoria();

                    categoria.setId(jsonObject.getLong("id"));
                    categoria.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
                    categoria.setSigla(jsonObject.has("sigla") ? jsonObject.getString("sigla") : "");
                    categoria.setUrlImg(jsonObject.has("urlimg") ? jsonObject.getString("urlimg") : "");

                    perfil.getCategorias().add(categoria);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
