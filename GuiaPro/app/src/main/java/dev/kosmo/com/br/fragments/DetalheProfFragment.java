package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class DetalheProfFragment extends Fragment {

    private TextView tvNomeAtende;
    private ImageView ivImagem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_prof, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        tvNomeAtende = (TextView) view.findViewById(R.id.tvNomeAtende);
        ivImagem = (ImageView) view.findViewById(R.id.ivImagem);

        ivImagem.setImageBitmap(VariaveisEstaticas.getProfissional().getImg());
        tvNomeAtende.setText(VariaveisEstaticas.getProfissional().getNome() + " atende a partir desta localidade");

        return view;
    }
}
