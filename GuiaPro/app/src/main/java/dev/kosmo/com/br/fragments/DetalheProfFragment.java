package dev.kosmo.com.br.fragments;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class DetalheProfFragment extends Fragment implements OnMapReadyCallback{

    private TextView tvNomeAtende;
    private ImageView ivImagem;
    private MapView map;
    private GoogleMap googleMap;
    private LinearLayout llEspecialidades;
    private LinearLayout llUrgencia;
    private LinearLayout llQualificacoes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_prof, container, false);
        VariaveisEstaticas.getFragmentInterface().visibilidadeMenu(true);

        tvNomeAtende = (TextView) view.findViewById(R.id.tvNomeAtende);
        ivImagem = (ImageView) view.findViewById(R.id.ivImagem);
        llEspecialidades = (LinearLayout) view.findViewById(R.id.llEspecialidades);
        llUrgencia = (LinearLayout) view.findViewById(R.id.llUrgencia);
        llQualificacoes = (LinearLayout) view.findViewById(R.id.llQualificacoes);
        map = (MapView) view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivImagem.setImageBitmap(VariaveisEstaticas.getProfissional().getImg());
        tvNomeAtende.setText(VariaveisEstaticas.getProfissional().getNome() + " atende a partir desta localidade");
        if(VariaveisEstaticas.getProfissional().getNome().contains("Kratos")){
            llUrgencia.setVisibility(View.GONE);
        }

        carregaEspecialidades();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng latLng = new LatLng(-20.460918,-54.611032);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).build();

        this.googleMap.clear();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        this.googleMap.addCircle(new CircleOptions()
                .center(new LatLng(-20.460918, -54.611032))
                .radius(100)
                .strokeColor(Color.parseColor("#f15109"))
                .fillColor(Color.parseColor("#32f15109")));
    }

    private void carregaEspecialidades(){

        List<Especialidades> lista = new ArrayList<>();

        lista.add(new Especialidades("Encanador", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));
        lista.add(new Especialidades("Pedreiro", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));
        lista.add(new Especialidades("Pintor", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));

        for(Especialidades aux :lista){

            LinearLayout especialidade = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.modelo_especialidades,null);
            ImageView img = (ImageView) especialidade.findViewById(R.id.ivItem);
            TextView texto = (TextView) especialidade.findViewById(R.id.tvLabelItem);
            img.setImageBitmap(aux.getImagem());
            texto.setText(aux.getNome());

            llEspecialidades.addView(especialidade);

        }

    }
}
