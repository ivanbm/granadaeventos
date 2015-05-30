package com.grxeventos.grxeventos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ivan on 19/04/2015.
 */
public class Favoritos extends ActionBarActivity {

    private ArrayList<Evento> lista;
    private AdaptadorFavoritos ad;
    private ListView lv;


    /*-------------------------------------------*/
    /*              METODOS ON                   */
    /*-------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_favoritos);

        initComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.contextual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){

            case R.id.elimiar:
                eliminar(index);
                ad.notifyDataSetChanged();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }


    private void initComponents(){
        lista = new ArrayList<Evento>();

        lv = (ListView)findViewById(R.id.lvLista);
        ClaseXML cxml = new ClaseXML();
        lista = cxml.leer(getApplicationContext());

        mostrarFavs();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Evento e = new Evento(lista.get(position).getId(),
                        lista.get(position).getNombre(),
                        lista.get(position).getDescripcion(),
                        lista.get(position).getFecha(),
                        lista.get(position).getPrecio(),
                        lista.get(position).getIdlocalizacion(),
                        lista.get(position).getIdcategoria(),
                        lista.get(position).getAprobado()
                );

                Intent i = new Intent(Favoritos.this, DetalleEvento.class);
                Bundle b = new Bundle();
                b.putSerializable("EVENTO", e);
                i.putExtra("BUNDLE", b);
                startActivity(i);

            }
        });

    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /*-------------------------------------*/
    /*--       VISTUALIZAR DISCOS        --*/
    /*-------------------------------------*/

    public void mostrarFavs(){
        ad = new AdaptadorFavoritos(this, R.layout.detalle_favoritos, lista);
        final ListView ls = (ListView)findViewById(R.id.lvLista);
        ls.setAdapter(ad);
        registerForContextMenu(ls);
    }

    public void eliminar(int index){
        int id = lista.get(index).getId();

        ClaseXML cxml = new ClaseXML();
        cxml.eliminar(getApplicationContext(), lista, id);

        Collections.sort(lista);
        tostada(getString(R.string.msgeliminar));
        mostrarFavs();
    }

}



