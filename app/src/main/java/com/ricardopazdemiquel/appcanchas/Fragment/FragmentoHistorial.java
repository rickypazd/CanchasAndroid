package com.ricardopazdemiquel.appcanchas.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorHistory;
import com.ricardopazdemiquel.appcanchas.R;
import com.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import complementos.Contexto;
import complementos.infoCelda;


public class FragmentoHistorial extends Fragment {

    private RecyclerView lv;
    private LinearLayout liner_reservas;
    private String id_usr;
    private RecyclerView.LayoutManager layoutManager;

    public FragmentoHistorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_historial, container, false);

        lv = view.findViewById(R.id.list_history);
        layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);
        liner_reservas = view.findViewById(R.id.liner_reservas);

        try {
            JSONObject usr_log = SPref.getUsr_log(getContext());
            id_usr = usr_log.getString("ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new CargarListaTask().execute();
        return view;
    }



    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public CargarListaTask() {
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
            parametros.put("evento", "get_reservas_usuario");
            parametros.put("id", id_usr);
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
                liner_reservas.setVisibility(View.VISIBLE);
            } else if (resp.isEmpty()) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
                liner_reservas.setVisibility(View.VISIBLE);
            } else if (resp.contains("falso")) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
                liner_reservas.setVisibility(View.VISIBLE);
            } else {
                try {
                    liner_reservas.setVisibility(View.GONE);
                    JSONArray arr = new JSONArray(resp);
                    AdaptadorHistory history = new AdaptadorHistory(getActivity(), arr);
                    lv.setAdapter(history);
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
