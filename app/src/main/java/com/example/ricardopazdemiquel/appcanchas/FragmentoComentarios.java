package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentoComentarios extends Fragment {


    public FragmentoComentarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragmento_comentarios, container, false);

        try{
            ((detalleCancha)getActivity()).rezize_fragment(1000);
        }catch (Exception e){

        }
        return view;
    }


}
