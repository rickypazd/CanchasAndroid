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

    public FragmentoListaCanchas() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        new CargarListaTask().execute();

        try{
            ((detalleCancha)getActivity()).rezize_fragment(1000);
        }catch (Exception e){

        }

        return view;
    }

    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;


        public CargarListaTask(){

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
            String respuesta="";
            try {
                 respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, parametros));
            } catch (Exception ex) {
                Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
            }

            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            progreso.dismiss();
            if(resp ==""){
                Toast.makeText(getActivity(),"Error al obtener Datos" ,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray arr = new JSONArray(resp);
                AdaptadorCanchas adaptador = new AdaptadorCanchas(getContext(),
                        arr);

                lvCanchas.setAdapter(adaptador);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

    }

}
