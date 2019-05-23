package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Usuario;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

public class EspecialidadesFragment extends Fragment {

    private EditText edtDescricao;
    private Spinner spCategorias;
    private Button btnVoltar;
    private Button btnSalvar;
    private LinearLayout llListaEspecialidades;

    private Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_especialidades, container, false);

        edtDescricao = (EditText) view.findViewById(R.id.edtDescricao);
        spCategorias = (Spinner) view.findViewById(R.id.spCategorias);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        llListaEspecialidades = (LinearLayout) view.findViewById(R.id.llListaEspecialidades);

        usuario = VariaveisEstaticas.getUsuario();
        carregarSpinnerCategorias();
        acoesBotoes();

        return view;
    }

    private void carregarSpinnerCategorias(){
        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,usuario.getPerfil().getCategorias());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategorias.setAdapter(adapter);
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
                
            }
        });
    }

    private void carregarEspecialidadesPerfil(){
        
    }

}