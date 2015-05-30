package com.grxeventos.grxeventos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragmento extends Fragment{
	private static final String BACKGROUND_COLOR = "color";
	private static final String INDEX = "index";
	private static final String NOMBRE = "nombre";
	private static final String ICONO = "icono";
	private static final String CONTENEDOR = "contenedor";
	private static final String CATEGORIA = "categoria";
	
	private static final String TAG_IDEVENTO = "idevento";
	  private static final String TAG_NOMBRE = "nombre";
	  private static final String TAG_DESCRIP = "descripcion";
	  private static final String TAG_FECHA = "fecha";
	  private static final String TAG_PRECIO = "precio";
	  private static final String TAG_IDLOCALIZACION = "idlocalizacion";
	  private static final String TAG_IDUSUARIO = "idusuario";
	  private static final String TAG_IDCATEGORIA = "idcategoria";
	  private static final String TAG_APROBADO = "aprobado";
	
	private int color;
	private static int index;
	private static int categoria;
	private int icono; 
	private int eventoEncurso = 0;
	private ContenedorEventos contenedor;
	private String nombre;
	private ArrayList<Evento>eventosCategoria;
	private Evento evento;
	private TextView tv;
	private TextView tv2;
	private ImageView imagenEvento;
	
	public static Fragmento newInstance(int color, int index, String textoNombreCanal, int icono, ContenedorEventos contenedor, int categoria){
		Fragmento fragment = new Fragmento();
		Bundle b  = new Bundle();
		b.putInt(BACKGROUND_COLOR, color);
		b.putInt(INDEX, index);
		b.putString(NOMBRE, textoNombreCanal);
		b.putInt(ICONO, icono);
		b.putParcelable(CONTENEDOR, contenedor);
		b.putInt(CATEGORIA, categoria);
		fragment.setArguments(b);
		fragment.setRetainInstance(true);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		eventosCategoria = new ArrayList<Evento>();

		this.color=(getArguments() != null) ? getArguments().getInt(BACKGROUND_COLOR): Color.GRAY;
		this.index = (getArguments() != null) ? getArguments().getInt(INDEX): -1;
		this.nombre = (getArguments() != null) ? getArguments().getString(NOMBRE): "Canal";
		this.icono = (getArguments() != null) ? getArguments().getInt(ICONO): R.drawable.ic_launcher;
		this.contenedor = (ContenedorEventos) ((getArguments() != null) ? getArguments().getParcelable(CONTENEDOR): null);
		this.categoria = (getArguments() != null) ? getArguments().getInt(CATEGORIA): 1;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragmento, container, false);
		//tv = (TextView) rootView.findViewById(R.id.tvindex);
		tv = (TextView) rootView.findViewById(R.id.tv2_nombre_evento);
        tv2 = (TextView) rootView.findViewById(R.id.tv2_desc_evento);
		imagenEvento = (ImageView) rootView.getRootView().findViewById(R.id.imagenEvento);
		TextView tvN = (TextView) rootView.findViewById(R.id.tv_nombre_canal);
		LinearLayout l = (LinearLayout) rootView.findViewById(R.id.linea_canal);
		//ImageView iv = (ImageView) rootView.findViewById(R.id.imagen_canal);
		
		for(int i=0; i<contenedor.size();i++){
			Evento ev = contenedor.get(i);
			if(ev.getIdcategoria() == categoria){
				//Log.v("ESTE ES EL ID", ev.getIdcategoria()+"");
				eventosCategoria.add(ev);
			}
			//Log.v("DENTRO DEL FRAGMENT", ev.getIdcategoria()+"");
		}
		
		l.setBackgroundColor(this.color);
		tvN.setText(String.valueOf(this.nombre));
		//iv.setImageResource(icono);
		cargaEventos(eventosCategoria);
		return rootView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void eventoAnterior(){
		if(eventoEncurso>0){
            eventoEncurso--;
            evento = eventosCategoria.get(eventoEncurso);
            tv.setText(String.valueOf(evento.getNombre()));

            String acortado = "";
            if(evento.getDescripcion().length()>100) {
                acortado = evento.getDescripcion().substring(0, 100);
                tv2.setText(String.valueOf(acortado + "..."));
            }else{
                tv2.setText(evento.getDescripcion());
            }

		}
    }
	
	public void eventoSiguiente(){
		if(eventoEncurso<(eventosCategoria.size()-1)){
		    eventoEncurso++;
            evento = eventosCategoria.get(eventoEncurso);
		    tv.setText(String.valueOf(evento.getNombre()));

            String acortado;
            if(evento.getDescripcion().length()>100) {
                acortado = evento.getDescripcion().substring(0, 100);
                tv2.setText(String.valueOf(acortado + "..."));
            }else{
                tv2.setText(evento.getDescripcion());
            }
		}
	}
	
	public void cargaElementosInicio(){
		evento = eventosCategoria.get(eventoEncurso);
		tv.setText(String.valueOf(evento.getNombre()));

        String acortado;
        if(evento.getDescripcion().length()>100) {
            acortado = evento.getDescripcion().substring(0, 100);
            tv2.setText(String.valueOf(acortado + "..."));
        }else{
            tv2.setText(evento.getDescripcion());
        }
		
		//imagenEvento.setImageResource(R.drawable.ic_launcher);
	}
	
	
	public void cargaEventos(ArrayList<Evento> ev){
		
		for(Evento e : ev)
			Log.v("CARGANDO EVENTOS", e.getNombre());
		
		eventosCategoria = ev;
		if(eventosCategoria.size()>0)
			cargaElementosInicio();
		
	}
	
	public Evento getCurrentEvento(){
		return eventosCategoria.get(eventoEncurso);
	}
		
	}
	
	
		

