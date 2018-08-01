package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;

public class FragmentoComentarios extends Fragment {


    private ListView lv;
    private JSONObject obj_complejo;

    public FragmentoComentarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragmento_comentarios, container, false);
        lv=view.findViewById(R.id.list_comentario);

        try {
            obj_complejo=((detalleCancha)getActivity()).getComplejo();
            JSONArray arr = obj_complejo.getJSONArray("COMENTARIOS");
            AdaptadorComentario comentario = new AdaptadorComentario(getActivity(),arr);
            lv.setAdapter(comentario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //new CargarListaTask().execute();

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
            parametros.put("evento", "get_comentario");
            parametros.put("id_complejo", "sd");
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
            if(resp =="" || resp == null){
                Toast.makeText(getActivity(),"Error al obtener Datos" ,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray arr = new JSONArray(resp);
                AdaptadorComentario comentario = new AdaptadorComentario(getActivity(),arr);
                lv.setAdapter(comentario);
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
