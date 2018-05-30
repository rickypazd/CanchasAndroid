package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        arr = (ArrayList<infoCelda>) getIntent().getSerializableExtra("arr_reservas");
        btn_sin_pago=findViewById(R.id.btn_reserva_sin_pago);
        btn_targeta=findViewById(R.id.btn_reserva_targeta);

        btn_sin_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MetodoDePago.this,detalle_reserva.class);
                intent.putExtra("arr_reservas",arr);
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
}
