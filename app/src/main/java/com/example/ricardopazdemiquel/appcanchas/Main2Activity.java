package com.example.ricardopazdemiquel.appcanchas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Fragment.FragmentoHistorial;
import com.example.ricardopazdemiquel.appcanchas.Fragment.Fragmento_busqueda;
import com.example.ricardopazdemiquel.appcanchas.Fragment.SetupViewPager_fragment;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener  ,OnClickListener {

    private ImageView btn_ver_menu;
    private TextView buscar_edit;
    private JSONArray arr_canchas;
    private android.support.v7.widget.CardView nav_mis_reservas;
    private android.support.v7.widget.CardView nav_canchas;
    private android.support.v7.widget.CardView nav_mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_main2);
        navigationView.setNavigationItemSelectedListener(this);

        nav_mis_reservas = header.findViewById(R.id.nav_mis_reservas);
        nav_canchas = header.findViewById(R.id.nav_canchas);
        nav_mapa = header.findViewById(R.id.nav_mapa);

        nav_mis_reservas.setOnClickListener(this);
        nav_canchas.setOnClickListener(this);
        nav_mapa.setOnClickListener(this);

        btn_ver_menu= findViewById(R.id.btn_ver_menu);
        btn_ver_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else{
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                }
                return false;
            }
        });

        new CargarListaTask().execute();

        buscar_edit= findViewById(R.id.buscar_edit);
        buscar_edit.setOnClickListener(this);
        buscar_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AdaptadorCanchas a  = new AdaptadorCanchas(Main2Activity.this ,arr_canchas);
                FragmentoListaCanchas sdd = new FragmentoListaCanchas();
                sdd.ActualizarView(a);
                a.getFilter().filter(s.toString().trim());
                //a.FilterTextShared(s.toString().trim());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                fragmentoGenerico= new SetupViewPager_fragment();
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
    public void onClick(View v) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id=v.getId();
        switch (id){
            case R.id.nav_mis_reservas:
                fragmentoGenerico= new SetupViewPager_fragment();
                break;
            case R.id.nav_canchas:
                fragmentoGenerico = new FragmentoListaCanchas();
                break;
            case R.id.nav_mapa:
                fragmentoGenerico = new FragmentoMapa();
                break;
            case R.id.buscar_edit:
                fragmentoGenerico = new Fragmento_busqueda();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();
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
    public JSONObject getUsr_log() {
        SharedPreferences preferencias = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);
                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
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
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                new CargarListaTask().execute();
            }else {
                runtime_permissions();
            }
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public CargarListaTask(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(Main2Activity.this);
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
            if(resp == null){
                Toast.makeText(Main2Activity.this,"Error al obtener Datos" , Toast.LENGTH_SHORT).show();
            }else if(resp.isEmpty()){
                Toast.makeText(Main2Activity.this,"Error al obtener Datos" , Toast.LENGTH_SHORT).show();
            }else if(resp.equals("falso")) {
                Toast.makeText(Main2Activity.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONArray arr = new JSONArray(resp);
                    arr_canchas = arr;
                    seleccionarFragmento("canchas");

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
