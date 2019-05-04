package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.interfaces.PutEnderecoInterface;
import dev.kosmo.com.br.models.Endereco;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetPerfilAsyncTask;
import dev.kosmo.com.br.task.put.PutEnderecoAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class AlterarEnderecoFragment extends Fragment implements PutEnderecoInterface, PerfilInterface {

    private EditText edtCEP;
    private EditText edtNumero;
    private EditText edtLogradouro;
    private EditText edtComplemento;
    private EditText edtBairro;
    private EditText edtCidade;
    private EditText edtUf;
    private EditText edtPais;

    private Button btnVoltar;
    private Button btnSalvar;

    private InformacaoDialog informacaoDialog;

    private final String API_PERFIL = "perfil_logado";
    private final String API_ENDERECO = "mobile/endereco/";
    private final String API_ENDERECO_PRO = "mobile/enderecopro/";
    private PerfilInterface perfilInterface = this;
    private PutEnderecoInterface putEnderecoInterface = this;
    private Usuario usuario;
    private GuiaProDao guiaProDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alterar_endereco, container, false);

        edtCEP = (EditText) view.findViewById(R.id.edtCEP);
        edtNumero = (EditText) view.findViewById(R.id.edtNumero);
        edtLogradouro = (EditText) view.findViewById(R.id.edtLogradouro);
        edtComplemento = (EditText) view.findViewById(R.id.edtComplemento);
        edtBairro = (EditText) view.findViewById(R.id.edtBairro);
        edtCidade = (EditText) view.findViewById(R.id.edtCidade);
        edtUf = (EditText) view.findViewById(R.id.edtUf);
        edtPais = (EditText) view.findViewById(R.id.edtPais);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);

        informacaoDialog = new InformacaoDialog(getContext());
        usuario = VariaveisEstaticas.getUsuario();
        guiaProDao = (GuiaProDao) getActivity().getApplication();

        acoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarEndereco();
    }

    private void acoes(){

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
                Perfil perfil = VariaveisEstaticas.getUsuario().getPerfil();
                PutEnderecoAsyncTask putEnderecoAsyncTask = new PutEnderecoAsyncTask(getContext(), montarJsonParaEnviar(), putEnderecoInterface);
                putEnderecoAsyncTask.execute(FerramentasBasicas.getURL()
                        + ( perfil.getTipoPerfil().getDescricao().equals("Profissional") ? API_ENDERECO_PRO : API_ENDERECO )
                        + VariaveisEstaticas.getUsuario().getPerfil().getEndereco().getId());
            }
        });

    }

    private void carregarEndereco(){
        Endereco endereco = VariaveisEstaticas.getUsuario().getPerfil().getEndereco();

        edtCEP.setText(endereco.getCep());
        edtNumero.setText(endereco.getNumero());
        edtLogradouro.setText(endereco.getLogradouro());
        edtComplemento.setText(endereco.getComplemento());
        edtBairro.setText(endereco.getBairro());
        edtCidade.setText(endereco.getCidade());
        edtUf.setText(endereco.getUf());
        edtPais.setText(endereco.getPais());
    }

    private JSONObject montarJsonParaEnviar(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", VariaveisEstaticas.getUsuario().getPerfil().getId());
            jsonObject.put("cep", edtCEP.getText().toString());
            jsonObject.put("numero", edtNumero.getText().toString());
            jsonObject.put("logradouro", edtLogradouro.getText().toString());
            jsonObject.put("complemento", edtComplemento.getText().toString());
            jsonObject.put("bairro", edtBairro.getText().toString());
            jsonObject.put("cidade", edtCidade.getText().toString());
            jsonObject.put("uf", edtUf.getText().toString());
            jsonObject.put("pais", edtPais.getText().toString());
            jsonObject.put("latitude", "");
            jsonObject.put("longitude", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    private boolean validarCampos(){

        if(edtCEP.getText().toString().isEmpty()){
            edtCEP.setError("Informe o CEP!");
            return false;
        }
        if(edtNumero.getText().toString().isEmpty()){
            edtNumero.setError("Informe o número!");
            return false;
        }
        if(edtLogradouro.getText().toString().isEmpty()){
            edtLogradouro.setError("Informe o logradouro!");
            return false;
        }
        if(edtBairro.getText().toString().isEmpty()){
            edtBairro.setError("Informe o bairro!");
            return false;
        }
        if(edtCidade.getText().toString().isEmpty()){
            edtCidade.setError("Informe o cidade!");
            return false;
        }
        if(edtUf.getText().toString().isEmpty()){
            edtUf.setError("Informe o estado!");
            return false;
        }
        if(edtPais.getText().toString().isEmpty()){
            edtPais.setError("Informe o país!");
            return false;
        }

        return true;
    }

    @Override
    public void retornoAlteracao(boolean alterou) {
        if(alterou){
            informacaoDialog.gerarDialog("Dados atualizados com sucesso!");
            GetPerfilAsyncTask getPerfilAsyncTask = new GetPerfilAsyncTask(getContext(),
                    perfilInterface,
                    usuario.getToken());
            getPerfilAsyncTask.execute(FerramentasBasicas.getURL() + API_PERFIL);
        }else{
            informacaoDialog.gerarDialog("Não foi possível atualizar o dados!");
        }
    }

    @Override
    public void getPerfil(Perfil perfil) {
        usuario.setPerfil(perfil);
        guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(usuario.getPerfil());
        VariaveisEstaticas.getFragmentInterface().voltar();

    }

    @Override
    public void getPerfis(List<Perfil> perfis) {

    }
}
