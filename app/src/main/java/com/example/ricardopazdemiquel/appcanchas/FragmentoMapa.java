package com.example.ricardopazdemiquel.appcanchas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchas2;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.example.ricardopazdemiquel.appcanchas.dialog.finalizar_reserva_dialog;
import com.example.ricardopazdemiquel.appcanchas.dialog.map_reserva_dialog;
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
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import complementos.Contexto;


public class FragmentoMapa extends Fragment implements View.OnClickListener {
    private JSONArray arr_canchas;

    MapView mMapView;
    private GoogleMap googleMap;
    private Button btn_buscar;
    private LinearLayout btn_select_hora;
    private LinearLayout btn_select_fecha;
    private TextView text_fecha;
    private TextView text_hora;
    private String fecha_actual;
    private String hora_actual;

    public FragmentoMapa() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragmento_mapa, container, false);

        mMapView = view.findViewById(R.id.mapView);
        btn_buscar = view.findViewById(R.id.btn_buscar);
        btn_select_hora = view.findViewById(R.id.btn_select_hora);
        btn_select_fecha = view.findViewById(R.id.btn_select_fecha);
        text_fecha = view.findViewById(R.id.text_fecha);
        text_hora = view.findViewById(R.id.text_hora);

        btn_select_hora.setOnClickListener(this);
        btn_select_fecha.setOnClickListener(this);
        btn_buscar.setOnClickListener(this);

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
                arr_canchas = ((Main2Activity) getActivity()).get_complejos();
                if (arr_canchas != null) {
                    for (int i = 0; i < arr_canchas.length(); i++) {
                        try {
                            obj = arr_canchas.getJSONObject(i);
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pelota);
                            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(obj.getDouble("LAT"), obj.getDouble("LNG")))
                                    .title(obj.getString("NOMBRE")).icon(icon));
                            marker.setTag(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            JSONObject obj = (JSONObject) marker.getTag();
                            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                            new map_reserva_dialog(obj).show(fragmentManager, "Dialog");
                            return false;
                        }
                    });

                    /*AdapterInfoWinfow adapterInfoWinfow = new AdapterInfoWinfow(getActivity());
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

                        }]

                    });*/
                }

            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buscar:
                Intent intent = new Intent(getActivity(), buscar.class);
                startActivity(intent);
                break;
            case R.id.btn_select_fecha:
                dialogDatePickerLight((LinearLayout) view);
                break;
            case R.id.btn_select_hora:
                dialogTimePickerLight((LinearLayout) view);
                break;
        }
    }

    private void dialogDatePickerLight(final LinearLayout bt) {
        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePicker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
                        fecha_actual = fecha.format(calendar.getTime());
                        text_fecha.setText(fecha_actual);
                        verificar();
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


    private void dialogTimePickerLight(final LinearLayout bt) {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                if (minute < 10) {
                    String aux = "0" + minute;
                    hora_actual = hourOfDay + ":" + aux;
                    text_hora.setText(hora_actual);
                    verificar();
                } else {
                    hora_actual = hourOfDay + ":" + minute;
                    text_hora.setText(hora_actual);
                    verificar();
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    private void verificar(){

        String fecha = text_fecha.getText().toString().trim();
        String hora = text_hora.getText().toString().trim();

        boolean isValid = true;

        if (fecha.isEmpty()) {
            text_fecha.setError("campo obligatorio");
            isValid = false;
        }
        if (hora.isEmpty()) {
            text_hora.setError("campo obligatorio");
            isValid = false;
        }

        if (!isValid) {
            return;
        }else{
            text_fecha.setError(null);
            text_hora.setError(null);
            new buscar_complejos_filtros().execute();
        }

    }


    private class buscar_complejos_filtros extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;

        public buscar_complejos_filtros() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(getActivity());
            progreso.setIndeterminate(true);
            progreso.setTitle("obteniendo datos");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "get_complejos_fecha_hora");
            parametros.put("fecha", fecha_actual);
            parametros.put("hora", hora_actual);
            String respuesta = "";
            try {
                respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_android), MethodType.POST, parametros));
            } catch (Exception ex) {
                Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            progreso.dismiss();
            if (resp == null) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.isEmpty()) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.equals("falso")) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray arr = new JSONArray(resp);
                    arr_canchas = arr;
                    if (arr_canchas != null) {
                        for (int i = 0; i < arr_canchas.length(); i++) {
                            JSONObject obj;
                            obj = arr_canchas.getJSONObject(i);
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pelota);
                            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(obj.getDouble("LAT"), obj.getDouble("LNG")))
                                    .title(obj.getString("NOMBRE")).icon(icon));
                            marker.setTag(obj);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

    }


}
