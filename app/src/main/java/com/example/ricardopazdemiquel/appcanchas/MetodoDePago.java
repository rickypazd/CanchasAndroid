package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.example.ricardopazdemiquel.appcanchas.dialog.finalizar_reserva_dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import complementos.Contexto;
import complementos.infoCelda;

public class MetodoDePago extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sin_pago;
    private Button btn_targeta;
    private JSONArray arr;
    private JSONObject obj;
    private String id_cancha;
    private String fecha;
    private String id_usr;

    private static final int EFECTIVO = 1;
    private static final int TARJETA = 2;

    //private ArrayList<infoCelda> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_de_pago);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icon_left_arrow);

//        arr = (ArrayList<infoCelda>) getIntent().getSerializableExtra("arr_reservas");

        try {
            obj = new JSONObject(getIntent().getStringExtra("obj"));
            arr = obj.getJSONArray("horas");
            //id_cancha = obj.getString("id_cancha");
            fecha = obj.getString("fecha");
            JSONObject usr_log = SPref.getUsr_log(this);
            id_usr = usr_log.getString("ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_sin_pago = findViewById(R.id.btn_reserva_sin_pago);
        btn_targeta = findViewById(R.id.btn_reserva_targeta);
        btn_sin_pago.setOnClickListener(this);
        btn_targeta.setOnClickListener(this);
    }

    // Opcion para ir atras sin reiniciar en la actividad anterior de nuevo
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reserva_sin_pago:
                new Cargar_lista_complejos().execute();
                break;
            case R.id.btn_reserva_targeta:
                break;
        }
    }

    private class Cargar_lista_complejos extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public Cargar_lista_complejos() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(MetodoDePago.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("obteniendo datos");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "ok_res_sin_tarjeta");
            //parametros.put("id_cancha", id_cancha);
            parametros.put("fecha", fecha);
            parametros.put("arr", arr.toString());
            parametros.put("id_usr", id_usr);
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
                Toast.makeText(MetodoDePago.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.isEmpty()) {
                Toast.makeText(MetodoDePago.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.equals("falso")) {
                Toast.makeText(MetodoDePago.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                new finalizar_reserva_dialog(1).show(fragmentManager, "Dialog");
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }

}
