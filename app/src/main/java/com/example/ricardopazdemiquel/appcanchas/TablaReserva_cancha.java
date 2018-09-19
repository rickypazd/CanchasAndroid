package com.example.ricardopazdemiquel.appcanchas;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import complementos.Contexto;
import complementos.TablaDynamic;
import complementos.Tools;
import complementos.infoCelda;

public class TablaReserva_cancha extends AppCompatActivity {

    private TableLayout tableLayout;
    private TablaDynamic tablaDynamic;
    private Calendar domingo_actual;
    private Calendar domingo_inicio;
    private ImageView btn_atras;
    private ImageView btn_adelante;
    private Button btn_confirmar;
    private Button btn_select_hora;
    private TextView tv_horas;
    private TextView tv_total;
    private TextView text_dia;
    private TextView text_año;
    private ImageView iv_about;
    Point p;
    JSONObject objcomplejo;
    private Spinner SpinnerCanchas;
    private int nivel;
    private TextView text_fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_reserva_cancha);
        tableLayout=findViewById(R.id.tablalayout);
        tableLayout.setBackgroundColor(Color.BLACK);
        iv_about=findViewById(R.id.iv_about);
        btn_atras=findViewById(R.id.btn_atras);
        btn_adelante=findViewById(R.id.btn_adelante);
        btn_confirmar=findViewById(R.id.btn_confirmar);
        btn_select_hora=findViewById(R.id.btn_select_hora);
        text_fecha = findViewById(R.id.text_fecha);
        text_dia = findViewById(R.id.text_dia);
        text_año = findViewById(R.id.text_año);
        tv_horas=findViewById(R.id.tv_horas);
        tv_total=findViewById(R.id.tv_total);
        SpinnerCanchas = findViewById(R.id.Spinner_canchas);

            String obj = getIntent().getStringExtra("obj");
        try {
            objcomplejo = new JSONObject(obj);
            JSONArray jsonArray = objcomplejo.getJSONArray("CANCHAS");
            agregarSpinnerCanchas(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //recibe id cancha
        iv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p != null) {
                    showPopup(TablaReserva_cancha.this,p);
                }

            }
        });
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal =(Calendar)domingo_actual.clone();
                cal.add(Calendar.DAY_OF_WEEK,-7);
                if(cal.after(domingo_inicio) || cal.equals(domingo_inicio)){
                    domingo_actual=(Calendar)cal.clone();
                    tablaDynamic = new TablaDynamic(tableLayout,getApplicationContext(),tv_horas,tv_total);
                    tableLayout.removeAllViews();
                    new getHorasAsyn(1).execute();
                }

            }
        });
        btn_adelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domingo_actual.add(Calendar.DAY_OF_WEEK,7);
                tablaDynamic = new TablaDynamic(tableLayout,getApplicationContext(),tv_horas,tv_total);
                tableLayout.removeAllViews();
                new getHorasAsyn(1).execute();
            }
        });

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tablaDynamic.getAhReservar().size()>0){
                    Intent intent = new Intent(TablaReserva_cancha.this,detalle_reserva.class);
                    intent.putExtra("arr_reservas",tablaDynamic.getAhReservar());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(TablaReserva_cancha.this,"Deve seleccionar su reserva" ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_select_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePickerLight((Button) v);
            }
        });

    }

    private void dialogDatePickerLight(final Button bt) {
        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePicker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.get(Calendar.DAY_OF_MONTH);
                        long date_ship_millis2 = calendar.get(Calendar.YEAR);
                        text_fecha.setText(date_ship_millis+"");
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                        String dayOfTheWeek =sdf.format( calendar.getTime());
                        text_dia.setText(dayOfTheWeek+"");
                        text_año.setText(date_ship_millis2+"");
                        Toast.makeText(TablaReserva_cancha.this,dayOfTheWeek+"" + date_ship_millis2,Toast.LENGTH_LONG).show();
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorAccent));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    private void agregarSpinnerCanchas(JSONArray jsonArray){
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,jsonArray);
        SpinnerCanchas.setAdapter(spinnerAdapter);
        SpinnerCanchas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                nivel = position;
                int i = (int) parent.getSelectedItemId();
                tableLayout.removeAllViews();
                tv_horas.setText("0");
                tv_total.setText("0");

                tablaDynamic = new TablaDynamic(tableLayout,getApplicationContext(),tv_horas,tv_total);
                Calendar fechaActual= Calendar.getInstance();
                 fechaActual.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                domingo_actual=(Calendar) fechaActual.clone();
                domingo_inicio=(Calendar) fechaActual.clone();
                new getHorasAsyn(i).execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];


        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        iv_about.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    private void showPopup(final Activity context, Point p) {
        int popupWidth = 400;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        ConstraintLayout viewGroup = (ConstraintLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popupabout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 200;
        int OFFSET_Y = 120;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.

    }


    private class getHorasAsyn extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private int id;

        public getHorasAsyn(int id){
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(TablaReserva_cancha.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("obteniendo datos");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("por favor espere...");
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "get_dias_activos");
            parametros.put("fe_in", getDomingoActual());
            parametros.put("fe_fin", getDomingoSiguiente());
            parametros.put("id", id+"");

            String respuesta="";
            try {
                respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, parametros));
            } catch (Exception ex) {
                Log.e(Contexto.APP_TAG, "Hubo un error al cargar la lista");
            }

            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            progreso.dismiss();
            if(resp ==""){
                Toast.makeText(TablaReserva_cancha.this,"Error al obtener Datos" ,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                ArrayList<infoCelda> header = new ArrayList<>();
           JSONObject obje = new JSONObject(resp);
           JSONArray arrdia = obje.getJSONArray("dias");
           JSONArray arrhoras = obje.getJSONArray("horas");
           JSONArray arrcostos = obje.getJSONArray("costos");
           JSONArray arrreservas = obje.getJSONArray("reservas");
           infoCelda cel = new infoCelda();
           cel.setTipo(1);
           cel.setTexto("HORAS");
           header.add(cel);
           JSONObject obj;
           int dia;
                for (int i = 0; i < arrdia.length(); i++) {
                    obj=arrdia.getJSONObject(i);
                    cel=new infoCelda();
                    cel.setTipo(2);
                    dia=obj.getInt("DIA");
                    cel.setDia(dia);
                    cel.setFecha(getFechaDia(dia));
                    String fehca=cel.getFecha();
                    cel.setTexto(fehca.substring(0,fehca.length()-3));
                    header.add(cel);
                }

                tablaDynamic.addHeader(header);
                tablaDynamic.createHeaderdia();
                JSONObject objtemp;
                ArrayList<infoCelda[]> data = new ArrayList<>();
                JSONObject cos;
                infoCelda celda,icaux;
                    for (int i = 0; i <arrhoras.length() ; i++) {
                        cos=arrhoras.getJSONObject(i);
                        String horas =cos.getString("HORA");
                        infoCelda[] celdas = new infoCelda[header.size()];
                        for (int j = 0; j < header.size() ; j++) {
                            celda = new infoCelda();
                            if(j==0){
                                celda.setTipo(3);
                                celda.setHora(horas);
                                celda.setTexto(horas.substring(0,5));
                            }else{
                                icaux=header.get(j);
                                celda.setTipo(4);
                                celda.setAbierto(0);
                                celda.setHora(horas);
                                celda.setDia(icaux.getDia());
                                celda.setFecha(icaux.getFecha());
                                for (int k = 0; k < arrcostos.length(); k++) {
                                    objtemp=arrcostos.getJSONObject(k);
                                    if(objtemp.getString("HORA").equals(celda.getHora()) && objtemp.getInt("DIA")==celda.getDia()){
                                        celda.setAbierto(1);
                                        celda.setCosto(objtemp.getInt("PRECIO"));
                                        celda.setTexto(celda.getCosto()+"");
                                        break;
                                    }
                                }
                                for (int k = 0; k <arrreservas.length() ; k++) {
                                    objtemp=arrreservas.getJSONObject(k);
                                    if(objtemp.getString("FECHA").equals(celda.getFechaHora())){
                                        celda.setEstado(objtemp.getInt("ESTADO"));
                                        break;
                                    }
                                }

                            }
                            celdas[j]=celda;
                        }
                    data.add(celdas);
                }
                tablaDynamic.addData(data);
                tablaDynamic.createDataTable();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

    }

    private String getFechaDia(int dia){
        Calendar cal = (Calendar) domingo_actual.clone();
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy");
        switch (dia){
            case 0:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                return form.format(cal.getTime());
            case 1:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                return form.format(cal.getTime());
            case 2:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                return form.format(cal.getTime());
            case 3:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                return form.format(cal.getTime());
            case 4:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                return form.format(cal.getTime());
            case 5:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                return form.format(cal.getTime());
            case 6:
                cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                return form.format(cal.getTime());
        }
        return "";
    }
    private SimpleDateFormat formFechaConsular= new SimpleDateFormat("MM-dd-yyyy");
    private String getDomingoActual(){
           Calendar cal = (Calendar) domingo_actual.clone();
           cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            cal.add(Calendar.DATE,-7);
           return formFechaConsular.format(cal.getTime());
    }

    private String getDomingoSiguiente(){
        Calendar cal = (Calendar) domingo_actual.clone();
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return formFechaConsular.format(cal.getTime());
    }
}
