package dev.kosmo.com.br.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.adapter.HistoricoAdapter;
import dev.kosmo.com.br.dao.DataBaseHelper;
import dev.kosmo.com.br.dao.HistoricoManager;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Historico;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 26/03/2018.
 */

public class HistoricoFragment extends Fragment {

    private List<Historico> lista;
    private DataBaseHelper dataBaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        dataBaseHelper = new DataBaseHelper(getContext());

        carregaHistorico();

        return view;

    }

    private void carregaHistorico(){

        HistoricoManager historicoManager = new HistoricoManager(dataBaseHelper.getWritableDatabase());
        lista = historicoManager.getAllHistorico();

        HistoricoAdapter historicoAdapter = new HistoricoAdapter(getContext(), R.layout.adapter_historico_cliente,lista);
        

    }
}
