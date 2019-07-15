package com.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ricardopazdemiquel.appcanchas.R;
import com.ricardopazdemiquel.appcanchas.TablaReserva_cancha;
import com.ricardopazdemiquel.appcanchas.detalleCancha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter_tipo_canchas extends BaseAdapter {

    private JSONArray tipos_canchas;
    private Context contexto;

    public Adapter_tipo_canchas() {
    }

    public Adapter_tipo_canchas(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.tipos_canchas = lista;
    }

    @Override
    public int getCount() {
        return tipos_canchas.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return tipos_canchas.get(i);
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
                    .inflate(R.layout.layout_item_tipos_canchas, viewGroup, false);
        }
        TextView text_nombre = view.findViewById(R.id.text_nombre);
        LinearLayout liner_tipo_cancha = view.findViewById(R.id.liner_tipo_cancha);
        try {
            final JSONObject cancha = tipos_canchas.getJSONObject(i);
            text_nombre.setText(cancha.getString("TIPO"));

            liner_tipo_cancha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(contexto,TablaReserva_cancha.class);
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


}
