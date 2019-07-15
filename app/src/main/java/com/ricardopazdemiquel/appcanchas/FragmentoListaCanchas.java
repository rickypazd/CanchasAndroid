package com.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
import com.ricardopazdemiquel.appcanchas.Fragment.SetupViewPager_fragment;
import com.ricardopazdemiquel.appcanchas.Listener.Canchas_AdapterClick;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.ricardopazdemiquel.appcanchas.R;
//import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;

public class FragmentoListaCanchas extends Fragment implements Canchas_AdapterClick {

    private RecyclerView lvCanchas;
    private EditText buscar_edit;
    AdaptadorCanchas2 adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private JSONArray arr_canchas;

    public FragmentoListaCanchas() {
    }

    public void ActualizarView(AdaptadorCanchas2 nombre) {
        lvCanchas.setAdapter(nombre);
        adaptador.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        layoutManager = new LinearLayoutManager(getActivity());
        lvCanchas.setLayoutManager(layoutManager);
        arr_canchas = ((Main2Activity) getActivity()).get_complejos();

        if (arr_canchas == null) {
            //Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
        } else if (arr_canchas.length() == 0) {
           // Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
        } else {
            AdaptadorCanchas2 adaptador = new AdaptadorCanchas2(getContext(), arr_canchas);
            lvCanchas.setAdapter(adaptador);
        }

        //new Cargar_lista_complejos().execute();
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

    @Override
    public void onClick(int id, View view) {
        /*Intent inten = new Intent(getActivity(), detalleCancha.class);
        inten.putExtra("id_complejo", id);
        startActivity(inten);*/
    }


}
