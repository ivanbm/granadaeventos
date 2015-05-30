package com.grxeventos.grxeventos;

public class CLocalizacion {

	  private int id;
	  private String calle;
	  private String provincia;
	  private int numero;
	  private int telefono;
	  private float latitud;
	  private float longitud;
	  
	  
	public CLocalizacion(int id, String calle, String provincia, int numero,
			int telefono, float latitud, float longitud) {
		super();
		this.id = id;
		this.calle = calle;
		this.provincia = provincia;
		this.numero = numero;
		this.telefono = telefono;
		this.latitud = latitud;
		this.longitud = longitud;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public int getTelefono() {
		return telefono;
	}


	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}


	public float getLatitud() {
		return latitud;
	}


	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}


	public float getLongitud() {
		return longitud;
	}


	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}


	
	  
	  
}
