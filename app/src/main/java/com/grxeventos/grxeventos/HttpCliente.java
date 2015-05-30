package com.grxeventos.grxeventos;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class HttpCliente {

	public static boolean descargarFicheroHttp(String pathServidor, String pathDispositivo){
		
		Log.w("PATH", ""+pathServidor+ " --- "+pathDispositivo);
		
		URL url;
		try {
			url = new URL(""+pathServidor);
			// establecemos conexion
            URLConnection urlCon = url.openConnection();
            // Se obtiene el inputStream de la foto web y se abre el fichero local.
            InputStream is = urlCon.getInputStream();
            FileOutputStream fos = new FileOutputStream(pathDispositivo);
            
            // Lectura de la web y escritura en fichero local
            byte[] array = new byte[20480]; // buffer temporal de lectura.
            int leido = is.read(array);
            while (leido > 0) {
                fos.write(array, 0, leido);
                leido = is.read(array);
            }
 
            // cierre de conexion y fichero.
            is.close();
            fos.close();

            return true;
		}catch(MalformedURLException e){
			Log.e("ERROR MALF", e.getMessage());
			return false;
		}catch(Exception e){
			Log.e("ERROR DESC", e.getMessage());
			return false;
		}
	}
	
	public static int getTamanoFichero(String pathServidor){
		URL url;
		try {
			url = new URL(""+pathServidor);
			URLConnection urlCon = url.openConnection();
			
			return urlCon.getContentLength();
			
		}catch(MalformedURLException e){
			Log.e("ERROR MALF", e.getMessage());
			return 0;
		}catch(Exception e){
			Log.e("ERROR", e.getMessage());
			return 0;
		}
	}

	public static JSONArray getJSONfromURL(String url){
		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		
		
		// CONECTAMOS CON HTTP VIA POST
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.v("HTTPCLIENT", "Paso 1 correcto");
		}catch(Exception e){
			Log.e("ERROR => ", "Error en conexion http : "+e.toString());
			e.printStackTrace();
			return null;
		}
		
		// SI EL SERVICIO NOS DEVUELVE DATOS EN EL InputStream is
		if (is!=null){
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result=sb.toString();
				Log.v("HTTPCLIENT", "Paso 2 correcto");
			}catch(Exception e){
				Log.e("ERROR => ", "En datos devueltos por el Servicio POST : "+e.toString());
				e.printStackTrace();
				return null;
			}
		}
		//RECOGEMOS LOS DATOS DEVUELTOS POR EL POST Y CONVERTIDOS A STRING Y LOS DEVOLVEMOS COMO UN OBJETO JSONArray
		try{
			jArray = new JSONArray(result);
			Log.v("HTTPCLIENT", "Paso 3 correcto");
			return jArray;
			
		} catch(JSONException e){
			Log.e("ERROR => ", "Error convirtiendo los datos a JSON : "+e.toString());
			e.printStackTrace();
			return null;
		}
	}
}
