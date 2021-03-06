package dev.kosmo.com.br.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.kosmo.com.br.models.Atendimento;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.Endereco;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.models.HistoricoAtendimento;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.TipoPerfil;

public class CarregaDados {

    private final String FORMATO_DATA_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

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

            /*if(jsonObject.has("success"))
                return perfil;*/

            perfil.setId(jsonObject.getLong("id"));
            perfil.setNome(jsonObject.has("nome") ? jsonObject.getString("nome") : "");
            perfil.setSobrenome(jsonObject.has("sobrenome") ? jsonObject.getString("sobrenome") : "");
            perfil.setDataNascimentoByString(jsonObject.has("datanascimento") ? jsonObject.getString("datanascimento") : "");
            perfil.setCpf(jsonObject.has("cpf") ? jsonObject.getString("cpf") : "");
            perfil.setSexo(jsonObject.has("sexo") ? jsonObject.getString("sexo") : "");
            perfil.setCelular(jsonObject.has("celular") ? jsonObject.getString("celular") : "");
            perfil.setCelularwhats(jsonObject.has("celularwhats") ? jsonObject.getString("celularwhats") : "");
            perfil.setUrlImg(jsonObject.has("urlimg") ? jsonObject.getString("urlimg") : "");
            perfil.setAtivo(jsonObject.has("ativo") ? jsonObject.getString("ativo") : "");

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

    public List<Atendimento> montaAtendimentos(JSONArray jsonArray){
        List<Atendimento> atendimentos = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                atendimentos.add(montaAtendimento(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return atendimentos;
    }

    public Atendimento montaAtendimento(JSONObject jsonObject){
        Atendimento atendimento = new Atendimento();

        try {

            atendimento.setId(jsonObject.getLong("id"));
            atendimento.setData(jsonObject.has("data") ? FerramentasBasicas.converterStringParaData(jsonObject.getString("data"), FORMATO_DATA_SERVER) : new Date());
            atendimento.setTitulo(jsonObject.has("titulo") ? jsonObject.getString("titulo") : "");
            atendimento.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
            atendimento.setClienteId(jsonObject.has("clienteId") ? jsonObject.getLong("clienteId") : 0);
            atendimento.setProfissionalId(jsonObject.has("profissionalId") ? jsonObject.getLong("profissionalId") : 0);
            atendimento.setTipoAtendimentoId(jsonObject.has("tipoatendimentoId") ? jsonObject.getLong("tipoatendimentoId") : 0);
            atendimento.setSitucaoId(jsonObject.has("situacaoId") ? jsonObject.getLong("situacaoId") : 0);
            atendimento.setCategoriaId(jsonObject.has("categoriaId") ? jsonObject.getLong("categoriaId") : 0);

            if(jsonObject.has("cliente"))
                atendimento.setCliente(montaPerfil(jsonObject.getJSONObject("cliente")));
            if(jsonObject.has("profissional"))
                atendimento.setProfisisonal(montaPerfil(jsonObject.getJSONObject("profissional")));
            if(jsonObject.has("categoria"))
                atendimento.setCategoria(montaCategoria(jsonObject.getJSONObject("categoria")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return atendimento;
    }

    public List<HistoricoAtendimento> montaHistoricosAtendimento(JSONArray jsonArray){
        List<HistoricoAtendimento> historicoAtendimentos = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                historicoAtendimentos.add(montaHistoricoAtendimento(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return historicoAtendimentos;
    }

    public HistoricoAtendimento montaHistoricoAtendimento(JSONObject jsonObject){
        HistoricoAtendimento historicoAtendimento = new HistoricoAtendimento();

        try {

            historicoAtendimento.setId(jsonObject.getLong("id"));
            historicoAtendimento.setData(jsonObject.has("data") ? FerramentasBasicas.converterStringParaData(jsonObject.getString("data"), FORMATO_DATA_SERVER) : new Date());
            historicoAtendimento.setTitulo(jsonObject.has("titulo") ? jsonObject.getString("titulo") : "");
            historicoAtendimento.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
            historicoAtendimento.setClienteId(jsonObject.has("clienteId") ? jsonObject.getLong("clienteId") : 0);
            historicoAtendimento.setProfissionalId(jsonObject.has("profissionalId") ? jsonObject.getLong("profissionalId") : 0);
            historicoAtendimento.setTipoAtendimentoId(jsonObject.has("tipoatendimentoId") ? jsonObject.getLong("tipoatendimentoId") : 0);
            historicoAtendimento.setSitucaoId(jsonObject.has("situacaoId") ? jsonObject.getLong("situacaoId") : 0);
            historicoAtendimento.setCategoriaId(jsonObject.has("categoriaId") ? jsonObject.getLong("categoriaId") : 0);
            historicoAtendimento.setAtendimentoId(jsonObject.has("atendimentoId") ? jsonObject.getLong("atendimentoId") : 0);

            if(jsonObject.has("cliente"))
                historicoAtendimento.setCliente(montaPerfil(jsonObject.getJSONObject("cliente")));
            if(jsonObject.has("profissional"))
                historicoAtendimento.setProfisisonal(montaPerfil(jsonObject.getJSONObject("profissional")));
            if(jsonObject.has("categoria"))
                historicoAtendimento.setCategoria(montaCategoria(jsonObject.getJSONObject("categoria")));
            if(jsonObject.has("atendimento"))
                historicoAtendimento.setAtendimento(montaAtendimento(jsonObject.getJSONObject("atendimento")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return historicoAtendimento;
    }

    public List<Categoria> montaCategorias(JSONArray jsonArray){

        List<Categoria> categorias = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                categorias.add(montaCategoria(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return categorias;
    }

    public Categoria montaCategoria(JSONObject jsonObject){

        Categoria categoria = new Categoria();

        try {
            categoria.setId(jsonObject.getLong("id"));
            categoria.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");
            categoria.setSigla(jsonObject.has("sigla") ? jsonObject.getString("sigla") : "");
            categoria.setUrlImg(jsonObject.has("urlimg") ? jsonObject.getString("urlimg") : "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoria;

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
            endereco.setComplemento(jsonObject.has("complemento") && jsonObject.getString("complemento").equals("null") == false ? jsonObject.getString("complemento") : "");
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

    public List<Especialidades> montarEspecialidades(JSONArray jsonArray){
        List<Especialidades> especialidades = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                especialidades.add(montarEspecialidade(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return especialidades;
    }

    private Especialidades montarEspecialidade(JSONObject jsonObject){

        Especialidades especialidades = new Especialidades();

        try{
            especialidades.setId(jsonObject.getLong("id"));
            especialidades.setCategoriaId(jsonObject.has("categoriaId") ? jsonObject.getLong("categoriaId") : 0);
            especialidades.setProfissionalId(jsonObject.has("profissionalId") ? jsonObject.getLong("profissionalId") : 0);
            especialidades.setDescricao(jsonObject.has("descricao") ? jsonObject.getString("descricao") : "");

            if(jsonObject.has("profissional"))
                especialidades.setProfisisonal(montaPerfil(jsonObject.getJSONObject("profissional")));
            if(jsonObject.has("categoria"))
                especialidades.setCategoria(montaCategoria(jsonObject.getJSONObject("categoria")));

        }catch (JSONException e){
            e.printStackTrace();
        }

        return especialidades;
    }
}
