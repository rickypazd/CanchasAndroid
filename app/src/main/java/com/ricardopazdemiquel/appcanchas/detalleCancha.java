package com.ricardopazdemiquel.appcanchas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.ricardopazdemiquel.appcanchas.dialog.map_reserva_dialog;
import com.ricardopazdemiquel.appcanchas.dialog.tipos_de_cancha_dialog;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;

import complementos.Contexto;

@SuppressLint("ValidFragment")
public class detalleCancha extends Fragment implements BaseSliderView.OnSliderClickListener, View.OnClickListener {


    private SliderLayout mDemoSlider;
    private CardView cardview;
    private ScrollView scrollView;
    private int id_complejo;
    private JSONObject complejo;
    //private TextView tv_nombre_cancha;
    private JSONObject obj_complejo;
    private Button reservar,btn_ver_mapa;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_info: // Buscar
                    seleccionarFragmento("detalle");
                    return true;
                case R.id.navigation_horarios: // Lista de canchas
                    seleccionarFragmento("horario");
                    return true;
                case R.id.navigation_comentarios: // Filtro
                    seleccionarFragmento("comentarios");
                    return true;
            }

            return false;
        }
    };

    private void seleccionarFragmento(String fragmento) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (fragmento) {
            case "detalle":
                fragmentoGenerico = new FragmentoInfo(getComplejo());
                break;
            case "horario":
                fragmentoGenerico = new FragmentoHorario(getComplejo());
                break;
            case "comentarios":
                fragmentoGenerico = new FragmentoComentarios(getComplejo());
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().
                    replace(R.id.contenDetalle, fragmentoGenerico)
                    .commit();
        }
    }

    @SuppressLint("ValidFragment")
    public detalleCancha(int id) {
        this.id_complejo = id;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detalle_cancha, container, false);

        //id_complejo = getActivity().getIntent().getIntExtra("id_complejo", 0);
        new CargarListaTask(id_complejo).execute();
        cardview = view.findViewById(R.id.contenDetalle);
        scrollView = view.findViewById(R.id.scrollviw);
        //tv_nombre_cancha=findViewById(R.id.tv_nombre_cancha);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);

        reservar = view.findViewById(R.id.btnReservar);
        btn_ver_mapa = view.findViewById(R.id.btn_ver_mapa);

        reservar.setOnClickListener(this);
        btn_ver_mapa.setOnClickListener(this);

        return view;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cancha);
        cardview = findViewById(R.id.contenDetalle);
        scrollView = findViewById(R.id.scrollviw);
        //tv_nombre_cancha=findViewById(R.id.tv_nombre_cancha);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        id_complejo = getIntent().getIntExtra("id_complejo", 0);
        reservar = findViewById(R.id.btnReservar);
        reservar.setOnClickListener(this);
        new CargarListaTask(id_complejo).execute();
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReservar:
                Intent intent = new Intent(getActivity(), TablaReserva_cancha.class);
                intent.putExtra("obj", obj_complejo.toString());
                startActivity(intent);
                /*android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                new tipos_de_cancha_dialog(obj_complejo).show(fragmentManager, "Dialog");
                break;*/
                break;
            case R.id.btn_ver_mapa:
                Intent intent2 = new Intent(getActivity(), Complejo_map_activity.class);
                intent2.putExtra("obj", obj_complejo.toString());
                startActivity(intent2);
                /*android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                new tipos_de_cancha_dialog(obj_complejo).show(fragmentManager, "Dialog");
                break;*/
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    public void rezize_fragment(int heigh) {
        cardview.setMinimumHeight(heigh);

    }

    public JSONObject getComplejo() {
        return obj_complejo;
    }

    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private int id;

        public CargarListaTask(int id) {
            this.id = id;
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
            parametros.put("evento", "get_complejos_id");
            parametros.put("id", id + "");
            String respuesta = "";
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
            if (resp == null) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else if (resp.isEmpty()) {
                Toast.makeText(getActivity(), "Error al obtener Datos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(resp);
                    obj_complejo = obj;
                    seleccionarFragmento("detalle");
                    //tv_nombre_cancha.setText(obj.getString("NOMBRE"));
                    //setTitle(obj.getString("NOMBRE"));
                    JSONArray arr_carrusel = obj.getJSONArray("FOTOS_CARRUSEL");
                    JSONObject object;
                    HashMap<String, String> url_maps = new HashMap<String, String>();
                    for (int i = 0; i < arr_carrusel.length(); i++) {
                        object = arr_carrusel.getJSONObject(i);
                        url_maps.put("" + i, getString(R.string.url_foto) + object.getString("FOTO"));
                    }
                    for (String name : url_maps.keySet()) {
                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(url_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(detalleCancha.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);

                        mDemoSlider.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(4000);

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
