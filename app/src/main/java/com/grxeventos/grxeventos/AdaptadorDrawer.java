package com.grxeventos.grxeventos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ivan on 31/05/2015.
 */
public class AdaptadorDrawer extends BaseAdapter {

    Context contexto;
    ArrayList<ElementoDrawer> elementos;

    public AdaptadorDrawer(Context contexto, ArrayList<ElementoDrawer> elementosNav) {
        this.contexto = contexto;
        this.elementos = elementosNav;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Object getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.elemento_drawer, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.tituloDrawer);
        TextView subtitleView = (TextView) view.findViewById(R.id.subtituloDrawer);
        ImageView iconView = (ImageView) view.findViewById(R.id.iconoDrawer);

        titleView.setText( elementos.get(position).titulo );
        subtitleView.setText( elementos.get(position).subtitulo );
        iconView.setImageResource(elementos.get(position).icono);

        return view;
    }

}
