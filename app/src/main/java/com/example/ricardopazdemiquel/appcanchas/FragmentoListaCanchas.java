package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;



import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
//import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import complementos.Contexto;

public class FragmentoListaCanchas extends Fragment {

    private ListView lvCanchas;
    private JSONArray arr_canchas;
    private EditText buscar_edit;
    AdaptadorCanchas adaptador;

    public FragmentoListaCanchas() {
    }

    public void ActualizarView(AdaptadorCanchas nombre) {
        lvCanchas.setAdapter(nombre);
        adaptador.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        arr_canchas = ((Main2Activity) getActivity()).getArr_canchas();
        adaptador = new AdaptadorCanchas(getContext(), arr_canchas);
        lvCanchas.setAdapter(adaptador);
       /* buscar_edit=view.findViewById(R.id.buscar_edit);

        if(arr_canchas!=null){
            final AdaptadorCanchas adaptador = new AdaptadorCanchas(getContext(), arr_canchas);
     arr_canchas = ((Main2Activity) getActivity()).getArr_canchas();
            lvCanchas.setAdapter(adaptador);
            Toolbar toolbar = view.findViewById(R.id.toolbar2);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            buscar_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    adaptador.getFilter().filter(s.toString());
                }
            });
            try {
                ((detalleCancha) getActivity()).rezize_fragment(1000);
            } catch (Exception e) {

            }
        }*/
        return view;
    }




}
