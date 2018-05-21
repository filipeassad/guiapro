package dev.kosmo.com.br.adapter;

import android.app.Activity;
import android.content.Context;
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
import dev.kosmo.com.br.models.Historico;

public class HistoricoAdapter extends ArrayAdapter<Historico> {

    private Context myContext;
    private int myResource;

    public HistoricoAdapter(@NonNull Context context, int resource, @NonNull List<Historico> objects) {
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

        ImageView ivHistorico = (ImageView) convertView.findViewById(R.id.ivHistorico);
        TextView tvHistorico = (TextView) convertView.findViewById(R.id.tvHistorico);

        ivHistorico.setImageBitmap(getItem(position).getProfissionalObj().getImg());
        tvHistorico.setText(getItem(position).getDescricao());

        return convertView;

    }
}
