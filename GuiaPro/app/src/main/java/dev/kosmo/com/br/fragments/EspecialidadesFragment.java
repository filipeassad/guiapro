package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EspecialidadesFragment extends Fragment {

    private EditText edtDescricao;
    private Button btnVoltar;
    private Button btnSalvar;
    private LinearLayout llListaEspecialidades;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_especialidades, container, false);

        edtDescricao = (EditText) view.findViewById(R.id.edtDescricao);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        llListaEspecialidades = (LinearLayout) view.findViewById(R.id.llListaEspecialidades);

        acoesBotoes();

        return view;
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