package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.PutAlterarClienteInterface;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.posts.PostAlterarClienteAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class ModoDescansoFragment extends Fragment implements PutAlterarClienteInterface {

    private RadioGroup rdSituacao;

    private Usuario usuario;
    private GuiaProDao guiaProDao;
    private final String URL_ALTERAR_PROFISSIONAL = "mobile/alterarprofissional";
    private InformacaoDialog informacaoDialog;
    private String descricao;

    private PutAlterarClienteInterface putAlterarClienteInterface = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modo_descanso, container, false);

        rdSituacao = (RadioGroup) view.findViewById(R.id.rdSituacao);

        usuario = VariaveisEstaticas.getUsuario();
        guiaProDao = (GuiaProDao) getActivity().getApplication();

        informacaoDialog = new InformacaoDialog(getContext());

        carregarTela();

        acoes();
        return  view;
    }

    private void carregarTela(){
        if(usuario.getPerfil().getAtivo().equals("true")){
            rdSituacao.check(R.id.rbT);
        }else{
            rdSituacao.check(R.id.rbD);
        }
    }

    private void acoes(){
        rdSituacao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton button = (RadioButton) radioGroup.findViewById(checkedId);
                descricao = button.getText().toString();

                if(FerramentasBasicas.isOnline(getContext())){
                    PostAlterarClienteAsyncTask postAlterarClienteAsyncTask = new PostAlterarClienteAsyncTask(getContext(), montarJsonParaEnviar(), putAlterarClienteInterface);
                    postAlterarClienteAsyncTask.execute(FerramentasBasicas.getURLAPI() + URL_ALTERAR_PROFISSIONAL);
                }else{
                    informacaoDialog.gerarDialog("Não foi possível atualizar a situação!");
                    carregarTela();
                }

            }
        });
    }

    private JSONObject montarJsonParaEnviar(){
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            jsonObject.put("id", usuario.getPerfil().getId());
            jsonObject.put("nome", usuario.getPerfil().getNome());
            jsonObject.put("sobrenome", usuario.getPerfil().getSobrenome());
            jsonObject.put("datanascimento", usuario.getPerfil().getDataNascimento() != null ? simpleDateFormat.format(usuario.getPerfil().getDataNascimento()) : simpleDateFormat.format(new Date()));
            jsonObject.put("cpf", usuario.getPerfil().getCpf());
            jsonObject.put("sexo", usuario.getPerfil().getSexo());
            jsonObject.put("celular", usuario.getPerfil().getCelular());
            jsonObject.put("ativo", descricao.equals("Trabalhando") ? "true" : "false");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    @Override
    public void retornoAlteracao(boolean alterou) {
        if(alterou){
            informacaoDialog.gerarDialog("Situação atualizada com sucesso!");
            if(descricao.equals("Trabalhando")){
                usuario.getPerfil().setAtivo("true");
            }else{
                usuario.getPerfil().setAtivo("false");
            }
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(usuario.getPerfil());
        }else{
            informacaoDialog.gerarDialog("Não foi possível atualizar a situação!");
        }
    }
}
