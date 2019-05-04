package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Perfil;
import dev.kosmo.com.br.utils.FerramentasBasicas;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 09/03/2018.
 */

public class PerfilFragment extends Fragment {

    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtDataNascimento;
    private TextView txtCpf;
    private TextView txtCelular;
    private TextView txtSexo;

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

    }
}
