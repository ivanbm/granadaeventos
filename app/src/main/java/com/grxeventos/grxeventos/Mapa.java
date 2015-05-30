package com.grxeventos.grxeventos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

public class Mapa extends FragmentActivity {

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
    private double latitud, longitud;

    private CLocalizacion l;
    private int id;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        tCalle = (TextView) findViewById(R.id.tv_calle);
        tProvincia = (TextView) findViewById(R.id.tv_provincia);
        tTelefono = (TextView) findViewById(R.id.tv_numero);
        tTelefono = (TextView) findViewById(R.id.tv_telefono);

        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("BUNDLE");
        if(intent != null){
            id = b.getInt("IDLOCALIZACION");
        }
        Log.v("ID DE LA LOCALIZACION", id + "");
        new Async_ActualizarListadoLocalizaciones().execute("");

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }*/

        //setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded(latitud, longitud);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded(double lat, double lon) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(lat, lon);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(double lat, double lon) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("Evento"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 12.0f));

    }



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


    /*---------------------------------------*/
    /*--------  OBTENER DATOS LOC -----------*/
    /*---------------------------------------*/

    private class Async_ActualizarListadoLocalizaciones extends AsyncTask<String, Integer, Integer> {

        private JSONArray jSONArray;

        @Override
        protected void onPreExecute() {
            //ServicioComprobarRevistasNuevas.DESCARGANDO = true;

        }

        @Override
        protected Integer doInBackground(String... arg0) {

            jSONArray = HttpCliente.getJSONfromURL("http://192.168.1.101/eventos/php/localizaciones.php");



            if(jSONArray!=null){

                for(int i=0;i<jSONArray.length();i++){
                    try {


                        Log.v("EVENTO", "Evento nuevo");

                        int idl = jSONArray.getJSONObject(i).getInt(TAG_IDLOCALIZACION);
                        double lg = jSONArray.getJSONObject(i).getDouble(TAG_LATITUD);
                        int lt = jSONArray.getJSONObject(i).getInt(TAG_LONGITUD);

                        if(idl == id){
                            l = (new CLocalizacion(id,
                                    jSONArray.getJSONObject(i).getString(TAG_CALLE),
                                    jSONArray.getJSONObject(i).getString(TAG_PROVINCIA),
                                    jSONArray.getJSONObject(i).getInt(TAG_NUMERO),
                                    jSONArray.getJSONObject(i).getInt(TAG_TELEFONO),
                                    Float.parseFloat(lg+""),
                                    Float.parseFloat(lt+"")));

                            latitud = jSONArray.getJSONObject(i).getDouble(TAG_LATITUD);
                            longitud = jSONArray.getJSONObject(i).getDouble(TAG_LONGITUD);
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
                    setUpMapIfNeeded(latitud, longitud);
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
}
