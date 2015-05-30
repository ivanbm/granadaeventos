package com.grxeventos.grxeventos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.grxeventos.grxeventos.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class Localizacion extends ActionBarActivity {
	
	  private static final String TAG_IDLOCALIZACION = "idlocalizacion";
	  private static final String TAG_CALLE = "calle";
	  private static final String TAG_PROVINCIA = "provincia";
	  private static final String TAG_NUMERO = "numero";
	  private static final String TAG_TELEFONO = "telefono";
	  private static final String TAG_LATITUD = "latitud";
	  private static final String TAG_LONGITUD = "longitud";
	 
	  private TextView tCalle;
	  private TextView tProvincia;
	  private TextView tNumero;
	  private TextView tTelefono;
      Constantes c = new Constantes();

	private CLocalizacion l;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localizacion);
		
		tCalle = (TextView) findViewById(R.id.tv_calle);
		tProvincia = (TextView) findViewById(R.id.tv_provincia);
		tTelefono = (TextView) findViewById(R.id.tv_numero);
		tTelefono = (TextView) findViewById(R.id.tv_telefono);
		
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("BUNDLE");
		if(intent != null){
			id = b.getInt("IDLOCALIZACION");
		}
		Log.v("ID DE LA LOCALIZACION", id+"");
		new Async_ActualizarListadoLocalizaciones().execute("");
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.localizacion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
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
			View rootView = inflater.inflate(R.layout.fragment_localizacion,
					container, false);
			return rootView;
		}
	}

	private class Async_ActualizarListadoLocalizaciones extends AsyncTask<String, Integer, Integer>{

		private JSONArray jSONArray;
		
		@Override
		protected void onPreExecute() {
			//ServicioComprobarRevistasNuevas.DESCARGANDO = true;			
			
		}
		
		@Override
		protected Integer doInBackground(String... arg0) {
			
			jSONArray = HttpCliente.getJSONfromURL(c.getURL()+"/php/localizaciones.php");
		
			
				
				if(jSONArray!=null){
					
					for(int i=0;i<jSONArray.length();i++){
						try {
					
							
							Log.v("EVENTO", "Evento nuevo");
							
							int idl = jSONArray.getJSONObject(i).getInt(TAG_IDLOCALIZACION);
							int lg = jSONArray.getJSONObject(i).getInt(TAG_LATITUD);
							int lt = jSONArray.getJSONObject(i).getInt(TAG_LONGITUD);
							if(idl == id){						
								l = (new CLocalizacion(id,
										jSONArray.getJSONObject(i).getString(TAG_CALLE),
										jSONArray.getJSONObject(i).getString(TAG_PROVINCIA),
										jSONArray.getJSONObject(i).getInt(TAG_NUMERO),
										jSONArray.getJSONObject(i).getInt(TAG_TELEFONO),
										Float.parseFloat(lg+""),
										Float.parseFloat(lt+"")));
										
										Log.v("LOCALIZACION", id+"");
								Log.v("LOCALIZACION", jSONArray.getJSONObject(i).getString(TAG_CALLE));
								Log.v("LOCALIZACION", jSONArray.getJSONObject(i).getString(TAG_PROVINCIA));
								Log.v("LOCALIZACION", jSONArray.getJSONObject(i).getInt(TAG_NUMERO)+"");
								Log.v("LOCALIZACION", jSONArray.getJSONObject(i).getInt(TAG_TELEFONO)+"");
									
							}
							
						} catch (JSONException e) {
							return -1;
						}
					}
					return 1;
				}
			
			
			return -1;
		}
		
		
		protected void onPostExecute(Integer result) {
			if(l != null){
				try{
			tCalle.setText(l.getCalle());
			tProvincia.setText(l.getProvincia());
			tNumero.setText(l.getNumero()+"");	
			tTelefono.setText(l.getTelefono()+"");
				}
				catch(Exception e){
					
				}
			}
		}
		
	}
	
	public void volver(View v){
		finish();
	}
	
}
