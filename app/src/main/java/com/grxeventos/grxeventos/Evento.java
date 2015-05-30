package com.grxeventos.grxeventos;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Evento implements Parcelable, Serializable, Comparable<Evento>{
	
	
	private int id;
      private String nombre;
      private String descripcion;
      private String fecha;
      private double precio;
      private int idlocalizacion;
      private int idcategoria;
      private int aprobado;

    public Evento() {
    }
	
	  public Evento(Parcel in) { 
		  readFromParcel(in);
	  
	  }

	public Evento(int id, String nombre, String descripcion, String fecha,
			double precio, int idlocalizacion, int idcategoria, int aprobado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.precio = precio;
		this.idlocalizacion = idlocalizacion;
		this.idcategoria = idcategoria;
		this.aprobado = aprobado;
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}



	public int getIdlocalizacion() {
		return idlocalizacion;
	}



	public void setIdlocalizacion(int idlocalizacion) {
		this.idlocalizacion = idlocalizacion;
	}



	public int getIdcategoria() {
		return idcategoria;
	}



	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}



	public int getAprobado() {
		return aprobado;
	}



	public void setAprobado(int aprobado) {
		this.aprobado = aprobado;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    @Override
    public int compareTo(Evento evento) {
        return 0;
    }

    @Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(nombre);
		dest.writeString(descripcion);
		dest.writeString(fecha);
		dest.writeDouble(precio);
		dest.writeInt(idlocalizacion);
		dest.writeInt(idcategoria);
		dest.writeInt(aprobado);
		
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		nombre = in.readString();
		descripcion = in.readString();
		fecha = in.readString();
		precio = in.readDouble();
		idlocalizacion = in.readInt();
		idcategoria = in.readInt();
		aprobado = in.readInt();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Evento createFromParcel(Parcel in) { 
			return new Evento(in); 
			}   
		public Evento[] newArray(int size) { 
			return new Evento[size]; 
			} 
		}; 

}
