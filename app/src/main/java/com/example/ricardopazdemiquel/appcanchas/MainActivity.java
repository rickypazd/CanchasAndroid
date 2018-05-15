package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



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

        seleccionarFragmento("canchas");

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
            startActivity(intent);
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
}
