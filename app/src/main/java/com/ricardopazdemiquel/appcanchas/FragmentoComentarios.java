package com.ricardopazdemiquel.appcanchas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorComentario;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;

@SuppressLint("ValidFragment")
public class FragmentoComentarios extends Fragment {

    private ListView lv;
    private JSONObject obj_complejo;

    @SuppressLint("ValidFragment")
    public FragmentoComentarios(JSONObject obj) {
        this.obj_complejo = obj;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragmento_comentarios, container, false);
        lv = view.findViewById(R.id.list_comentario);
        try {
            JSONArray arr = obj_complejo.getJSONArray("COMENTARIOS");
            if (arr == null) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            }else if(arr.length() < 0){
                //ghgfhfgh
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                AdaptadorComentario comentario = new AdaptadorComentario(getActivity(), arr);
                lv.setAdapter(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


}
