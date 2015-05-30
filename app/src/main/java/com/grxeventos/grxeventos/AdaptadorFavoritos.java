package com.grxeventos.grxeventos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdaptadorFavoritos extends ArrayAdapter<Evento> {

    private ArrayList<Evento> lista;
    private Context contexto;
    private int recurso;
    private static LayoutInflater i;

    public AdaptadorFavoritos(Context context, int resource, ArrayList<Evento> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView tv1, tv2, tv3;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);

            vh = new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tvTexto1);
            vh.tv2 = (TextView)convertView.findViewById(R.id.tvTexto2);
            vh.tv3 = (TextView)convertView.findViewById(R.id.tvTexto3);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        vh.position = position;
        vh.tv1.setTag(position);
        //Log.v("LOG",vh.tv1.getTag().toString());
        vh.tv1.setText(lista.get(position).getNombre());
        vh.tv2.setText(lista.get(position).getFecha());
        vh.tv3.setText(lista.get(position).getPrecio()+" €");


        return convertView;
    }

}
