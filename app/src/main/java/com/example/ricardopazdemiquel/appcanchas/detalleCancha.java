package com.example.ricardopazdemiquel.appcanchas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;

import complementos.Contexto;

public class detalleCancha extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener{


    private SliderLayout mDemoSlider;
    private CardView cardview;
    private ScrollView scrollView;
    private int id_complejo;
    private JSONObject complejo;
    private TextView tv_nombre_cancha;
    private JSONObject obj_complejo;
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (fragmento) {
            case "detalle":
                fragmentoGenerico = new FragmentoInfo();
                break;

            case "horario":
                fragmentoGenerico = new FragmentoHorario();
                break;
            case "comentarios":
                fragmentoGenerico = new FragmentoComentarios();
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().
                    replace(R.id.contenDetalle, fragmentoGenerico)
                    .commit();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cancha);
        cardview=findViewById(R.id.contenDetalle);
        scrollView=findViewById(R.id.scrollviw);
        tv_nombre_cancha=findViewById(R.id.tv_nombre_cancha);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        id_complejo=getIntent().getIntExtra("id_complejo",0);

        new CargarListaTask(id_complejo).execute();


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    public void rezize_fragment(int heigh){
       cardview.setMinimumHeight(heigh);

    }
    public JSONObject getComplejo(){
      return obj_complejo;
    }

    private class CargarListaTask extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private int id;

        public CargarListaTask(int id){
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(detalleCancha.this);
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
                Toast.makeText(detalleCancha.this,"Error al obtener Datos" ,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject obj = new JSONObject(resp);
                obj_complejo=obj;
                seleccionarFragmento("detalle");
                tv_nombre_cancha.setText(obj.getString("NOMBRE"));
                setTitle(obj.getString("NOMBRE"));
                JSONArray arr_carrusel=obj.getJSONArray("FOTOS_CARRUSEL");
                JSONObject object;
                HashMap<String,String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < arr_carrusel.length(); i++) {
                    object=arr_carrusel.getJSONObject(i);
                    url_maps.put(""+i, getString(R.string.url_foto)+object.getString("FOTO"));
                }
                for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(detalleCancha.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(detalleCancha.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);

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

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

    }

}
