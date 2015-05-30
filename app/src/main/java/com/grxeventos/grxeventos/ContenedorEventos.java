package com.grxeventos.grxeventos;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ContenedorEventos extends ArrayList<Evento> implements Parcelable{

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
