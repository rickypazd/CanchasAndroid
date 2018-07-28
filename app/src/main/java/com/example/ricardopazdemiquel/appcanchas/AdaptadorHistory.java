package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AdaptadorHistory extends BaseAdapter {

    private JSONArray listaHistory;
    private Context contexto;

    public AdaptadorHistory(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.listaHistory = lista;
    }

    @Override
    public int getCount() {
        return listaHistory.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return listaHistory.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
     return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(contexto)
                    .inflate(R.layout.layout_iten_historireseva, viewGroup, false);
        }


        TextView tvNombreComplejo = view.findViewById(R.id.tv_nombrecomplejo);
        TextView tvNombreCanchas = view.findViewById(R.id.tv_nombrecancha);
        TextView tvfecha = view.findViewById(R.id.tv_fecha);
        TextView tvTotal = view.findViewById(R.id.tv_total);
        TextView tvTotalHoras= view.findViewById(R.id.tv_total_horas);
        TextView tvEstado = view.findViewById(R.id.tv_estado);

        try {
            final JSONObject cancha = listaHistory.getJSONObject(i);
            //imgCancha.setImageResource(cancha.getImagen());
            tvNombreComplejo.setText(cancha.getString("NOMBRE_COMP"));
            tvNombreCanchas.setText(cancha.getString("NOMBRE_CAN"));
            tvfecha.setText(cancha.getString("FECHA"));
            tvTotal.setText("Total: "+cancha.getString("TOTAL"));
            tvTotalHoras.setText("Horas: "+cancha.getString("HORAS"));
            tvEstado.setText(getEstado(cancha.getInt("ESTADO")));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }
    private String getEstado(int estado){
        switch (estado){
            case 1:
                return "PENDIENTE";
            case 2:
                return "CONFIRMADO";
            case 3:
                return "CANCELADO";
        }
        return "";
    }
}