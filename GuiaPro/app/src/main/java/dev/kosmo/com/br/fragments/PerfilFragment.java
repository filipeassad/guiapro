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

import org.w3c.dom.Text;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.ImagemInterface;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.task.gets.GetImagemAsyncTask;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 09/03/2018.
 */

public class PerfilFragment extends Fragment implements ImagemInterface {

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

        acoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarDados();
    }

    private void carregarDados(){

        Perfil perfil = VariaveisEstaticas.getUsuario().getPerfil();

        txtNome.setText(perfil.getNome());
        txtSobrenome.setText(perfil.getSobrenome());
        txtDataNascimento.setText(FerramentasBasicas.converterDataParaString(perfil.getDataNascimento(), FORMATO_DATA_BR));
        txtCpf.setText(perfil.getCpf());
        txtCelular.setText(perfil.getCelular());
        txtSexo.setText(perfil.getSexo().equals(SIGLA_SEXO_MASCULINO) ? SEXO_MASCULINO : SEXO_FEMININO);

        if(perfil.getUrlImg() == null
                || perfil.getUrlImg().trim().equals("")){
            ivPerfil.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manuserbranco));
        }else{
            //ivPerfil.setImageBitmap(FerramentasBasicas.getBitmapFromURL(perfil.getUrlImg()));
            GetImagemAsyncTask getImagemAsyncTask = new GetImagemAsyncTask(getContext(), this);
            getImagemAsyncTask.execute(perfil.getUrlImg());
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
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, VariaveisEstaticas.getPickImage());
            }
        });

    }

    @Override
    public void getImagem(Bitmap imagem) {
        ivPerfil.setImageBitmap(imagem);
    }
}
