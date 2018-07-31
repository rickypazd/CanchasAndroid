package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import complementos.Contexto;


import static android.content.Context.MODE_PRIVATE;

public class Detalle_complejoActivity extends AppCompatActivity {

    private static final String TAG ="fragment_explorar";

    private TextView text_cancha;
    private TextView text_fecha;
    private TextView text_hora;
    private TextView text_monto;
    private TextView text_tipoPago;
    private TextView text_Complejo;
    private TextView text_estado;

    @Override
    protected void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_detalle_complejo);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_cancha = findViewById(R.id.text_cancha);
        text_Complejo= findViewById(R.id.text_Complejo);
        text_fecha = findViewById(R.id.text_fecha);
        text_hora = findViewById(R.id.text_hora);
        text_monto= findViewById(R.id.text_monto);
        text_estado = findViewById(R.id.text_Estado);
        text_tipoPago= findViewById(R.id.text_tipoPago);

        String obj = getIntent().getStringExtra("complejo");
        try {
            JSONObject objeto  = new JSONObject(obj);
            cargarUsuario(objeto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }

    // Opcion para ir atras sin reiniciar el la actividad anterior de nuevo
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


    private void cargarUsuario(JSONObject obj){
        if (obj != null) {
            try {
                String nombreComplejo = obj.getString("NOMBRE_COMP");
                String estado = obj.getString("ESTADO");
                String cancha = obj.getString("NOMBRE_CAN");
                String fecha = obj.getString("FECHA");
                String hora = obj.getString("HORAS");
                String monto = obj.getString("TOTAL");
                String tipo_pago = obj.getString("TIPO_PAGO");

                text_Complejo.setText(nombreComplejo);
                text_estado.setText(estado);
                text_cancha.setText(cancha);
                text_fecha.setText(fecha);
                text_hora.setText(hora);
                text_monto.setText(monto);
                text_tipoPago.setText(tipo_pago);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            finish();
        }
    }

    private class CargarListaDetalle extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        private int id;

        public CargarListaDetalle(int id){
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(Detalle_complejoActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("obteniendo datos");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "get_detalle_reserva");
            parametros.put("id", id+"");
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
                Toast.makeText(Detalle_complejoActivity.this,"Error al obtener Datos" , Toast.LENGTH_SHORT).show();
                return;
            }else {
                try {
                    JSONObject obj = new JSONObject(resp);
                    cargarUsuario(obj);
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
