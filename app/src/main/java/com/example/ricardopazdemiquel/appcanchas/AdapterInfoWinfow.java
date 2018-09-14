package com.example.ricardopazdemiquel.appcanchas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class AdapterInfoWinfow implements GoogleMap.InfoWindowAdapter{

    private Activity context;
    private JSONObject obj;
    private TextView tv_nombre;
    private Button btn_ver;
    private TextView tv_desc;
    private ImageView imageView;
    public AdapterInfoWinfow(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker)
    {
        View view = context.getLayoutInflater().inflate(R.layout.infowindow,null);
        obj= (JSONObject)marker.getTag();
        tv_nombre=view.findViewById(R.id.tv_info_nombre);
        tv_desc=view.findViewById(R.id.tv_info_desc);
        btn_ver=view.findViewById(R.id.btn_info_ver);

        try {
            tv_nombre.setText(obj.getString("NOMBRE"));
            tv_desc.setText(Html.fromHtml(obj.getString("PRESENTACION")));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent inten = new Intent(context,detalleCancha.class);
                    inten.putExtra("id_complejo",obj.getInt("ID"));
                    context.startActivity(inten);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
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
