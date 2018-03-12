package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.ItemMenuNav;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class CategoriasFragment extends Fragment {

    private LinearLayout llCategoria;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        llCategoria = (LinearLayout) view.findViewById(R.id.llCategoria);

        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        carregaCategorias();

        return view;

    }

    private void carregaCategorias(){

        List<ItemMenuNav> lista = new ArrayList<>();

        lista.add(new ItemMenuNav("Encanador", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.encanador)));
        lista.add(new ItemMenuNav("Marceneiro", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.marceneiro)));
        lista.add(new ItemMenuNav("Pedreiro", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pedreiro)));
        lista.add(new ItemMenuNav("Pintor", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pintor)));
        lista.add(new ItemMenuNav("Eletricista", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.eletricista)));
        lista.add(new ItemMenuNav("Calhas", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.calhas)));
        lista.add(new ItemMenuNav("Contrutores", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.construtores)));
        lista.add(new ItemMenuNav("Acabamento", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.acabamento)));
        lista.add(new ItemMenuNav("Serralheiros", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.serralheiros)));
        lista.add(new ItemMenuNav("Munk", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.munk)));
        lista.add(new ItemMenuNav("Jardineiros", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jardineiros)));
        lista.add(new ItemMenuNav("Gesso", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gesso)));
        lista.add(new ItemMenuNav("Decoradores", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.decoradores)));
        lista.add(new ItemMenuNav("Carpinteiros", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.carpinteiros)));
        lista.add(new ItemMenuNav("Banheiras", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banheiras)));
        lista.add(new ItemMenuNav("Reparo Gerais", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.reparogerais)));
        lista.add(new ItemMenuNav("Limpaze", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.limpeza)));
        lista.add(new ItemMenuNav("Engenheiros", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.engenheiros)));
        lista.add(new ItemMenuNav("Vidraceiros", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.vidraceiros)));
        lista.add(new ItemMenuNav("Piscina", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.piscina)));
        lista.add(new ItemMenuNav("Arquitetos", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arquitetos)));
        lista.add(new ItemMenuNav("Papel de Parede", BitmapFactory.decodeResource(getContext().getResources(), R.drawable.papeldeparede)));

        int nLinear = lista.size() / 2;
        int index = 0;

        for(int i=0; i<nLinear; i++){

            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setGravity(Gravity.CENTER);
            params.setMargins(0,20,0,20);
            linearLayout.setLayoutParams(params);

            int tam = 0;

            while(tam < 2){
                tam++;
                if(index == lista.size()){
                    tam = 2;
                }else{
                    LinearLayout cat = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.modelo_categoria,null);
                    ImageView img = (ImageView) cat.findViewById(R.id.ivImagem);
                    TextView texto = (TextView) cat.findViewById(R.id.tvRotulo);

                    img.setImageBitmap(lista.get(index).getImg());
                    texto.setText(lista.get(index).getRotulo());

                    cat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VariaveisEstaticas.getFragmentInterface().mudaTela("ListagemProfissionais");
                        }
                    });

                    linearLayout.addView(cat);
                }
                index++;
            }

            llCategoria.addView(linearLayout);

        }

    }

}
