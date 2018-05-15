package com.example.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentoPresentacion3 extends Fragment {
    
    public FragmentoPresentacion3() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_presentacion_3, container, false);

        Button btnListo = view.findViewById(R.id.btnListo);
        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // al presionar el boton hay que cerrar la actividad contenedora
                getActivity().finish();
            }
        });
        
        return view;
    }

}
