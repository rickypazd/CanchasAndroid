package com.example.ricardopazdemiquel.appcanchas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ricardopazdemiquel.appcanchas.Adapter.Adaptador_busquedas;
import com.example.ricardopazdemiquel.appcanchas.Main2Activity;
import com.example.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;


public class Fragmento_busqueda extends Fragment {

    private ListView list_busquedas;
    private JSONArray arr_canchas;

    public Fragmento_busqueda() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_busqueda, container, false);
        list_busquedas = view.findViewById(R.id.list_busquedas);
        arr_canchas = ((Main2Activity) getActivity()).getArr_canchas();
        Adaptador_busquedas adaptador = new Adaptador_busquedas(getContext(), arr_canchas);
        list_busquedas.setAdapter(adaptador);

        return view;
    }
}
