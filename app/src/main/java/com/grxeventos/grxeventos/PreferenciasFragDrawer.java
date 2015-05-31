package com.grxeventos.grxeventos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ivan on 31/05/2015.
 */
public class PreferenciasFragDrawer extends Fragment {

    public PreferenciasFragDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.preferencias_frag_drawer, container, false);
    }

}
