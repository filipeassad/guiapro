package dev.kosmo.com.br.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.interfaces.NotificacaoPostInterface;
import dev.kosmo.com.br.models.Especialidades;
import dev.kosmo.com.br.utils.VariaveisEstaticas;

/**
 * Created by Filipe on 11/03/2018.
 */

public class DetalheProfFragment extends Fragment implements OnMapReadyCallback, NotificacaoPostInterface{

    private TextView tvNomeAtende;
    private ImageView ivImagem;
    private MapView map;
    private GoogleMap googleMap;
    private LinearLayout llEspecialidades;
    private LinearLayout llUrgencia;
    private LinearLayout llQualificacoes;
    private LinearLayout btnLigar;
    private LinearLayout btnWhats;

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
        btnLigar = (LinearLayout) view.findViewById(R.id.btnLigar);
        btnWhats = (LinearLayout) view.findViewById(R.id.btnWhats);
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

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        carregaEspecialidades();
        acoes();
        return view;
    }

    private void acoes(){
        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:067991611563"));
                VariaveisEstaticas.getFragmentInterface().mudaActivity(callIntent);
            }
        });

        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getContext().getPackageManager();
                try {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone="+ "+55067991611563" +"&text=" + URLEncoder.encode("", "UTF-8");
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));

                    if (i.resolveActivity(pm) != null) {
                        getContext().startActivity(i);
                    }

                    /*Uri uri = Uri.parse("smsto:067991611563");
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, ""));*/

                } catch (PackageManager.NameNotFoundException e) {

                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
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

//        lista.add(new Especialidades("Encanador", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));
//        lista.add(new Especialidades("Pedreiro", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));
//        lista.add(new Especialidades("Pintor", BitmapFactory.decodeResource(this.getResources(), R.drawable.trophybranco), "descricao"));

        for(Especialidades aux :lista){

            LinearLayout especialidade = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.modelo_especialidades,null);
            ImageView img = (ImageView) especialidade.findViewById(R.id.ivItem);
            TextView texto = (TextView) especialidade.findViewById(R.id.tvLabelItem);
            img.setImageBitmap(aux.getImagem());
            texto.setText(aux.getNome());

            llEspecialidades.addView(especialidade);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    VariaveisEstaticas.getFragmentInterface().voltar();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }

    @Override
    public void retornoPost(HashMap<String, String> resultado) {
        Toast.makeText(getContext(),resultado.get("name"), Toast.LENGTH_SHORT).show();
    }
}
