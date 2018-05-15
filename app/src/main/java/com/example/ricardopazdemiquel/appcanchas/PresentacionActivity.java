package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PresentacionActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        if (savedInstanceState == null) {
            mViewPager = findViewById(R.id.container);
            poblarViewPager(mViewPager);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // si presiono la tecla atras en la presentacion, deberia salirse de la aplicacion
        // y no volver a la anterior activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("SALIR", true);

        startActivity(intent);
    }

    private void poblarViewPager(ViewPager mViewPager) {
        AdaptadorSecciones adaptador = new AdaptadorSecciones(getSupportFragmentManager());

        adaptador.agregarFragmento(new FragmentoPresentacion1());
        adaptador.agregarFragmento(new FragmentoPresentacion2());
        adaptador.agregarFragmento(new FragmentoPresentacion3());

        mViewPager.setAdapter(adaptador);
    }

    // La idea es manejar la lista de fragmentos para la presentacion de la app
    private class AdaptadorSecciones extends FragmentStatePagerAdapter {

        private final List<Fragment> fragmentos = new ArrayList<>();

        AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        void agregarFragmento(Fragment fragmento) {
            fragmentos.add(fragmento);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

    }

}
