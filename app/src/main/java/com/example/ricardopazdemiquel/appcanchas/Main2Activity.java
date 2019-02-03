package com.example.ricardopazdemiquel.appcanchas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
import com.example.ricardopazdemiquel.appcanchas.Fragment.Fragmento_busqueda;
import com.example.ricardopazdemiquel.appcanchas.Fragment.SetupViewPager_fragment;
import com.example.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import complementos.Contexto;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, OnClickListener {

    private DrawerLayout drawer;
    private ImageView btn_ver_menu;
    private TextView buscar_edit;
    private ImageButton btn_recargar;
    private JSONArray arr_canchas;
    private android.support.v7.widget.CardView nav_mis_reservas;
    private android.support.v7.widget.CardView nav_canchas;
    private android.support.v7.widget.CardView nav_mapa;
    private android.support.v7.widget.CardView nav_mis_contactanos;
    private android.support.v7.widget.CardView nav_preferencias;
    private TextView text_telefono;
    private TextView text_nombre;
    private com.mikhaellopez.circularimageview.CircularImageView img_photo;
    static final int PICK_CONTACT_REQUEST = 1;
    private ImageView nav_select1, nav_select2, nav_select3;
    static final int RECARGAR_CANCHA = 1;
    static final int RECARGAR_MAPA = 2;
    static final int RECARGAR_HISTORIAL = 3;
    static final int RECARGAR_COMPLEJO = 4;
    private int select_fragment , id_complejo;

    public int getSelect_fragment() {
        return select_fragment;
    }

    public void setSelect_fragment(int select_fragment , int id) {
        this.select_fragment = select_fragment;
        this.id_complejo = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View header = navigationView.inflateHeaderView(R.layout.nav_header_main2);
        navigationView.setNavigationItemSelectedListener(this);

        btn_recargar = findViewById(R.id.btn_recargar);
        buscar_edit = findViewById(R.id.buscar_edit);

        nav_mis_reservas = header.findViewById(R.id.nav_mis_reservas);
        nav_canchas = header.findViewById(R.id.nav_canchas);
        nav_mapa = header.findViewById(R.id.nav_mapa);
        nav_mis_contactanos = header.findViewById(R.id.nav_mis_contactanos);
        nav_preferencias = header.findViewById(R.id.nav_preferencias);
        text_telefono = header.findViewById(R.id.text_telefono);
        text_nombre = header.findViewById(R.id.text_nombre);
        img_photo = header.findViewById(R.id.img_photo);

        nav_select1 = findViewById(R.id.nav_select1);
        nav_select2 = findViewById(R.id.nav_select2);
        nav_select3 = findViewById(R.id.nav_select3);

        nav_mis_reservas.setOnClickListener(this);
        nav_canchas.setOnClickListener(this);
        nav_mapa.setOnClickListener(this);
        nav_mis_contactanos.setOnClickListener(this);
        nav_preferencias.setOnClickListener(this);
        btn_recargar.setOnClickListener(this);
        buscar_edit.setOnClickListener(this);

        btn_ver_menu = findViewById(R.id.btn_ver_menu);
        btn_ver_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        get_nav_perfil();

        new Cargar_lista_complejos().execute();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigacion_canchas:
                        seleccionarFragmento("canchas");
                        return true;
                    case R.id.navigacion_map:
                        seleccionarFragmento("mapa");
                        return true;
                    case R.id.navigacion_history:
                        seleccionarFragmento("historial");
                        return true;
                }
                return false;
            }
        });

        //seleccionarFragmento("canchas");

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
                nav_select1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                nav_select2.setBackground(getDrawable(R.drawable.fondobar));
                nav_select3.setBackground(getDrawable(R.drawable.fondobar));
                setSelect_fragment(1,0);
                break;
            case "mapa":
                fragmentoGenerico = new FragmentoMapa();
                nav_select2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                nav_select1.setBackground(getDrawable(R.drawable.fondobar));
                nav_select3.setBackground(getDrawable(R.drawable.fondobar));
                setSelect_fragment(2,0);
                break;
            case "historial":
                fragmentoGenerico = new SetupViewPager_fragment();
                nav_select3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                nav_select1.setBackground(getDrawable(R.drawable.fondobar));
                nav_select2.setBackground(getDrawable(R.drawable.fondobar));
                setSelect_fragment(3,0);
                break;
            case "complejo":
                fragmentoGenerico = new detalleCancha(id_complejo);
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();

        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.buscar_edit:
                Intent intent = new Intent(this, SearchToolbarLight.class);
                intent.putExtra("obj", arr_canchas.toString());
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                break;
            case R.id.btn_recargar:
                refreshFragment();
                break;
            case R.id.nav_mis_reservas:
                setSelect_fragment(3,0);
                fragmentoGenerico = new SetupViewPager_fragment();
                break;
            case R.id.nav_canchas:
                setSelect_fragment(1,0);
                fragmentoGenerico = new FragmentoListaCanchas();
                break;
            case R.id.nav_mapa:
                setSelect_fragment(2,0);
                fragmentoGenerico = new FragmentoMapa();
                break;
            case R.id.nav_mis_contactanos:
                setSelect_fragment(20,0);
                fragmentoGenerico = new Contactanos_fragment();
                break;
            case R.id.nav_preferencias:
                setSelect_fragment(20,0);
                fragmentoGenerico = new Preferencias();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();
            drawer.closeDrawers();
        }
    }

    private void refreshFragment() {
        switch (getSelect_fragment()) {
            case RECARGAR_CANCHA:
                new Cargar_lista_complejos().execute();
                break;
            case RECARGAR_MAPA:
                seleccionarFragmento("mapa");
                break;
            case RECARGAR_HISTORIAL:
                seleccionarFragmento("historial");
                break;
            case RECARGAR_COMPLEJO:
                seleccionarFragmento("complejo");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            int resultado = data.getExtras().getInt("RESULTADO");
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            if (requestCode == 1) {
                onStar(resultado);
            }
        }
    }

    public void onStar(int id) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentoGenerico = new detalleCancha(id);
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


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                seleccionarFragmento("canchas");
            } else {
                runtime_permissions();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void get_nav_perfil() {
        JSONObject usr = SPref.getUsr_log(Main2Activity.this);
        runtime_permissions();
        try {
            if (usr != null) {
                if (usr.getString("ID_FACE").length() > 0) {
                    //cargar_img_face
                    text_nombre.setText(usr.getString("NOMBRE") + " " + usr.getString("APELLIDO"));
                    text_telefono.setText("+591 " + usr.getString("TELEFONO"));
                    String id_face = usr.getString("ID_FACE");
                    String url = "https://graph.facebook.com/" + id_face + "/picture?type=large";
                    new AsyncTaskLoadImage(img_photo).execute(url);
                }
            } else {
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class AsyncTaskLoadImage extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;

        public AsyncTaskLoadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public JSONArray get_complejos() {
        return arr_canchas;
    }

    private class Cargar_lista_complejos extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public Cargar_lista_complejos() {
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
                Toast.makeText(Main2Activity.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.isEmpty()) {
                Toast.makeText(Main2Activity.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.equals("falso")) {
                Toast.makeText(Main2Activity.this, "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray arr = new JSONArray(resp);
                    arr_canchas = arr;
                    seleccionarFragmento("canchas");
                    //AdaptadorCanchas2 adaptador = new AdaptadorCanchas2(getContext(), arr);
                    //lvCanchas.setAdapter(adaptador);
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
