package dev.kosmo.com.br.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.Endereco;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.TipoPerfil;

public class CarregaDados {

    public List<Perfil> montaPerfis(JSONArray jsonArray){
        List<Perfil> perfis = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                perfis.add(montaPerfil(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return perfis;
    }

    public Perfil montaPerfil(JSONObject jsonObject){

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
                montaEndereco(perfil, jsonObject.getJSONObject("endereco"));

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
        perfil.setCategoriasInsercao(new ArrayList<Categoria>());
        if(jsonArray != null && jsonArray.length() > 0){
            try {
                for(int i = 0 ; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if(jsonObject.has("categoria")){
                        JSONObject jsonCategoria = jsonObject.getJSONObject("categoria");
                        Categoria categoria = new Categoria();

                        categoria.setId(jsonCategoria.getLong("id"));
                        categoria.setDescricao(jsonCategoria.has("descricao") ? jsonCategoria.getString("descricao") : "");
                        categoria.setSigla(jsonCategoria.has("sigla") ? jsonCategoria.getString("sigla") : "");
                        categoria.setUrlImg(jsonCategoria.has("urlimg") ? jsonCategoria.getString("urlimg") : "");

                        perfil.getCategoriasInsercao().add(categoria);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
