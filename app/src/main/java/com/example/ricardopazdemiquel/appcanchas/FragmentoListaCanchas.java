package com.example.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
//import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;

public class FragmentoListaCanchas extends Fragment {

    private RecyclerView lvCanchas;
    private JSONArray arr_canchas;
    private EditText buscar_edit;
    AdaptadorCanchas2 adaptador;
    private RecyclerView.LayoutManager layoutManager;
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
        arr_canchas = ((Main2Activity) getActivity()).getArr_canchas();
        adaptador = new AdaptadorCanchas2(getContext(), arr_canchas);
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
