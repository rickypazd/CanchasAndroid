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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AdaptadorCanchas extends BaseAdapter {

    private JSONArray listaCanchas;
    private Context contexto;

    public AdaptadorCanchas( Context contexto,JSONArray lista) {
        this.contexto = contexto;
        this.listaCanchas = lista;
    }

    @Override
    public int getCount() {
        return listaCanchas.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return listaCanchas.get(i);
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
                    .inflate(R.layout.layout_item_cancha, viewGroup, false);
        }

        ImageView imgCancha = view.findViewById(R.id.imgCancha);
        TextView tvNombre = view.findViewById(R.id.tvNombre);
        TextView tvValoracion = view.findViewById(R.id.tvValoracion);
        TextView tvCiudad = view.findViewById(R.id.tvCiudad);
        TextView tvDireccion = view.findViewById(R.id.tvDireccion);
        TextView tvDescripcion = view.findViewById(R.id.tvDescripcion);
        TextView tvNumero = view.findViewById(R.id.tvNumero);
        TextView tvCorreo = view.findViewById(R.id.tvCorreo);
        Button btn_ver = view.findViewById(R.id.btn_ver_complejo);
        try {
            final JSONObject cancha = listaCanchas.getJSONObject(i);
            //imgCancha.setImageResource(cancha.getImagen());
            URL url = null;
            if(cancha.getString("B64").length()>0){
                new AsyncTaskLoadImage(imgCancha).execute(contexto.getResources().getString(R.string.url_foto)+cancha.getString("B64"));
            }

            tvNombre.setText(cancha.getString("NOMBRE"));
            tvValoracion.setText("10 K");
            tvCiudad.setText("Santa Cruz De La Cierra");
            tvDireccion.setText(cancha.getString("DIRECCION"));
            tvDescripcion.setText(Html.fromHtml(cancha.getString("PRESENTACION")));
            tvNumero.setText(cancha.getJSONArray("TELEFONOS").getJSONObject(0).getString("TELEFONO"));
            tvCorreo.setText(cancha.getJSONArray("CORREOS").getJSONObject(0).getString("CORREO"));
            btn_ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(contexto,detalleCancha.class);
                    try {
                        inten.putExtra("id_complejo",cancha.getInt("ID"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    contexto.startActivity(inten);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }
    public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;
        public AsyncTaskLoadImage(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}