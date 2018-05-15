package com.example.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentoListaCanchas extends Fragment {

    private ListView lvCanchas;

    public FragmentoListaCanchas() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        AdaptadorCanchas adaptador = new AdaptadorCanchas(getContext(),
                ListaCanchas.getInstancia().getListaCanchas());

        lvCanchas.setAdapter(adaptador);

        try{
            ((detalleCancha)getActivity()).rezize_fragment(1000);
        }catch (Exception e){

        }

        return view;
    }


}
