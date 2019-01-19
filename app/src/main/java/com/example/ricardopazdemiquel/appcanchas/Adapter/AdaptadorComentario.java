package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdaptadorComentario extends BaseAdapter {

    private JSONArray listaHistory;
    private Context contexto;

    public AdaptadorComentario(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.listaHistory = lista;
    }

    @Override
    public int getCount() {
        return listaHistory.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return listaHistory.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) { return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(contexto)
                    .inflate(R.layout.layout_item_comentario, viewGroup, false);
        }

        TextView text_nombre = view.findViewById(R.id.text_nombre);
        RatingBar ratingBar= view.findViewById(R.id.ratingBar);
        TextView text_comentario = view.findViewById(R.id.text_comentario);
        ProfilePictureView fb = view.findViewById(R.id.fb_imagenPerfil);

        try {
            final JSONObject cancha = listaHistory.getJSONObject(i);
            //imgCancha.setImageResource(cancha.getImagen());
            //ratingBar.setText(cancha.getString("CLACIFICACION"));
            ratingBar.setRating((float) cancha.getDouble("CLASIFICACION"));
            text_comentario.setText(cancha.getString("COMENTARIO"));
            text_nombre.setText(cancha.getString("NOMBRE"));
            String usuario = cancha.getString("ID_USR");
            fb.setProfileId(usuario);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

}
