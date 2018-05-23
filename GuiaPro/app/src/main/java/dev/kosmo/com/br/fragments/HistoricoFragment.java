package dev.kosmo.com.br.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.adapter.HistoricoAdapter;
import dev.kosmo.com.br.dao.DataBaseHelper;
import dev.kosmo.com.br.dao.HistoricoManager;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Historico;
import dev.kosmo.com.br.utils.ConversaoTexto;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 26/03/2018.
 */

public class HistoricoFragment extends Fragment {

    private List<Historico> lista;
    private DataBaseHelper dataBaseHelper;
    private LinearLayout listaHistorico;
    private ConversaoTexto conversaoTexto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        listaHistorico = (LinearLayout) view.findViewById(R.id.listaHistorico);

        dataBaseHelper = new DataBaseHelper(getContext());

        conversaoTexto = new ConversaoTexto();

        carregaHistorico();

        return view;

    }

    private void carregaHistorico(){

        HistoricoManager historicoManager = new HistoricoManager(dataBaseHelper.getWritableDatabase());
        lista = historicoManager.getAllHistorico();

        //HistoricoAdapter historicoAdapter = new HistoricoAdapter(getContext(), R.layout.adapter_historico_cliente,lista);
        
        int tamanho = lista.size();
        for(Historico aux : lista){


            LinearLayout vi = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.adapter_historico_cliente,null);

            ImageView ivHistorico = (ImageView) vi.findViewById(R.id.ivHistorico);
            TextView tvHistorico = (TextView) vi.findViewById(R.id.tvHistorico);

            ivHistorico.setImageBitmap(aux.getProfissionalObj().getImg());
            tvHistorico.setText(Html.fromHtml(conversaoTexto.getTextoNomeLaranja(aux.getDescricao(), aux.getProfissionalObj().getNome())), TextView.BufferType.SPANNABLE);
            //tvHistorico.setText(aux.getDescricao());

            listaHistorico.addView(vi);
            if(tamanho != 1){
                LinearLayout linearLayout = new LinearLayout(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(60,0,60,0);
                linearLayout.setBackgroundColor(Color.parseColor("#e9a11c"));
                linearLayout.setLayoutParams(params);
                listaHistorico.addView(linearLayout);
            }
            tamanho--;
        }
    }
}
