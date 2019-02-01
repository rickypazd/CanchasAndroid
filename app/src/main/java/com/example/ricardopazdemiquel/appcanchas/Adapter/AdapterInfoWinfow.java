package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.detalleCancha;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class AdapterInfoWinfow implements GoogleMap.InfoWindowAdapter, View.OnClickListener {

    private Activity context;
    private JSONObject obj;
    private Button btn_reservar;
    public TextView tvNombre;
    public TextView tvDireccion;
    public TextView tvNumero;
    public TextView tvCorreo;
    private Context contexto;

    public AdapterInfoWinfow(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.map_reserva_dialog, null);
        obj = (JSONObject) marker.getTag();
        tvNombre = view.findViewById(R.id.tvNombre);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvNumero = view.findViewById(R.id.tvNumero);
        tvCorreo = view.findViewById(R.id.tvCorreo);
        btn_reservar = view.findViewById(R.id.btn_reservar);

        try {
            tvNombre.setText(obj.getString("NOMBRE"));
            tvDireccion.setText(obj.getString("DIRECCION"));
            tvNumero.setText(obj.getString("TELEFONO"));
            tvCorreo.setText(obj.getString("CORREO"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_reservar.setOnClickListener(this);

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reservar) {
            Fragment fragmentoGenerico = null;
            FragmentManager fragmentManager = ((AppCompatActivity)contexto).getSupportFragmentManager();
            try {
                fragmentoGenerico = new detalleCancha(obj.getInt("ID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (fragmentoGenerico != null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();
            }
        }
    }

}
