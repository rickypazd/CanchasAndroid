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

public class detalle_reserva extends AppCompatActivity implements View.OnClickListener{

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
        tableLayout.setBackgroundColor(Color.TRANSPARENT);
        tvtotal=findViewById(R.id.tv_total);
        btn_confirmar=findViewById(R.id.btn_confirmar);
        btn_confirmar.setOnClickListener(this);
        TableRow header = new TableRow(this);
        header.setBackgroundColor(Color.TRANSPARENT);

        TextView celfecha=new TextView(this);
        celfecha.setGravity(Gravity.CENTER);
        celfecha.setTextSize(18);
        celfecha.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        celfecha.setText("FECHA");
        TextView celhora=new TextView(this);
        celhora.setGravity(Gravity.CENTER);
        celhora.setTextSize(18);
        celhora.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        celhora.setText("HORA");
        TextView celprecio=new TextView(this);
        celprecio.setGravity(Gravity.CENTER);
        celprecio.setTextSize(18);
        celprecio.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
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
            fecha.setTextSize(15);
            fecha.setPadding(0,10,0,10);
            fecha.setTextColor(Color.BLACK);
            fecha.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_fecha));
            fecha.setText(cel.getFecha());

            hora=new TextView(this);
            hora.setGravity(Gravity.CENTER);
            hora.setTextSize(15);
            hora.setPadding(0,10,0,10);
            hora.setTextColor(Color.BLACK);
            hora.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_hora));
            hora.setText(cel.getHora());

            precio=new TextView(this);
            precio.setGravity(Gravity.CENTER);
            precio.setPadding(0,10,0,10);
            precio.setTextSize(15);
            precio.setTextColor(Color.WHITE);
            precio.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_precio));
            precio.setText("Bs."+cel.getCosto()+" ");


            row.addView(fecha,newTableRowParams());
            row.addView(hora,newTableRowParams());
            row.addView(precio,newTableRowParams());
            tableLayout.addView(row);
            total+=cel.getCosto();
        }
        tvtotal.setText("Total: Bs. "+total);

    }
    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(0,0,0,15);
        params.weight=1;
        return params;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirmar:
                Intent intent = new Intent(detalle_reserva.this,MetodoDePago.class);
                startActivity(intent);
                finish();
        }
    }
}
