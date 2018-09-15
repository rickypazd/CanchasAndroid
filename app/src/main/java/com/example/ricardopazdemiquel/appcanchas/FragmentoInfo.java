package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class FragmentoInfo extends Fragment {

    private JSONObject obj_complejo;

    private TextView tv_presentacion;
    private TextView tv_politicas;
    private TextView tv_caracteristicas;
    private TextView tv_contactanos;
    //private Button reservar;

    public FragmentoInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragmento_info, container, false);
        tv_presentacion=view.findViewById(R.id.tv_presentacion);
        tv_politicas=view.findViewById(R.id.tv_politicas);
        tv_caracteristicas=view.findViewById(R.id.tv_caracteristicas);
        tv_contactanos=view.findViewById(R.id.tv_contactanos);
        //reservar = view.findViewById(R.id.btnReservar);
        //reservar.setOnClickListener(this);

        try{
           obj_complejo=((detalleCancha)getActivity()).getComplejo();
           tv_presentacion.setText(Html.fromHtml(obj_complejo.getString("PRESENTACION")));
           tv_politicas.setText(Html.fromHtml(obj_complejo.getString("POLITICAS")));


        }catch (Exception e){

        }
        return view;
    }



}
