package com.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ricardopazdemiquel.appcanchas.R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Complejo_map_activity extends AppCompatActivity {

    private JSONObject obj;
    MapView mMapView;
    private GoogleMap googleMap;
    private Double lat, lng;
    private String nombre;

    public Complejo_map_activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complejo_mapa);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icon_left_arrow);

        mMapView = findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        MapsInitializer.initialize(getApplicationContext());

        String object = getIntent().getStringExtra("obj");
        try {
            obj = new JSONObject(object);
            lat = obj.getDouble("LAT");
            lng = obj.getDouble("LNG");
            nombre = obj.getString("NOMBRE");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(-17.7848688, -63.180835), 15);
                googleMap.animateCamera(cu);

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pelota);
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(nombre).icon(icon));
                marker.setTag(obj);
            }
        });
    }


    // Opcion para ir atras sin reiniciar en la actividad anterior de nuevo
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
