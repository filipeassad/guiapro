package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.EspecialidadesInterface;
import dev.kosmo.com.br.models.Categoria;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetEspecialidadesAsynctask;
import dev.kosmo.com.br.task.posts.PostEspecialidadesAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class EspecialidadesFragment extends Fragment implements EspecialidadesInterface {

    private EditText edtDescricao;
    private Spinner spCategorias;
    private Button btnVoltar;
    private Button btnSalvar;
    private LinearLayout llListaEspecialidades;

    private Usuario usuarioLogado;
    private Categoria categoriaSelecionada;
    private EspecialidadesInterface especialidadesInterface = this;

    private final String API_ESPECIALIDADES = "mobile/especialidades";

    private InformacaoDialog informacaoDialog;

    private List<Especialidades> especialidadesCadastradas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_especialidades,
                container,
                false);

        edtDescricao = (EditText) view.findViewById(R.id.edtDescricao);
        spCategorias = (Spinner) view.findViewById(R.id.spCategorias);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        llListaEspecialidades = (LinearLayout) view.findViewById(R.id.llListaEspecialidades);

        usuarioLogado = VariaveisEstaticas.getUsuario();
        carregarSpinnerCategorias();
        acoesBotoes();

        informacaoDialog = new InformacaoDialog(getContext());

        return view;
    }

    private void carregarSpinnerCategorias(){
        final ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,
                usuarioLogado.getPerfil().getCategorias());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategorias.setAdapter(adapter);

        spCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                categoriaSelecionada = (Categoria) adapterView.getItemAtPosition(posicao);
            }
        });
    }

    private void acoesBotoes(){

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
                PostEspecialidadesAsyncTask postEspecialidadesAsyncTask =
                        new PostEspecialidadesAsyncTask(getContext(),
                                montarJson(),
                                especialidadesInterface);

                postEspecialidadesAsyncTask.execute(FerramentasBasicas.getURLAPI()
                        + API_ESPECIALIDADES);
            }
        });
    }

    private boolean validarCampos(){
        if(edtDescricao.getText().toString().trim().equals("")){
            edtDescricao.setError("Informe uma descrição");
            return false;
        }
        if(categoriaSelecionada == null){
            Toast.makeText(getContext(),"Selecione uma categoria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private JSONObject montarJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("descricao", edtDescricao.getText().toString());
            jsonObject.put("profissionalId", usuarioLogado.getPerfil().getId());
            jsonObject.put("categoriaId", categoriaSelecionada.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private void limparCampos(){
        edtDescricao.setText("");
        categoriaSelecionada = null;
        spCategorias.setSelection(0);
    }

    private void buscarEspecialidades(){
        especialidadesCadastradas = new ArrayList<>();
        GetEspecialidadesAsynctask getEspecialidadesAsynctask = new GetEspecialidadesAsynctask(
                getContext(),
                especialidadesInterface);
        getEspecialidadesAsynctask.execute(FerramentasBasicas.getURLAPI()
                + API_ESPECIALIDADES
                + "/" + usuarioLogado.getPerfil().getId());
    }

    private void carregarEspecialidadesCadastradas(List<Especialidades> especialidadesRetornadas){

    }

    @Override
    public void retornoGetEspecialidades(List<Especialidades> especialidades) {
        if(especialidades != null && especialidades.size() > 0)
            carregarEspecialidadesCadastradas(especialidades);
    }

    @Override
    public void retornoPostEspecialidades(boolean cadastrou) {

        if(cadastrou){
            limparCampos();
            informacaoDialog.gerarDialog("Especialidade salva com sucesso!");
        }else{
            informacaoDialog.gerarDialog("Não foi possível salvar a especialidade!");
        }

    }
}