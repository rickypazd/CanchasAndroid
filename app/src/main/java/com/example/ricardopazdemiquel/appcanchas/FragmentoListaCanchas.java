package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
import com.example.ricardopazdemiquel.appcanchas.Listener.Canchas_AdapterClick;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
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
        new Cargar_lista_complejos().execute();
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
        Intent inten = new Intent(getActivity(), detalleCancha.class);
        inten.putExtra("id_complejo", id);
        startActivity(inten);
    }

    private class Cargar_lista_complejos extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public Cargar_lista_complejos() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(getActivity());
            progreso.setIndeterminate(true);
            progreso.setTitle("obteniendo datos");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "get_complejos");
            String respuesta = "";
            try {
                respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_android), MethodType.POST, parametros));
            } catch (Exception ex) {
                Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            progreso.dismiss();
            if (resp == null) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.isEmpty()) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.equals("falso")) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray arr = new JSONArray(resp);
                    AdaptadorCanchas2 adaptador = new AdaptadorCanchas2(getContext(), arr);
                    lvCanchas.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

    }

}
