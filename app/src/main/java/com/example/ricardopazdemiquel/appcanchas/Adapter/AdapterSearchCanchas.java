package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*import com.google.gson.Gson;
import com.material.components.R;*/

public class AdapterSearchCanchas extends RecyclerView.Adapter<AdapterSearchCanchas.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private JSONArray arrayCanchas;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterSearchCanchas(Context context, JSONArray arr) {
        this.arrayCanchas = arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final int pos = position;
            final JSONObject cancha = arrayCanchas.getJSONObject(pos);
            final String aux = cancha.getString("NOMBRE");
            holder.title.setText(aux);
            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    onItemClickListener.onItemClick(v, aux, pos);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setCountries(JSONArray arr) {
        this.arrayCanchas = arr;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayCanchas.length();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String viewModel, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

}