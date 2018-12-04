package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.interfaces.PutAlterarClienteInterface;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetPerfilAsyncTask;
import dev.kosmo.com.br.task.posts.PostAlterarClienteAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.MaskEditUtil;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class AlterarPerfilFragment extends Fragment implements PutAlterarClienteInterface, PerfilInterface {

    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtDataNascimento;
    private EditText edtCpf;
    private EditText edtCelular;

    private Button btnVoltar;
    private Button btnSalvar;

    private RadioGroup rdSexo;

    private InformacaoDialog informacaoDialog;
    private final String FORMATO_DATA_BR = "dd/MM/yyyy";
    private final String URL_ALTERAR_CLIENTE = "mobile/alterarcliente";
    private PutAlterarClienteInterface putAlterarClienteInterface = this;
    private final String SIGLA_SEXO_MASCULINO = "M";
    private final String SIGLA_SEXO_FEMININO = "F";
    private String sexoSelecionado = "M";
    private final String API_PERFIL = "perfil_logado";

    private PerfilInterface perfilInterface = this;
    private Usuario usuario;
    private GuiaProDao guiaProDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alterar_perfil, container, false);

        edtNome = (EditText) view.findViewById(R.id.edtNome);
        edtSobrenome = (EditText) view.findViewById(R.id.edtSobrenome);
        edtDataNascimento = (EditText) view.findViewById(R.id.edtDataNascimento);
        edtCpf = (EditText) view.findViewById(R.id.edtCpf);
        edtCelular = (EditText) view.findViewById(R.id.edtCelular);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        rdSexo = (RadioGroup) view.findViewById(R.id.rdSexo);

        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        edtDataNascimento.addTextChangedListener(MaskEditUtil.mask(edtDataNascimento, MaskEditUtil.FORMAT_DATE));
        edtCelular.addTextChangedListener(MaskEditUtil.mask(edtCelular, MaskEditUtil.FORMAT_FONE));

        informacaoDialog = new InformacaoDialog(getContext());
        guiaProDao = (GuiaProDao) getActivity().getApplication();
        usuario = VariaveisEstaticas.getUsuario();

        acoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarDados();
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
            public void onClick(View view) {
                validarCampos();
                PostAlterarClienteAsyncTask postAlterarClienteAsyncTask = new PostAlterarClienteAsyncTask(getContext(), montarJsonParaEnviar(), putAlterarClienteInterface);
                postAlterarClienteAsyncTask.execute(FerramentasBasicas.getURL() + URL_ALTERAR_CLIENTE);
            }
        });

        rdSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton button = (RadioButton) radioGroup.findViewById(checkedId);
                sexoSelecionado = button.getText().toString();
            }
        });
    }

    private void carregarDados(){

        Perfil perfil = VariaveisEstaticas.getUsuario().getPerfil();

        edtNome.setText(perfil.getNome());
        edtSobrenome.setText(perfil.getSobrenome());
        edtDataNascimento.setText(FerramentasBasicas.converterDataParaString(perfil.getDataNascimento(), FORMATO_DATA_BR));
        edtCpf.setText(perfil.getCpf());
        edtCelular.setText(perfil.getCelular());
        if(perfil.getSexo().equals(SIGLA_SEXO_MASCULINO)){
            sexoSelecionado = SIGLA_SEXO_MASCULINO;
            rdSexo.check(R.id.rbM);
        }else{
            sexoSelecionado = SIGLA_SEXO_FEMININO;
            rdSexo.check(R.id.rbF);
        }
    }

    private JSONObject montarJsonParaEnviar(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", VariaveisEstaticas.getUsuario().getPerfil().getId());
            jsonObject.put("nome", edtNome.getText().toString());
            jsonObject.put("sobrenome", edtSobrenome.getText().toString());
            jsonObject.put("datanascimento", edtDataNascimento.getText().toString());
            jsonObject.put("cpf", edtCpf.getText().toString());
            jsonObject.put("sexo", sexoSelecionado);
            jsonObject.put("celular", edtCelular.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private boolean validarCampos(){
        if(edtNome.getText().toString().isEmpty()){
            edtNome.setError("Informe o nome!");
            return false;
        }
        if(edtSobrenome.getText().toString().isEmpty()){
            edtSobrenome.setError("Informe o sobrenome!");
            return false;
        }
        if(edtDataNascimento.getText().toString().isEmpty()){
            edtDataNascimento.setError("Informe a data de nascimento!");
            return false;
        }
        if(edtCpf.getText().toString().isEmpty()){
            edtCpf.setError("Informe o CPF!");
            return false;
        }
        if(edtCelular.getText().toString().isEmpty()){
            edtCelular.setError("Informe o celular!");
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
