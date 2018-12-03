package dev.kosmo.com.br.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.CadastroClienteInterface;
import dev.kosmo.com.br.interfaces.DialogFinalizadoInterface;
import dev.kosmo.com.br.task.posts.PostCadastrarClienteAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.MaskEditUtil;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class CadastroActivity extends Activity implements CadastroClienteInterface, DialogFinalizadoInterface {

    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtDataNascimento;
    private EditText edtCpf;
    private EditText edtCelular;

    private Button btnCadastrar;
    private Button btnVoltar;

    private RadioGroup rdSexo;

    private CadastroClienteInterface cadastroClienteInterface = this;
    private DialogFinalizadoInterface dialogFinalizadoInterface = this;
    private InformacaoDialog informacaoDialog = new InformacaoDialog(this);

    private final String URL_CADASTRO_CLIENTE = "mobile/cadastrocliente";
    private final String SIGLA_MASCULINO = "M";
    private String sexoSelecionado = "M";
    private boolean cadastrou = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtSobrenome = (EditText) findViewById(R.id.edtSobrenome);
        edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtCelular = (EditText) findViewById(R.id.edtCelular);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        rdSexo = (RadioGroup) findViewById(R.id.rdSexo);

        rdSexo.check(R.id.rbM);
        sexoSelecionado = SIGLA_MASCULINO;

        edtCpf.addTextChangedListener(MaskEditUtil.mask(edtCpf, MaskEditUtil.FORMAT_CPF));
        edtDataNascimento.addTextChangedListener(MaskEditUtil.mask(edtDataNascimento, MaskEditUtil.FORMAT_DATE));
        edtCelular.addTextChangedListener(MaskEditUtil.mask(edtCelular, MaskEditUtil.FORMAT_FONE));

        acoes();
    }

    public void acoes(){
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
                PostCadastrarClienteAsyncTask postCadastrarClienteAsyncTask = new PostCadastrarClienteAsyncTask(view.getContext(), montarJsonParaEnviar(), cadastroClienteInterface);
                postCadastrarClienteAsyncTask.execute(FerramentasBasicas.getURL() + URL_CADASTRO_CLIENTE);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    private boolean validarCampos(){
        if(edtEmail.getText().toString().isEmpty()){
            edtEmail.setError("Informe o email!");
            return false;
        }
        if(edtSenha.getText().toString().isEmpty()){
            edtSenha.setError("Informe o senha!");
            return false;
        }
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

    private JSONObject montarJsonParaEnviar(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", edtEmail.getText().toString());
            jsonObject.put("senha", edtSenha.getText().toString());
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

    @Override
    public void retornoCadastro(boolean cadastrou) {
        if(cadastrou){
            this.cadastrou = cadastrou;
            informacaoDialog.gerarDialog("Você foi cadastrado com sucesso!", dialogFinalizadoInterface);
        }else{
            this.cadastrou = cadastrou;
            informacaoDialog.gerarDialog("Não foi possível efetuar o cadastro!");
        }
    }

    @Override
    public void finalizado() {
        if(cadastrou) {
           limpar();
        }
    }

    private void limpar(){
        edtEmail.setText("");
        edtSenha.setText("");
        edtNome.setText("");
        edtSobrenome.setText("");
        edtDataNascimento.setText("");
        edtCpf.setText("");
        edtCelular.setText("");
        rdSexo.check(R.id.rbM);
        sexoSelecionado = SIGLA_MASCULINO;
    }
}