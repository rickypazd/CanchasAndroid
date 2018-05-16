package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.invoke.MethodType;
import java.util.Hashtable;

public class FragmentoListaCanchas extends Fragment {

    private ListView lvCanchas;

    public FragmentoListaCanchas() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_canchas, container, false);

        lvCanchas = view.findViewById(R.id.lvCanchas);
        AdaptadorCanchas adaptador = new AdaptadorCanchas(getContext(),
                ListaCanchas.getInstancia().getListaCanchas());

        lvCanchas.setAdapter(adaptador);

        try{
            ((detalleCancha)getActivity()).rezize_fragment(1000);
        }catch (Exception e){

        }

        return view;
    }

    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private double lat;
        private double lon;
        private int id;

        public CargarListaTask(double lat, double lon, int id){
            this.lat=lat;
            this.lon=lon;
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "set_pos_vehiculo");
            parametros.put("lat",lat+"");
            parametros.put("lon",lon+"");
            parametros.put("id_vehiculo",id+"");
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, parametros));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

    }

}
