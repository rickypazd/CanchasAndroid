package com.example.ricardopazdemiquel.appcanchas.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.AdaptadorCanchas;
import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorHistory;
import com.example.ricardopazdemiquel.appcanchas.Adapter.Adaptador_busquedas;
import com.example.ricardopazdemiquel.appcanchas.Main2Activity;
import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Hashtable;

import complementos.Contexto;


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
