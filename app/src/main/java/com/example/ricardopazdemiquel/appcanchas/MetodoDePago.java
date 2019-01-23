package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import complementos.infoCelda;

public class MetodoDePago extends AppCompatActivity {

    private Button btn_sin_pago;
    private Button btn_targeta;

    private  ArrayList<infoCelda> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_de_pago);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icon_left_arrow);

        arr = (ArrayList<infoCelda>) getIntent().getSerializableExtra("arr_reservas");
        btn_sin_pago=findViewById(R.id.btn_reserva_sin_pago);
        btn_targeta=findViewById(R.id.btn_reserva_targeta);

        btn_sin_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MetodoDePago.this,Fragment_reserva_finalizada.class);
                //intent.putExtra("arr_reservas",arr);
                startActivity(intent);
                finish();
            }
        });

        btn_targeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

}
