package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.kosmo.com.br.guiapro.R;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class CategoriasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        return view;
    }
}
