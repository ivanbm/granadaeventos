package com.grxeventos.grxeventos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;
import android.widget.Toast;

public class DetalleEvento extends ActionBarActivity {
	private Evento evento;
	private TextView t1;
	private TextView t2;
    private TextView t2m;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private ImageView i;
	private URL url;
	private Bitmap bmp;
    private LinearLayout ll;
    private String dia, mes, anio;
    private ArrayList<Evento> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_evento);
		t1 = (TextView) findViewById(R.id.tv2_nombre_evento);
		t2 = (TextView) findViewById(R.id.tv2_fecha_evento);
        t2m = (TextView) findViewById(R.id.tv2_mes_evento);
		t3 = (TextView) findViewById(R.id.tv2_descripcion_detalle_evento);
		t4 = (TextView) findViewById(R.id.tv2_precio_evento);
        ll = (LinearLayout)findViewById(R.id.titEvt);
		
		i = (ImageView) findViewById(R.id.img2_imagen_evento);
		
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("BUNDLE");
		if(intent != null){
			evento = (Evento) b.getParcelable("EVENTO");
        }

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = evento.getFecha().split(" ")[0];
        Date fecha = null;
        try {

            fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String f = fecha.toString();
        dia = f.split(" ")[2];
        mes = f.split(" ")[1];
        anio = f.split(" ")[5];

        pintarTitulo(evento.getIdcategoria());
		
		t1.setText(evento.getNombre());
		t2.setText(dia);
        t2m.setText(mes);
		t3.setText(evento.getDescripcion());
		t4.setText(evento.getPrecio()+" €");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detalle_evento, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.atras) {
            finish();
		}else if (id == R.id.compartir) {
            compartir();
        }else if (id == R.id.favorito) {
            anadirFavorito();
        }
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detalle_evento,
					container, false);
			return rootView;
		}
	}

    public void pintarTitulo(int id){
        if(id==1){
            ll.setBackgroundColor(getResources().getColor(R.color.diseno_artes_graficas));
        }else if(id==2){
            ll.setBackgroundColor(getResources().getColor(R.color.biosanitario));
        }else if(id==3){
            ll.setBackgroundColor(getResources().getColor(R.color.juridico));
        }else if(id==4){
            ll.setBackgroundColor(getResources().getColor(R.color.educacion_sociedad));
        }else if(id==5){
            ll.setBackgroundColor(getResources().getColor(R.color.energia_medio_ambiente));
        }else if(id==6){
            ll.setBackgroundColor(getResources().getColor(R.color.idiomas));
        }else if(id==7){
        ll.setBackgroundColor(getResources().getColor(R.color.economia));
    }
    }
	
	public void atras(View v) {
		finish();
	}
	
	public void localizacion(View v){
        Intent i = new Intent(this.getApplicationContext(), Mapa.class);
        Bundle b = new Bundle();
        b.putInt("IDLOCALIZACION", evento.getIdlocalizacion());
        i.putExtra("BUNDLE", b);
        startActivity(i);
	}

    public void compartir(){

        String cuerpo = evento.getNombre() + " el " + dia + " de " + mes + " de " + anio + "\n" + evento.getDescripcion() + " +info: www.granadaeventos.es";

        Intent comp = new Intent(android.content.Intent.ACTION_SEND);
        comp.setType("text/plain");
        comp.putExtra(android.content.Intent.EXTRA_SUBJECT, "Granada Eventos");
        comp.putExtra(android.content.Intent.EXTRA_TEXT, cuerpo);
        startActivity(Intent.createChooser(comp, "Compartir"));
    }

    public void anadirFavorito(){
        ClaseXML cxml = new ClaseXML();

        lista = cxml.leer(getApplicationContext());
        lista.add(evento);
        cxml.nuevoArchivo(getApplicationContext(),lista);

        Toast.makeText(getApplicationContext(),"Guardado en favoritos", Toast.LENGTH_SHORT).show();
    }

}
