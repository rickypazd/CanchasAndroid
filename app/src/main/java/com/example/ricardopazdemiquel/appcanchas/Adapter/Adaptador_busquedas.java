package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.detalleCancha;
import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Adaptador_busquedas extends BaseAdapter {

    private JSONArray listaCanchas;
    private JSONArray filterData;
    private Context contexto;

    LayoutInflater layoutInflater;
    private ItemFilter mFilter = new ItemFilter();

    public Adaptador_busquedas() {
    }

    public Adaptador_busquedas(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.listaCanchas = lista;
        this.filterData = lista;
        layoutInflater=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filterData.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return filterData.get(i);
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

            view = LayoutInflater.from(contexto)
                    .inflate(R.layout.layout_item_busqueda, viewGroup, false);

        ViewGroup viewgroup = (ViewGroup)layoutInflater.inflate(R.layout.layout_item_cancha,null);

        TextView tvNombre = view.findViewById(R.id.tvNombre);
        TextView tvValoracion = view.findViewById(R.id.tvValoracion);
        TextView tvDireccion = view.findViewById(R.id.tvDireccion);
        TextView tvDescripcion = view.findViewById(R.id.tvDescripcion);
        Button btn_ver = view.findViewById(R.id.btn_ver_complejo);
        try {
            final JSONObject cancha = filterData.getJSONObject(i);

            tvNombre.setText(cancha.getString("NOMBRE"));
            tvValoracion.setText("10 K");
            tvDireccion.setText(cancha.getString("DIRECCION"));
            tvDescripcion.setText(Html.fromHtml(cancha.getString("PRESENTACION")));
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

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final JSONArray list = listaCanchas;

            int count = list.length();
            final JSONArray nlist = new JSONArray();

            String filterableString ;

            for (int i = 0; i < count; i++) {
                try {
                    filterableString = list.getJSONObject(i).getString("NOMBRE");
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.put(list.getJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            results.values = nlist;
            results.count = nlist.length();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterData =(JSONArray) results.values;
            notifyDataSetChanged();
        }

    }

}
