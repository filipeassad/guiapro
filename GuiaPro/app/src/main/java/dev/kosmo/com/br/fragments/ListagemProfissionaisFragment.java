package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    private LinearLayout abaQualificacoes;
    private LinearLayout abaProximidade;
    private LinearLayout abaUrgencia;
    private ImageView ivQualificacao;
    private TextView tvQualificacao;
    private ImageView ivProximidade;
    private TextView tvProximidade;
    private TextView tvUrgencia1;
    private TextView tvUrgencia2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listagem_profissionais, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        lvProfissionais = (ListView) view.findViewById(R.id.lvProfissionais);
        abaQualificacoes = (LinearLayout) view.findViewById(R.id.abaQualificacoes);
        abaProximidade = (LinearLayout) view.findViewById(R.id.abaProximidade);
        abaUrgencia = (LinearLayout) view.findViewById(R.id.abaUrgencia);
        ivQualificacao = (ImageView) view.findViewById(R.id.ivQualificacao);
        tvQualificacao = (TextView) view.findViewById(R.id.tvQualificacao);
        ivProximidade = (ImageView) view.findViewById(R.id.ivProximidade);
        tvProximidade = (TextView) view.findViewById(R.id.tvProximidade);
        tvUrgencia1 = (TextView) view.findViewById(R.id.tvUrgencia1);
        tvUrgencia2 = (TextView) view.findViewById(R.id.tvUrgencia2);

        abaQualificacoes.setTag("Qualificacoes");
        abaProximidade.setTag("Proximidade");
        abaUrgencia.setTag("Urgencia");

        abaUrgencia.setOnClickListener(abaOnCLickListener);
        abaQualificacoes.setOnClickListener(abaOnCLickListener);
        abaProximidade.setOnClickListener(abaOnCLickListener);

        List<Profissional> lista = new ArrayList<>();
       /* lista.add(new Profissional("Kratos", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.kratos)));
        lista.add(new Profissional("Gandalf, O Cinzento", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.gandalf)));
        lista.add(new Profissional("Chimbinha, Guitar Master", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.chimbinha)));
        lista.add(new Profissional("Darth Vader", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.darthvader)));
        lista.add(new Profissional("Finn", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.finn)));
        lista.add(new Profissional("Agent Smith", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.agentsmith)));
        lista.add(new Profissional("Michelangelo", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.michelangelo)));
        lista.add(new Profissional("Roy, Like tears in rain", "","", BitmapFactory.decodeResource(this.getResources(), R.drawable.royrain)));*/

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

    private View.OnClickListener abaOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setBackGroundLinearLayout();
            ((LinearLayout)view).setBackgroundResource(R.drawable.menutopocinza);

            if(((String)view.getTag()).equals("Qualificacoes")){
                ivQualificacao.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.trophycinza));
                tvQualificacao.setTextColor(Color.parseColor("#bfbfbf"));

            }else if(((String)view.getTag()).equals("Proximidade")){
                ivProximidade.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.locationcinza));
                tvProximidade.setTextColor(Color.parseColor("#bfbfbf"));

            }else if(((String)view.getTag()).equals("Urgencia")){
                tvUrgencia1.setTextColor(Color.parseColor("#bfbfbf"));
                tvUrgencia2.setTextColor(Color.parseColor("#bfbfbf"));
            }
        }
    };


    private void setBackGroundLinearLayout(){

        abaQualificacoes.setBackgroundResource(R.drawable.menutopolaranja);
        ivQualificacao.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.trophylaranja));
        tvQualificacao.setTextColor(Color.parseColor("#fddeb3"));

        abaProximidade.setBackgroundResource(R.drawable.menutopolaranja);
        ivProximidade.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.locationlaranja));
        tvProximidade.setTextColor(Color.parseColor("#fddeb3"));

        abaUrgencia.setBackgroundResource(R.drawable.menutopolaranja);
        tvUrgencia1.setTextColor(Color.parseColor("#fddeb3"));
        tvUrgencia2.setTextColor(Color.parseColor("#fddeb3"));
    }
}
