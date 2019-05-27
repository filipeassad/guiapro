package dev.kosmo.com.br.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;

import dev.kosmo.com.br.dao.GuiaProDao;
import dev.kosmo.com.br.dialogs.InformacaoDialog;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.interfaces.PutAlterarClienteInterface;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.task.UploadAwsS3;
import dev.kosmo.com.br.task.gets.GetImagemAsyncTask;
import dev.kosmo.com.br.task.gets.GetPerfilAsyncTask;
import dev.kosmo.com.br.task.posts.PostAlterarClienteAsyncTask;
import dev.kosmo.com.br.task.posts.PostImagemAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.MontarJson;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 09/03/2018.
 */

public class PerfilFragment extends Fragment implements ImagemInterface, PutAlterarClienteInterface {

    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtDataNascimento;
    private TextView txtCpf;
    private TextView txtCelular;
    private TextView txtSexo;
    private ImageView ivPerfil;

    private Button btnAlterar;
    private Button btnEndereco;

    private final String FORMATO_DATA_BR = "dd/MM/yyyy";
    private final String NOME_TELA_ALTERAR_DADOS_PERFIL = "AlterarDadosCliente";
    private final String SIGLA_SEXO_MASCULINO = "M";
    private final String SEXO_MASCULINO = "Masculino";
    private final String SEXO_FEMININO = "Feminino";
    private final String NOME_TELA_ENDERECO = "Endereco";
    private final String URL_POST_IMAGEM = "upload-arquivo/send-aws-mobile";
    private final String URL_ALTERAR_CLIENTE = "mobile/alterarcliente";
    private final String URL_ALTERAR_PROFISSIONAL = "mobile/alterarprofissional";
    private final String TIPO_PERFIL_PROFISSIONAL = "Profissional";

    private Bitmap imagemSelecionada = null;
    private InformacaoDialog informacaoDialog = new InformacaoDialog(getContext());
    private ImagemInterface imagemInterface = this;

    private PutAlterarClienteInterface putAlterarClienteInterface = this;

    private GuiaProDao guiaProDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(false);

        txtNome = (TextView) view.findViewById(R.id.txtNome);
        txtSobrenome = (TextView) view.findViewById(R.id.txtSobrenome);
        txtDataNascimento = (TextView) view.findViewById(R.id.txtDataNascimento);
        txtCpf = (TextView) view.findViewById(R.id.txtCpf);
        txtCelular = (TextView) view.findViewById(R.id.txtCelular);
        txtSexo = (TextView) view.findViewById(R.id.txtSexo);
        btnAlterar = (Button) view.findViewById(R.id.btnAlterar);
        btnEndereco = (Button) view.findViewById(R.id.btnEndereco);
        ivPerfil = (ImageView) view.findViewById(R.id.ivPerfil);

        guiaProDao = (GuiaProDao) getActivity().getApplication();
        VariaveisEstaticas.setImagemInterface(this);

        informacaoDialog = new InformacaoDialog(getContext());

        //AWSMobileClient.getInstance().initialize(getContext()).execute();

        carregarDados();
        acoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void carregarDados(){

        Perfil perfil = VariaveisEstaticas.getUsuario().getPerfil();

        txtNome.setText(perfil.getNome());
        txtSobrenome.setText(perfil.getSobrenome());
        txtDataNascimento.setText(FerramentasBasicas.converterDataParaString(perfil.getDataNascimento(), FORMATO_DATA_BR));
        txtCpf.setText(perfil.getCpf());
        txtCelular.setText(perfil.getCelular());
        txtSexo.setText(perfil.getSexo().equals(SIGLA_SEXO_MASCULINO) ? SEXO_MASCULINO : SEXO_FEMININO);

        if(VariaveisEstaticas.getImagemPerfil() == null){
            ivPerfil.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manuserbranco));
        }else{
            ivPerfil.setImageBitmap(VariaveisEstaticas.getImagemPerfil());
        }
    }

    private void acoes(){

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().mudaTela(NOME_TELA_ALTERAR_DADOS_PERFIL);
            }
        });

        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().mudaTela(NOME_TELA_ENDERECO);
            }
        });

        ivPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, VariaveisEstaticas.getPickImage());
            }
        });

    }

    @Override
    public void getImagem(Bitmap imagem) {
        ivPerfil.setImageBitmap(imagem);
    }

    @Override
    public void setImagem(Bitmap imagem) {
        if (imagem != null) {
            imagemSelecionada = imagem;

            Usuario usuario = VariaveisEstaticas.getUsuario();
            String nome = usuario.getPerfil().getNome()
                    + usuario.getPerfil().getSobrenome() +
                    usuario.getId();
            UploadAwsS3 uploadAwsS3 = new UploadAwsS3(getContext(),
                    FerramentasBasicas.bitmapParaFile(imagem,getContext(), nome),
                    nome,
                    imagemInterface);
            uploadAwsS3.execute();
            /*PostImagemAsyncTask postImagemAsyncTask = new PostImagemAsyncTask(getContext(),
                                                                                imagem,
                                                                    this);
            postImagemAsyncTask.execute(FerramentasBasicas.getURL() + URL_POST_IMAGEM);*/
        }else{
            informacaoDialog.gerarDialog("Não foi possível selecionar uma imagem!");
        }
    }

    @Override
    public void retornoPostImagem(boolean cadastrou, String urlImagem) {
        if(cadastrou){
            VariaveisEstaticas.getUsuario().getPerfil().setUrlImg(urlImagem);

            PostAlterarClienteAsyncTask postAlterarClienteAsyncTask = new PostAlterarClienteAsyncTask(getContext(),
                    MontarJson.montarJsonPerfil(VariaveisEstaticas.getUsuario().getPerfil()),
                    putAlterarClienteInterface);

            postAlterarClienteAsyncTask.execute(FerramentasBasicas.getURLAPI() +
                    (VariaveisEstaticas.getUsuario().getPerfil().getTipoPerfil().getDescricao().equals(TIPO_PERFIL_PROFISSIONAL) ?
                            URL_ALTERAR_PROFISSIONAL : URL_ALTERAR_CLIENTE));

            ivPerfil.setImageBitmap(imagemSelecionada);
        }else{
            informacaoDialog.gerarDialog(urlImagem);
        }
    }

    @Override
    public void retornoAlteracao(boolean alterou) {
        if(alterou){
            informacaoDialog.gerarDialog("Imagem atualizada com sucesso!");
            guiaProDao.getDaoSession().getPerfilDao().insertOrReplace(VariaveisEstaticas.getUsuario().getPerfil());
        }else{
            informacaoDialog.gerarDialog("Não foi possível salvar a imagem!");
        }
    }
}
