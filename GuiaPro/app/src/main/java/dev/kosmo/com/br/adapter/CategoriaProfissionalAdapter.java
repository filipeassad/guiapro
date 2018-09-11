package dev.kosmo.com.br.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import dev.kosmo.com.br.guiapro.R;
import dev.kosmo.com.br.models.Categoria;

public class CategoriaProfissionalAdapter extends ArrayAdapter<Categoria> {

    private Context context;
    private int resource;

    public CategoriaProfissionalAdapter(@NonNull Context context, int resource, @NonNull List<Categoria> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Categoria categoria = getItem(position);
        TextView tvCategoria = (TextView) convertView.findViewById(R.id.tvCategoria);
        tvCategoria.setText(categoria.getDescricao());

        return convertView;
    }
}
