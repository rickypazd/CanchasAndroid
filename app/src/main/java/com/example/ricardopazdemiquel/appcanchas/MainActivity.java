package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Hashtable;

import complementos.Contexto;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

private JSONArray arr_canchas;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigacion_canchas: // Buscar
                    seleccionarFragmento("canchas");
                    return true;
                case R.id.navigacion_map: // Lista de canchas
                    seleccionarFragmento("mapa");
                    return true;
                case R.id.navigacion_history: // Filtro
                    seleccionarFragmento("history");
                    return true;
                case R.id.navigacion_config: // Filtro
                    seleccionarFragmento("config");
                    return true;
            }

            return false;
        }
    };



    private void seleccionarFragmento(String fragmento) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (fragmento) {
            case "canchas":
                fragmentoGenerico = new FragmentoListaCanchas();
                break;

            case "mapa":
                fragmentoGenerico = new FragmentoMapa();
                break;

            case "history":
                fragmentoGenerico = new FragmentoHistorial();
                break;
            case "config":
                fragmentoGenerico = new FragementoConfig();
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentoContenedor, fragmentoGenerico)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().getBooleanExtra("SALIR", false)) {
            finish();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        Button buton = findViewById(R.id.button);
//        buton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, PresentacionActivity.class);
//                startActivity(intent);
//            }
//        });

        if (primeraVezEjecutada()) {
            Intent intent = new Intent(MainActivity.this, PresentacionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(AccessToken.getCurrentAccessToken() == null){
            Intent intent = new Intent(this,login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            new CargarListaTask().execute();
        }


    }

    public boolean primeraVezEjecutada() {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean primeraVez = preferencias.getBoolean("PrimeraVez", false);

        if (!primeraVez) {
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putBoolean("PrimeraVez", true);
            editor.commit();
        }

        return !primeraVez;
    }


    public JSONArray getArr_canchas(){
        return arr_canchas;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;


        public CargarListaTask(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(MainActivity.this);
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
            if(resp =="" || resp == null){
                Toast.makeText(MainActivity.this,"Error al obtener Datos" ,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray arr = new JSONArray(resp);
                arr_canchas=arr;
                seleccionarFragmento("canchas");

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
