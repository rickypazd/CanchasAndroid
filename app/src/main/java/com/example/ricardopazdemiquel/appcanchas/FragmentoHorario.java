package com.example.ricardopazdemiquel.appcanchas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ValidFragment")
public class FragmentoHorario extends Fragment {

    private JSONObject obj_complejo;

    private TextView tv_hora_lunes;
    private TextView tv_hora_martes;
    private TextView tv_hora_miercoles;
    private TextView tv_hora_jueves;
    private TextView tv_hora_viernes;
    private TextView tv_hora_sabado;
    private TextView tv_hora_domingo;

    @SuppressLint("ValidFragment")
    public FragmentoHorario(JSONObject object) {
        this.obj_complejo = object;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragmento_horario, container, false);
        tv_hora_lunes=view.findViewById(R.id.tv_hora_lunes);
        tv_hora_martes=view.findViewById(R.id.tv_hora_martes);
        tv_hora_miercoles=view.findViewById(R.id.tv_hora_miercoles);
        tv_hora_jueves=view.findViewById(R.id.tv_hora_jueves);
        tv_hora_viernes=view.findViewById(R.id.tv_hora_viernes);
        tv_hora_sabado=view.findViewById(R.id.tv_hora_sabado);
        tv_hora_domingo=view.findViewById(R.id.tv_hora_domingo);

            try{
                //obj_complejo=((detalleCancha)getActivity()).getComplejo();
                JSONArray arr= obj_complejo.getJSONArray("HORARIOS");
                JSONObject obj;
                int dia;
                for (int i = 0; i < arr.length(); i++) {
                    obj=arr.getJSONObject(i);
                    dia=obj.getInt("DIA");
                    if(obj.getInt("ABIERTO")==1){
                        getview(dia).setText(obj.getString("HORA_INI")+" - "+obj.getString("HORA_FIN"));
                    }else{
                        getview(dia).setText("CERRADO");
                    }
                }

            }catch (Exception e){

            }

        return view;
    }

    private TextView getview(int dia){
            switch (dia){
                case 0:
                    return tv_hora_lunes;
                case 1:
                    return tv_hora_martes;
                case 2:
                    return tv_hora_miercoles;
                case 3:
                    return tv_hora_jueves;
                case 4:
                    return tv_hora_viernes;
                case 5:
                    return tv_hora_sabado;
                case 6:
                    return tv_hora_domingo;
            }
        return null;
    }
}
