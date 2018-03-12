package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.adapter.ListProfAdapter;
import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Profissional;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class ListagemProfissionaisFragment extends Fragment {

    private ListView lvProfissionais;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listagem_profissionais, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        lvProfissionais = (ListView) view.findViewById(R.id.lvProfissionais);

        List<Profissional> lista = new ArrayList<>();
        lista.add(new Profissional("Kratos", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.kratos)));
        lista.add(new Profissional("Gandalf, O Cinzento", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.gandalf)));
        lista.add(new Profissional("Chimbinha, Guitar Master", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.chimbinha)));
        lista.add(new Profissional("Darth Vader", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.darthvader)));
        lista.add(new Profissional("Finn", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.finn)));
        lista.add(new Profissional("Agent Smith", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.agentsmith)));
        lista.add(new Profissional("Michelangelo", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.michelangelo)));
        lista.add(new Profissional("Roy, Like tears in rain", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.royrain)));

        ListProfAdapter listProfAdapter = new ListProfAdapter(getContext(),R.layout.adapter_list_prof,lista);
        lvProfissionais.setAdapter(listProfAdapter);

        lvProfissionais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VariaveisEstaticas.setProfissional((Profissional) parent.getItemAtPosition(position));
                VariaveisEstaticas.getFragmentInterface().mudaTela("DetalheProfissional");
            }
        });

        return view;
    }
}
