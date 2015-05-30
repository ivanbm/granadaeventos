package com.grxeventos.grxeventos;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 16/11/2014.
 */
public class ClaseXML {

    private String nombre;
    private String descripcion;
    private String fecha;
    private double precio;
    private int idlocalizacion;
    private int idcategoria;
    private int aprobado;

    public static void nuevoArchivo(Context contexto, ArrayList<Evento> lista){
        try{
            FileOutputStream fosxml = new FileOutputStream(new File(contexto.getFilesDir(),"favoritos.xml"));

            XmlSerializer docxml= Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            docxml.startTag(null, "favoritos");
            for(int i = 0; i<lista.size();i++){
                //System.out.println("ESCRIBIENDO "+lista.get(i).getAlbum());
                docxml.startTag(null, "evento");
                docxml.attribute(null, "id", ""+lista.get(i).getId());
                docxml.attribute(null, "nombre", lista.get(i).getNombre());
                docxml.attribute(null, "descripcion", lista.get(i).getDescripcion());
                docxml.attribute(null, "fecha", lista.get(i).getFecha());
                docxml.attribute(null, "precio", ""+lista.get(i).getPrecio());
                docxml.attribute(null, "idlocalizacion", ""+ lista.get(i).getIdlocalizacion());
                docxml.attribute(null, "idcategoria", ""+lista.get(i).getIdcategoria());
                docxml.attribute(null, "aprobado", ""+ lista.get(i).getAprobado());
                docxml.endTag(null, "evento");
            }
            docxml.endTag(null, "favoritos");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        }catch(Exception e){
            System.out.println("ERROR AL ESCRIBIR");
        }
    }

    public static void eliminar(Context con, ArrayList<Evento> lista , int id){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getId()==id){
                lista.remove(i);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }

    }

    /*public static void eliminar(Context con, ArrayList<Evento> lista , Evento e){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(e)){
                lista.remove(i);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }

    }*/

    /*public static void modificar(Context con, ArrayList<Disco> lista, Disco nuevoDisco, Disco antiguoDisco){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(antiguoDisco)){
                lista.remove(i);
                lista.add(nuevoDisco);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }
    }*/

    public static ArrayList<Evento> leer(Context contexto){
        ArrayList<Evento> lista = new ArrayList<Evento>();

        try{
            XmlPullParser lectorxml= Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(contexto.getFilesDir(),"favoritos.xml")),"utf-8");
            int evento = lectorxml.getEventType();


            while(evento!= XmlPullParser.END_DOCUMENT){
                if(evento== XmlPullParser.START_TAG){
                    String etiqueta = lectorxml.getName();
                    if(etiqueta.compareTo("evento")==0){
                        System.out.println("LEYENDO "+lectorxml.getAttributeValue(null, "album"));
                        lista.add(new Evento(Integer.parseInt(lectorxml.getAttributeValue(null, "id")),
                                lectorxml.getAttributeValue(null, "nombre"),
                                lectorxml.getAttributeValue(null, "descripcion"),
                                lectorxml.getAttributeValue(null, "fecha"),
                                Double.parseDouble(lectorxml.getAttributeValue(null, "precio")),
                                Integer.parseInt(lectorxml.getAttributeValue(null, "idlocalizacion")),
                                Integer.parseInt(lectorxml.getAttributeValue(null, "idcategoria")),
                                Integer.parseInt(lectorxml.getAttributeValue(null, "aprobado"))
                        ));
                    }
                }
                evento = lectorxml.next();
            }

        }catch (Exception e) {
            System.out.println("ERROR AL LEER");
        }

        if(lista.size()!=0){
            return lista;
        }else{
            return new ArrayList<Evento>();
        }

    }

}
