package com.grxeventos.grxeventos;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.grxeventos.grxeventos.R.drawable;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
//import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Constantes c = new Constantes();
	private ViewPager pager = null;
	private AdaptadorFragmento adapter;
	private int categoriaEnCurso;
	private TextView catEnCurso;
	private ContenedorEventos event;
	private ImageView i;
	private String url = c.getURL()+"eventos/php/categorias.php";
	JSONArray eventos = null;
	
    private static final String TAG_IDEVENTO = "idevento";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_DESCRIP = "descripcion";
    private static final String TAG_FECHA = "fecha";
    private static final String TAG_PRECIO = "precio";
    private static final String TAG_IDLOCALIZACION = "idlocalizacion";
    private static final String TAG_IDUSUARIO = "idusuario";
    private static final String TAG_IDCATEGORIA = "idcategoria";
    private static final String TAG_APROBADO = "aprobado";

    //-------------------   DRAWER --------------------
    private static String TAG = MainActivity.class.getSimpleName();
    ListView listaDrawer;
    RelativeLayout contDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    ArrayList<ElementoDrawer> elemsDrawer = new ArrayList<ElementoDrawer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        llenarDrawer();


		event = new ContenedorEventos();
		i = (ImageView) findViewById(R.id.imagenEvento);
		i.setImageResource(R.drawable.cine);
	    Async_ActualizarListadoEventos l = new Async_ActualizarListadoEventos();
	    l.execute("");
		this.pager = (ViewPager) this.findViewById(R.id.pager);
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				damePagina(pager.getCurrentItem());
				cargaImagen();
			}
			
		});
		
		ArrayList<Evento>eventos = new ArrayList<Evento>();
		
        // Create an adapter with the fragments we show on the ViewPager
         
       
        
        categoriaEnCurso = pager.getCurrentItem();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
            //getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
    public void onBackPressed() {
 
        // Return to previous page when we press back button
        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.verfav) {

            Intent intent = new Intent(getBaseContext(), Favoritos.class);
            startActivity(intent);

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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void abrirEvento(View v){
		Fragmento f = (Fragmento) adapter.getItem(pager.getCurrentItem());
        Evento e = f.getCurrentEvento();
		
		Intent intent = new Intent(getBaseContext(), DetalleEvento.class);
		Bundle b = new Bundle();
		b.putSerializable("EVENTO", e);
		intent.putExtra("BUNDLE", b);
		startActivity(intent);
	}
	
	public void damePagina(int i){
		categoriaEnCurso = i;
		//catEnCurso = (TextView) findViewById(R.id.tv_main_categoria_curso);
		//catEnCurso.setText(String.valueOf(categoriaEnCurso));
	}

	public void siguiente(View v){
		Fragmento f = (Fragmento) adapter.getItem(pager.getCurrentItem());
		f.eventoSiguiente();
	}
	
	public void anterior(View v){
		Fragmento f = (Fragmento) adapter.getItem(pager.getCurrentItem());
		f.eventoAnterior();
	}
	
	private class Async_ActualizarListadoEventos extends AsyncTask<String, Integer, Integer>{
		private ProgressDialog pd;
		private JSONArray jSONArray;
		
		@Override
		protected void onPreExecute() {
			//ServicioComprobarRevistasNuevas.DESCARGANDO = true;			
			pd = new ProgressDialog(MainActivity.this);
			pd.setMessage("Actualizando catalogo de eventos...");
			pd.setCancelable(false);
			pd.setSecondaryProgress(ProgressDialog.STYLE_SPINNER);
			pd.show();
		}
		
		@Override
		protected Integer doInBackground(String... arg0) {
			
			jSONArray = HttpCliente.getJSONfromURL(c.getURL()+"eventos/php/eventos.php");
		
			
				
				if(jSONArray!=null){
					
					for(int i=0;i<jSONArray.length();i++){
						try {
							
							//Log.v("EVENTO", "Evento nuevo");
							int id = jSONArray.getJSONObject(i).getInt(TAG_IDEVENTO);
							String n = jSONArray.getJSONObject(i).getString(TAG_NOMBRE);
							String d = jSONArray.getJSONObject(i).getString(TAG_DESCRIP);
							String f = jSONArray.getJSONObject(i).getString(TAG_FECHA);
							double p = jSONArray.getJSONObject(i).getDouble(TAG_PRECIO);
							int idl = jSONArray.getJSONObject(i).getInt(TAG_IDLOCALIZACION);
							int idc = jSONArray.getJSONObject(i).getInt(TAG_IDCATEGORIA);
							int a = jSONArray.getJSONObject(i).getInt(TAG_APROBADO);
							event.add(new Evento(id, n, d, f, p, idl, idc, a));
							
						} catch (JSONException e) {
							return -1;
						}
					}
					return 1;
				}
			

			return -1;
		}
		
		
		protected void onPostExecute(Integer result) {
			adapter = new AdaptadorFragmento(
	                getSupportFragmentManager());
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.diseno_artes_graficas), 1, "Cine", drawable.cine, event, 1));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.biosanitario), 2, "Teatro", drawable.teatro, event, 1));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.juridico), 3, "Arte", drawable.exposiciones, event, 3));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.educacion_sociedad), 4, "Conciertos", drawable.conciertos, event, 4));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.energia_medio_ambiente), 5, "Restaurantes", drawable.restaurantes, event, 5));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.idiomas), 6, "Infantil", drawable.ninos, event, 6));
	        adapter.addFragment(Fragmento.newInstance(getResources()
	                .getColor(R.color.economia), 7, "Noche", drawable.noche, event, 7));
	        pager.setAdapter(adapter);
			pd.dismiss();
				
			
		}
		
	}
	
	public void cargaImagen(){
	
		switch (pager.getCurrentItem()) {
		case 0:
			i.setImageResource(R.drawable.cine);
			break;
			
		case 1:
			i.setImageResource(R.drawable.teatro);
			break;
			
		case 2:
			i.setImageResource(R.drawable.exposiciones);
			break;
	
		case 3:
			i.setImageResource(R.drawable.conciertos);
			break;
	
		case 4:
			i.setImageResource(R.drawable.restaurantes);
			break;
	
		case 5:
			i.setImageResource(R.drawable.ninos);
			break;
	

		default:
			i.setImageResource(R.drawable.noche);
			break;
		}
	}


    public void llenarDrawer(){
        elemsDrawer.add(new ElementoDrawer("Favoritos", "Mis eventos favoritos", drawable.ic_menu_star));
        elemsDrawer.add(new ElementoDrawer("Ajustes", "Edita tus preferencias", drawable.ic_menu_preferences));
        elemsDrawer.add(new ElementoDrawer("About", "Sobre nosotros", drawable.ic_menu_info_details));

        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        contDrawer = (RelativeLayout) findViewById(R.id.drawerPane);
        listaDrawer = (ListView) findViewById(R.id.navList);
        AdaptadorDrawer adapter = new AdaptadorDrawer(this, elemsDrawer);
        listaDrawer.setAdapter(adapter);

        // Drawer Item click listeners
        listaDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionElementoDrawer(position);
            }
        });
    }


    private void seleccionElementoDrawer(int position) {
        Fragment fragment = new PreferenciasFragDrawer();

        if(position == 0){
            Intent intent = new Intent(getBaseContext(), Favoritos.class);
            startActivity(intent);
        }else if(position == 1){

        }else if(position == 2){
            Intent intent = new Intent(getBaseContext(), SobreNosotros.class);
            startActivity(intent);
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        listaDrawer.setItemChecked(position, true);
        setTitle(elemsDrawer.get(position).titulo);

        // Close the drawer
        drawerLayout.closeDrawer(contDrawer);
    }


}



    
  
