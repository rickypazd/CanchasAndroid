package com.example.ricardopazdemiquel.appcanchas.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Adapter.Adapter_tipo_canchas;
import com.example.ricardopazdemiquel.appcanchas.Main2Activity;
import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.example.ricardopazdemiquel.appcanchas.detalleCancha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;

/**
 * Created by Edson on 02/12/2017.
 */

@SuppressLint("ValidFragment")
public class tipos_de_cancha_dialog extends DialogFragment {

    private ListView lv_lista;
    private JSONObject obj;


    @SuppressLint("ValidFragment")
    public tipos_de_cancha_dialog(JSONObject obj) {
        this.obj = obj;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmanetstyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tipos_de_canchas_dialog, null);
        DisplayMetrics dm = new DisplayMetrics();

        //Una estructura que describe información general sobre una pantalla, como su tamaño, densidad y escala de fuente.
        /*getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= dm.widthPixels;
        int height = dm.heightPixels;
        getActivity().getWindow().setLayout((int)(width*.8),(int)(height*.6));*/

        builder.setView(view);

        lv_lista = view.findViewById(R.id.lv_lista);

        try {
            if (obj != null) {
                new Cargar_tipos_de_canchas(obj.getInt("ID")).execute();
            }else{
                Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
        }

        return builder.create();
    }

    private class Cargar_tipos_de_canchas extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private int id_complejo;

        public Cargar_tipos_de_canchas(int tipo) {
            this.id_complejo = tipo;
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
            parametros.put("evento", "get_tipos_canchas_de_complejo");
            parametros.put("id_complejo", id_complejo + "");
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
                    Adapter_tipo_canchas adaptador = new Adapter_tipo_canchas(getActivity(), arr);
                    lv_lista.setAdapter(adaptador);
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


