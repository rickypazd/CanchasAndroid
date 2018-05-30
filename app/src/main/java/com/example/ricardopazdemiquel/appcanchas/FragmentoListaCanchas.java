package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Hashtable;

import complementos.Contexto;

public class FragmentoListaCanchas extends Fragment {

    private ListView lvCanchas;
    private JSONArray arr_canchas;
    public FragmentoListaCanchas() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        arr_canchas=((MainActivity)getActivity()).getArr_canchas();
        AdaptadorCanchas adaptador = new AdaptadorCanchas(getContext(),arr_canchas);

        lvCanchas.setAdapter(adaptador);



        try{
            ((detalleCancha)getActivity()).rezize_fragment(1000);
        }catch (Exception e){

        }

        return view;
    }



}
