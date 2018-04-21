package dev.kosmo.com.br.fragments;

import android.graphics.Bitmap;
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
import dev.kosmo.com.br.interfaces.EspecialidadeInterface;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.models.ItemMenuNav;
import dev.kosmo.com.br.task.GetEspecialidadeAsyncTask;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by 0118431 on 08/03/2018.
 */

public class CategoriasFragment extends Fragment implements EspecialidadeInterface{

    private LinearLayout llCategoria;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        llCategoria = (LinearLayout) view.findViewById(R.id.llCategoria);

        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        //carregaCategorias();

        GetEspecialidadeAsyncTask getEspecialidadeAsyncTask = new GetEspecialidadeAsyncTask(getContext(),this);
        getEspecialidadeAsyncTask.execute("http://guia-pro.herokuapp.com/api/especialidades/");

        return view;

    }

    private void carregaCategorias(List<Especialidades> especialidades){


        int nLinear = especialidades.size() / 2;
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
                if(index == especialidades.size()){
                    tam = 2;
                }else{
                    LinearLayout cat = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.modelo_categoria,null);
                    ImageView img = (ImageView) cat.findViewById(R.id.ivImagem);
                    TextView texto = (TextView) cat.findViewById(R.id.tvRotulo);

                    img.setImageBitmap(especialidades.get(index).getImagem());
                    texto.setText(especialidades.get(index).getNome());

                    cat.setTag(especialidades.get(index));

                    cat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VariaveisEstaticas.setEspecialidades((Especialidades) v.getTag());
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

    @Override
    public void getEspecialidades(List<Especialidades> especialidades) {

        for(Especialidades aux :especialidades){
            aux.setImagem(getImagem(aux.getNome()));
        }
        carregaCategorias(especialidades);
    }

    private Bitmap getImagem(String nome){

        if(nome.equals("Encanador")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.encanador);
        }else if(nome.equals("Marceneiro")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.marceneiro);
        }else if(nome.equals("Pedreiro")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pedreiro);
        }else if(nome.equals("Pintor")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pintor);
        }else if(nome.equals("Eletricista")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.eletricista);
        }else if(nome.equals("Calhas")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.calhas);
        }else if(nome.equals("Construtores")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.construtores);
        }else if(nome.equals("Acabamento")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.acabamento);
        }else if(nome.equals("Serralheiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.serralheiros);
        }else if(nome.equals("Munk")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.munk);
        }else if(nome.equals("Jardineiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jardineiros);
        }else if(nome.equals("Gesseiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gesso);
        }else if(nome.equals("Decoradores")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.decoradores);
        }else if(nome.equals("Carpinteiro")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.carpinteiros);
        }else if(nome.equals("Banheiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banheiras);
        }else if(nome.equals("Limpeza")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.limpeza);
        }else if(nome.equals("Engenheiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.engenheiros);
        }else if(nome.equals("Reparos Gerais")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.reparogerais);
        }else if(nome.equals("Banheiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banheiras);
        }else if(nome.equals("Vidraceiros")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.vidraceiros);
        }else if(nome.equals("Piscina")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.piscina);
        }else if(nome.equals("Arquitetos")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arquitetos);
        }else if(nome.equals("Papel de Parede")){
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.papeldeparede);
        }else{
            return null;
        }

    }
}
