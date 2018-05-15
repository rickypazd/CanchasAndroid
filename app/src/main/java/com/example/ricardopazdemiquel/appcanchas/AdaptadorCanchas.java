package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorCanchas extends BaseAdapter {

    private List<Cancha> listaCanchas;
    private Context contexto;

    public AdaptadorCanchas(Context contexto, List<Cancha> lista) {
        this.contexto = contexto;
        this.listaCanchas = lista;
    }

    @Override
    public int getCount() {
        return listaCanchas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaCanchas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaCanchas.get(i).getId();
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

        Cancha cancha = this.listaCanchas.get(i);
        imgCancha.setImageResource(cancha.getImagen());
        tvNombre.setText(cancha.getNombre());
        tvValoracion.setText(cancha.getValoracion());
        tvCiudad.setText(cancha.getCiudad());
        tvDireccion.setText(cancha.getDireccion());
        tvDescripcion.setText(cancha.getDescripcion());
        tvNumero.setText(cancha.getNumero());
        tvCorreo.setText(cancha.getCorreo());
        btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(contexto,detalleCancha.class);
                contexto.startActivity(inten);
            }
        });
        return view;
    }
}
