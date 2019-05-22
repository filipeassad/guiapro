package dev.kosmo.com.br.utils;

import org.json.JSONException;
import org.json.JSONObject;

import dev.kosmo.com.br.models.Perfil;

public class MontarJson {

    public static JSONObject montarJsonPerfil(Perfil perfil){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", perfil.getId());
            jsonObject.put("nome",perfil.getNome());
            jsonObject.put("sobrenome", perfil.getSobrenome());
            jsonObject.put("datanascimento", FerramentasBasicas.converterDataParaString(perfil.getDataNascimento(),"dd/MM/yyyy"));
            jsonObject.put("cpf", perfil.getCpf());
            jsonObject.put("sexo", perfil.getSexo());
            jsonObject.put("celular", perfil.getCelular());
            jsonObject.put("ativo", perfil.getAtivo());
            jsonObject.put("urlimg", perfil.getUrlImg());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
