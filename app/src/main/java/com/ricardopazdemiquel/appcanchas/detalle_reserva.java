package com.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
import com.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import complementos.Contexto;
import complementos.infoCelda;

public class detalle_reserva extends AppCompatActivity implements View.OnClickListener {

    private JSONArray arr;
    private JSONObject obj;
    private TableLayout tableLayout;
    private TextView tvtotal;
    private Button btn_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icon_left_arrow);

        try {
            obj = new JSONObject(getIntent().getStringExtra("obj"));
            arr = obj.getJSONArray("horas");

            tableLayout = findViewById(R.id.table_layout);
            tableLayout.setBackgroundColor(Color.TRANSPARENT);
            tvtotal = findViewById(R.id.tv_total);
            btn_confirmar = findViewById(R.id.btn_confirmar);
            btn_confirmar.setOnClickListener(this);

            TableRow header = new TableRow(this);
            header.setBackgroundColor(Color.TRANSPARENT);

            TextView celfecha = new TextView(this);
            celfecha.setGravity(Gravity.CENTER);
            celfecha.setTextSize(18);
            celfecha.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
            celfecha.setText("FECHA");
            TextView celhora = new TextView(this);
            celhora.setGravity(Gravity.CENTER);
            celhora.setTextSize(18);
            celhora.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
            celhora.setText("HORA");
            TextView celprecio = new TextView(this);
            celprecio.setGravity(Gravity.CENTER);
            celprecio.setTextSize(18);
            celprecio.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
            celprecio.setText("PRECIO");

            header.addView(celfecha, newTableRowParams());
            header.addView(celhora, newTableRowParams());
            header.addView(celprecio, newTableRowParams());
            tableLayout.addView(header);

            JSONObject cel;
            TableRow row;
            TextView fecha;
            TextView hora;
            TextView precio;
            int total = 0;
            for (int i = 0; i < arr.length(); i++) {

                cel = arr.getJSONObject(i);

                row = new TableRow(this);

                fecha = new TextView(this);
                fecha.setGravity(Gravity.CENTER);
                fecha.setTextSize(15);
                fecha.setPadding(0, 10, 0, 10);
                fecha.setTextColor(Color.BLACK);
                fecha.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_fecha));

                fecha.setText(obj.getString("fecha"));


                hora = new TextView(this);
                hora.setGravity(Gravity.CENTER);
                hora.setTextSize(15);
                hora.setPadding(0, 10, 0, 10);
                hora.setTextColor(Color.BLACK);
                hora.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_hora));

                hora.setText(cel.getString("hora"));

                precio = new TextView(this);
                precio.setGravity(Gravity.CENTER);
                precio.setPadding(0, 10, 0, 10);
                precio.setTextSize(15);
                precio.setTextColor(Color.WHITE);
                precio.setBackground(getApplication().getResources().getDrawable(R.drawable.ic_button_precio));

                precio.setText("Bs." + cel.getString("precio") + " ");


                row.addView(fecha, newTableRowParams());
                row.addView(hora, newTableRowParams());
                row.addView(precio, newTableRowParams());
                tableLayout.addView(row);

                total += cel.getInt("precio");

            }
            tvtotal.setText("Total: Bs. " + total);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private TableRow.LayoutParams newTableRowParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(0, 0, 0, 15);
        params.weight = 1;
        return params;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirmar:
                Intent intent = new Intent(this, MetodoDePago.class);
                intent.putExtra("obj", obj.toString());
                startActivity(intent);

        }
    }


}
