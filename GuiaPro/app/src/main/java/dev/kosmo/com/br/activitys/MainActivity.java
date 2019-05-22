package dev.kosmo.com.br.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.PerfilInterface;
import dev.kosmo.com.br.interfaces.PostLoginInterface;
import dev.kosmo.com.br.interfaces.PostValidaTokenInterface;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.gets.GetPerfilAsyncTask;
import dev.kosmo.com.br.task.posts.PostLoginAsyncTask;
import dev.kosmo.com.br.task.posts.PostValidaTokenAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.StatusAplicativo;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class MainActivity extends Activity implements PostLoginInterface, PerfilInterface, PostValidaTokenInterface{

    private Button btnEntrar;
    private TextView tvCadastre;
    private TextView tvSenha;
    private EditText edtEmail;
    private EditText edtSenha;

    private Usuario usuario;

    private PostLoginInterface postLoginInterface = this;
    private PerfilInterface perfilInterface = this;
    private PostValidaTokenInterface postValidaTokenInterface = this;

    private final String MENSAGEM_ERRO_LOGIN = "Sem retorno do servidor!";
    private final String API_LOGIN = "login";
    private final String API_PERFIL = "perfil_logado";
    private final String API_VALIDA_TOKEN = "valida_token";

    private GuiaProDao guiaProDao;
    private StatusAplicativo statusAplicativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvCadastre = (TextView) findViewById(R.id.tvCadastre);
        tvSenha = (TextView) findViewById(R.id.tvSenha);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        /*edtEmail.setText("dev-noob");*/
        //edtSenha.setText("12345");

        guiaProDao = (GuiaProDao) getApplication();
        statusAplicativo = new StatusAplicativo(this);

        verificaTolken();
        acoes();
    }

    private void acoes(){

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtEmail.getText().toString().trim().equals("")){
                    edtEmail.setError("Digite o login.");
                    return;
                }

                if(edtSenha.getText().toString().trim().equals("")){
                    edtSenha.setError("Digite a senha.");
                    return;
                }

                if(!FerramentasBasicas.isOnline(view.getContext())){
                    InformacaoDialog informacaoDialog = new InformacaoDialog(view.getContext());
                    informacaoDialog.gerarDialog("Sem acesso a internet!\n Tente novamente.");
                    return;
                }

                usuario = new Usuario();
                usuario.setEmail(edtEmail.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());

                PostLoginAsyncTask postLoginAsyncTask = new PostLoginAsyncTask(view.getContext(), usuario, postLoginInterface);
                postLoginAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_LOGIN);

            }
        });

        tvCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        tvSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), SenhaActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void verificaTolken(){
        List<Usuario> lista = guiaProDao.getDaoSession().getUsuarioDao().loadAll();

        if(!lista.isEmpty()){
           usuario = lista.get(0);
           if(statusAplicativo.isOnline()){
               PostValidaTokenAsyncTask postValidaTokenAsyncTask = new PostValidaTokenAsyncTask(this,usuario.getToken(),postValidaTokenInterface);
               postValidaTokenAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_VALIDA_TOKEN);
           }else{
               entrarAplicativo();
           }
        }
    }

    @Override
    public void postLogin(HashMap<String, String> hash) {
        InformacaoDialog informacaoDialog = new InformacaoDialog(this);

        if(hash.isEmpty()){
            informacaoDialog.gerarDialog(MENSAGEM_ERRO_LOGIN);
            return;
        }
        if(hash.get("erro") != null){
            informacaoDialog.gerarDialog(hash.get("erro"));
            return;
        }

        usuario.setToken(hash.get("token"));

        GetPerfilAsyncTask getPerfilAsyncTask = new GetPerfilAsyncTask(this,
                perfilInterface,
                usuario.getToken());

        getPerfilAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_PERFIL);
    }

    @Override
    public void getPerfil(Perfil perfil) {
        InformacaoDialog informacaoDialog = new InformacaoDialog(this);

        if(perfil == null || perfil.getId() == null){
            informacaoDialog.gerarDialog(MENSAGEM_ERRO_LOGIN);
            return;
        }

        List<Usuario> lista = guiaProDao.getDaoSession().getUsuarioDao().loadAll();

        usuario.setPerfil(perfil);
        guiaProDao.getDaoSession().getUsuarioDao().insertOrReplace(usuario);
        guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(usuario.getPerfil());
        //guiaProDao.getDaoSession().getEnderecoDao().insertOrReplace(usuario.getPerfil().getEndereco());
        guiaProDao.getDaoSession().getTipoPerfilDao().insertOrReplace(usuario.getPerfil().getTipoPerfil());
        entrarAplicativo();
    }

    @Override
    public void getPerfis(List<Perfil> perfis) {

    }

    private void entrarAplicativo(){
        VariaveisEstaticas.setUsuario(usuario);

        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void postValidaToken(boolean valido) {
        if(valido){
            GetPerfilAsyncTask getPerfilAsyncTask = new GetPerfilAsyncTask(this,
                    perfilInterface,
                    usuario.getToken());
            getPerfilAsyncTask.execute(FerramentasBasicas.getURLAPI() + API_PERFIL);
        }else{
            guiaProDao.getDaoSession().getUsuarioDao().delete(usuario);
            usuario = new Usuario();
        }
    }
}
