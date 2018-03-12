package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class NotificacaoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacao, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(false);
        return view;
    }
}
