package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import complementos.infoCelda;

public class detalle_reserva extends AppCompatActivity {

    private ArrayList<infoCelda> arr;
    private TableLayout tableLayout;
    private TextView tvtotal;
    private Button btn_confirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva);
        arr = (ArrayList<infoCelda>) getIntent().getSerializableExtra("arr_reservas");
        tableLayout=findViewById(R.id.table_layout);
        tableLayout.setBackgroundColor(Color.BLACK);
        tvtotal=findViewById(R.id.tv_total);
        btn_confirmar=findViewById(R.id.btn_confirmar);
        TableRow header = new TableRow(this);

        TextView celfecha=new TextView(this);
        celfecha.setGravity(Gravity.CENTER);
        celfecha.setTextSize(18);
        celfecha.setBackgroundColor(Color.WHITE);
        celfecha.setText("FECHA");
        TextView celhora=new TextView(this);
        celhora.setGravity(Gravity.CENTER);
        celhora.setTextSize(18);
        celhora.setBackgroundColor(Color.WHITE);
        celhora.setText("HORA");
        TextView celprecio=new TextView(this);
        celprecio.setGravity(Gravity.CENTER);
        celprecio.setTextSize(18);
        celprecio.setBackgroundColor(Color.WHITE);
        celprecio.setText("PRECIO");

        header.addView(celfecha,newTableRowParams());
        header.addView(celhora,newTableRowParams());
        header.addView(celprecio,newTableRowParams());
        tableLayout.addView(header);

        infoCelda cel;
        TableRow row;
        TextView fecha;
        TextView hora;
        TextView precio;
        int total=0;
        for (int i = 0; i <arr.size() ; i++) {
            cel=arr.get(i);
            row= new TableRow(this);
            fecha=new TextView(this);
            fecha.setGravity(Gravity.CENTER);
            fecha.setTextSize(18);
            fecha.setBackgroundColor(Color.WHITE);
            fecha.setText(cel.getFecha());
            hora=new TextView(this);
            hora.setGravity(Gravity.CENTER);
            hora.setTextSize(18);
            hora.setBackgroundColor(Color.WHITE);
            hora.setText(cel.getHora());
            precio=new TextView(this);
            precio.setGravity(Gravity.CENTER);
            precio.setTextSize(18);
            precio.setBackgroundColor(Color.WHITE);
            precio.setText(cel.getCosto()+"");
            row.addView(fecha,newTableRowParams());
            row.addView(hora,newTableRowParams());
            row.addView(precio,newTableRowParams());
            tableLayout.addView(row);
            total+=cel.getCosto();
        }
        tvtotal.setText("TOTAL: "+total);

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detalle_reserva.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }
}
