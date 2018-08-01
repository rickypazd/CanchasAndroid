package com.example.ricardopazdemiquel.appcanchas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentoMapa extends Fragment {
    private JSONArray arr_canchas;

    MapView mMapView;
    private GoogleMap googleMap;
    private Button btn_buscar;

    public FragmentoMapa() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragmento_mapa, container, false);
        mMapView = view.findViewById(R.id.mapView);
        btn_buscar = view.findViewById(R.id.btn_buscar);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), buscar.class);
                startActivity(intent);
            }
        });
        try {
            arr_canchas = ((MainActivity) getActivity()).getArr_canchas();


        } catch (Exception e) {

        }
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(-17.7848688, -63.180835), 15);
                googleMap.animateCamera(cu);

                JSONObject obj;
                if (arr_canchas != null) {


                    for (int i = 0; i < arr_canchas.length(); i++) {
                        try {
                            obj = arr_canchas.getJSONObject(i);
                            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(obj.getDouble("LAT"), obj.getDouble("LNG")))
                                    .title(obj.getString("NOMBRE")));
                            marker.setTag(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    AdapterInfoWinfow adapterInfoWinfow = new AdapterInfoWinfow(getActivity());
                    mMap.setInfoWindowAdapter(adapterInfoWinfow);
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            JSONObject obj = (JSONObject) marker.getTag();
                            try {
                                Intent inten = new Intent(getActivity(), detalleCancha.class);
                                inten.putExtra("id_complejo", obj.getInt("ID"));
                                getActivity().startActivity(inten);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });
                }

            }
        });

        return view;
    }


}
