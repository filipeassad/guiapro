package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Endereco;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class EnderecoFragment extends Fragment {

    private TextView txtCEP;
    private TextView txtNumero;
    private TextView txtLogradouro;
    private TextView txtComplemento;
    private TextView txtBairro;
    private TextView txtCidade;
    private TextView txtUf;
    private TextView txtPais;
    private Button btnAlterar;

    private final String NOME_TELA_ALTERAR_ENDERECO = "EnderecoEditar";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endereco, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(false);

        txtCEP = (TextView) view.findViewById(R.id.txtCEP);
        txtNumero = (TextView) view.findViewById(R.id.txtNumero);
        txtLogradouro = (TextView) view.findViewById(R.id.txtLogradouro);
        txtComplemento = (TextView) view.findViewById(R.id.txtComplemento);
        txtBairro = (TextView) view.findViewById(R.id.txtBairro);
        txtCidade = (TextView) view.findViewById(R.id.txtCidade);
        txtUf = (TextView) view.findViewById(R.id.txtUf);
        txtPais = (TextView) view.findViewById(R.id.txtPais);

        btnAlterar = (Button) view.findViewById(R.id.btnAlterar);

        acoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarEndereco();
    }

    public void carregarEndereco(){

        Endereco endereco = VariaveisEstaticas.getUsuario().getPerfil().getEndereco();

        txtCEP.setText(endereco.getCep());
        txtNumero.setText(endereco.getNumero());
        txtLogradouro.setText(endereco.getLogradouro());
        txtComplemento.setText(endereco.getComplemento());
        txtBairro.setText(endereco.getBairro());
        txtCidade.setText(endereco.getCidade());
        txtUf.setText(endereco.getUf());
        txtPais.setText(endereco.getPais());

    }

    public void acoes(){
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().mudaTela(NOME_TELA_ALTERAR_ENDERECO);
            }
        });
    }
}
