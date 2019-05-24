package dev.kosmo.com.br.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Perfil;

/**
 * Created by Filipe on 11/03/2018.
 */

public class ProfissionalAdapter extends ArrayAdapter<Perfil> {

    private Context myContext;
    private int myResource;

    public ProfissionalAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Perfil> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        ImageView ivItem = (ImageView) convertView.findViewById(R.id.ivItem);
        TextView tvNome = (TextView) convertView.findViewById(R.id.tvNome);

        Perfil perfil = getItem(position);

        ivItem.setImageBitmap(perfil.getImagemBaixada());

        tvNome.setText(perfil.getNome());

        return convertView;
    }
}
