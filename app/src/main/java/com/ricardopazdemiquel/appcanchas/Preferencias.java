package com.ricardopazdemiquel.appcanchas;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.ricardopazdemiquel.appcanchas.Utiles.Token;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;


public class Preferencias extends Fragment implements View.OnClickListener {


    private LinearLayout liner_ver_perfil;
    private LinearLayout liner_sign_out;
    private TextView text_nombre;
    private TextView text_apellidos;
    private com.mikhaellopez.circularimageview.CircularImageView img_photo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_preferencias, container, false);

        liner_ver_perfil = view.findViewById(R.id.liner_ver_perfil);
        liner_sign_out = view.findViewById(R.id.liner_sign_out);
        text_nombre = view.findViewById(R.id.text_nombre);
        text_apellidos = view.findViewById(R.id.text_apellidos);
        img_photo = view.findViewById(R.id.img_photo);
        liner_sign_out.setOnClickListener(this);
        liner_ver_perfil.setOnClickListener(this);

        final JSONObject usr_log = SPref.getUsr_log(getActivity());
        if (usr_log != null) {
            try {
                if (usr_log.getString("ID_FACE").length() > 0) {
                    String nombre = usr_log.getString("NOMBRE");
                    String apellido_pa = usr_log.getString("APELLIDO");
                    text_nombre.setText(nombre);
                    text_apellidos.setText(apellido_pa);
                    String id_face = usr_log.getString("ID_FACE");
                    String url = "https://graph.facebook.com/" + id_face + "/picture?type=large";
                    new AsyncTaskLoadImage(img_photo).execute(url);
                }
                if (usr_log.getString("id_gmail").length() > 0) {
                    //cargar_img_gmail
                    String url = "https://pikmail.herokuapp.com/" + usr_log.getString("id_gmail") + "?size=100";
                    new AsyncTaskLoadImage(img_photo).execute(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }


    @Override
    public void onClick(View view) {
        JSONObject usr = SPref.getUsr_log(getActivity());
        switch (view.getId()) {
            case R.id.liner_sign_out:
                SharedPreferences preferencias = getActivity().getSharedPreferences("myPref", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.remove("usr_log");
                editor.commit();
                try {
                    new Desconectarse(usr.getInt("id"), Token.currentToken).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getActivity(), login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    public class AsyncTaskLoadImage extends AsyncTask<String, String, Bitmap> {
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
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
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

    private class Desconectarse extends AsyncTask<Void, String, String> {
        private int id;
        private String nombre;

        public Desconectarse(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> param = new Hashtable<>();
            param.put("evento", "desconectarse");
            param.put("id", id + "");
            param.put("token", nombre);
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, param));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String pacientes) {
            super.onPostExecute(pacientes);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}
