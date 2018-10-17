package com.example.ricardopazdemiquel.appcanchas.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Adapter.AdaptadorHistory;
import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Hashtable;

import complementos.Contexto;
import complementos.infoCelda;


public class Fragment_proximas_reservas extends Fragment {

    private ListView lv;

    private ArrayList<infoCelda> arr;
    private TableLayout tableLayout;

    public Fragment_proximas_reservas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_proximas_reservas, container, false);
        //lv=view.findViewById(R.id.list_history);
        //new CargarListaTask().execute();

       /* arr = (ArrayList<infoCelda>) getActivity().getIntent().getSerializableExtra("arr_reservas");
        tableLayout= view.findViewById(R.id.table_layout);
        tableLayout.setBackgroundColor(Color.TRANSPARENT);*/
        return view;
}

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(0,0,0,15);
        params.weight=1;
        return params;
    }


    private void cargarCanchaReservas(){

        TableRow header = new TableRow(getActivity());
        header.setBackgroundColor(Color.TRANSPARENT);

        TextView celfecha=new TextView(getActivity());
        celfecha.setGravity(Gravity.CENTER);
        celfecha.setTextSize(18);
        celfecha.setTextColor(getActivity().getApplication().getResources().getColor(R.color.colorAccent));
        celfecha.setText("FECHA");

        TextView celcomplejo=new TextView(getActivity());
        celcomplejo.setGravity(Gravity.CENTER);
        celcomplejo.setTextSize(18);
        celcomplejo.setTextColor(getActivity().getApplication().getResources().getColor(R.color.colorAccent));
        celcomplejo.setText("COMPLEJO");

        TextView celhora=new TextView(getActivity());
        celhora.setGravity(Gravity.CENTER);
        celhora.setTextSize(18);
        celhora.setTextColor(getActivity().getApplication().getResources().getColor(R.color.colorAccent));
        celhora.setText("HORA");

        TextView celestado=new TextView(getActivity());
        celestado.setGravity(Gravity.CENTER);
        celestado.setTextSize(18);
        celestado.setTextColor(getActivity().getApplication().getResources().getColor(R.color.colorAccent));
        celestado.setText("ESTADO");

        header.addView(celfecha,newTableRowParams());
        header.addView(celcomplejo,newTableRowParams());
        header.addView(celhora,newTableRowParams());
        header.addView(celestado,newTableRowParams());
        tableLayout.addView(header);

        infoCelda cel;
        TableRow row;
        TextView fecha;
        TextView complejo;
        TextView hora;
        TextView estado;

        for (int i = 0; i <arr.size() ; i++) {
            cel=arr.get(i);
            row= new TableRow(getActivity());

            fecha=new TextView(getActivity());
            fecha.setGravity(Gravity.CENTER);
            fecha.setTextSize(15);
            fecha.setPadding(0,10,0,10);
            fecha.setTextColor(Color.BLACK);
            fecha.setBackground(getActivity().getApplication().getResources().getDrawable(R.drawable.ic_button_fecha));
            fecha.setText(cel.getFecha());

            complejo=new TextView(getActivity());
            complejo.setGravity(Gravity.CENTER);
            complejo.setPadding(0,10,0,10);
            complejo.setTextSize(15);
            complejo.setTextColor(Color.WHITE);
            complejo.setBackground(getActivity().getApplication().getResources().getDrawable(R.drawable.ic_button_precio));
            complejo.setText("Bs."+cel.getCosto()+" ");

            hora=new TextView(getActivity());
            hora.setGravity(Gravity.CENTER);
            hora.setTextSize(15);
            hora.setPadding(0,10,0,10);
            hora.setTextColor(Color.BLACK);
            hora.setBackground(getActivity().getApplication().getResources().getDrawable(R.drawable.ic_button_hora));
            hora.setText(cel.getHora());

            estado=new TextView(getActivity());
            estado.setGravity(Gravity.CENTER);
            estado.setTextSize(15);
            estado.setPadding(0,10,0,10);
            estado.setTextColor(Color.BLACK);
            estado.setBackground(getActivity().getApplication().getResources().getDrawable(R.drawable.ic_button_hora));
            estado.setText(cel.getHora());


            row.addView(fecha,newTableRowParams());
            row.addView(complejo,newTableRowParams());
            row.addView(hora,newTableRowParams());
            row.addView(estado,newTableRowParams());
            tableLayout.addView(row);
        }
    }


    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        public CargarListaTask(){
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
            parametros.put("evento", "get_mis_reservas");
            parametros.put("id", AccessToken.getCurrentAccessToken().getUserId());
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
            if(resp == null){
                Toast.makeText(getActivity(),"Error al obtener Datos",Toast.LENGTH_SHORT).show();
            }else if(resp.isEmpty()){
                Toast.makeText(getActivity(),"Error al obtener Datos",Toast.LENGTH_SHORT).show();
            }else {
                try {
                    JSONArray arr = new JSONArray(resp);
                    AdaptadorHistory history = new AdaptadorHistory(getActivity(), arr);
                    lv.setAdapter(history);
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
