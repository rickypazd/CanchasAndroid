package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdaptadorCanchasComplejo extends RecyclerView.Adapter<viewHolder> {

    private JSONArray listaCanchas;
    private Context contexto;

    public AdaptadorCanchasComplejo(JSONArray listaCanchas, Context contexto) {
        this.listaCanchas = listaCanchas;
        this.contexto = contexto;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_iten_cancha_complejo, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        try {
            JSONObject obj =listaCanchas.getJSONObject(position);
            holder.nombre.setText(obj.getString("NOMBRE"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listaCanchas.length();
    }
}
