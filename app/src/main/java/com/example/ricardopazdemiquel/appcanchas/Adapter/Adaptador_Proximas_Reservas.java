package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.Listener.HorasAdapterClick;
import com.example.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


public class Adaptador_Proximas_Reservas extends RecyclerView.Adapter<Adaptador_Proximas_Reservas.MyViewHolder> {

    private JSONArray objArray;

    private Context contexto;
    private SimpleDateFormat form;
    private SimpleDateFormat form2;
    private SimpleDateFormat formres;
    private JSONArray reservas;

    private HorasAdapterClick listener;

    public Adaptador_Proximas_Reservas() {
    }

    public Adaptador_Proximas_Reservas(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.objArray = lista;
        form = new SimpleDateFormat("HH:mm:ss");
        form2 = new SimpleDateFormat("HH:mm");
        formres = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_proximas_reservas, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        try {
            final JSONObject obj = objArray.getJSONObject(i);
            int estado = obj.getInt("ESTADO");
            switch (estado) {
                case 1:
                    holder.tv_fecha.setText(obj.getString("FECHA"));
                    holder.tv_nombre_complejo.setText(obj.getString("NOMBRE_COMPLEJO"));
                    String hora1 = obj.getString("HORA_INICIO");
                    String hora2 = obj.getString("HORA_FIN");
                    holder.tv_hora.setText(hora1 + " - " + hora2 + " hrs");
                    holder.tv_tipo.setText(getEstado(estado));
                    break;
                case 2:
                    holder.tv_fecha.setText(obj.getString("FECHA"));
                    holder.tv_nombre_complejo.setText(obj.getString("NOMBRE_COMPLEJO"));
                    String hora3 = obj.getString("HORA_INICIO");
                    String hora4 = obj.getString("HORA_FIN");
                    holder.tv_hora.setText(hora3 + " - " + hora4 + " hrs");
                    holder.tv_tipo.setText(getEstado(estado));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getEstado(int estado) {
        switch (estado) {
            case 1:
                return "Pendiente";
            case 2:
                return "Reservado";
            case 3:
                return "Cancelado";
            case 4:
                return "Terminado";
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return objArray.length();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_fecha;
        public TextView tv_nombre_complejo;
        public TextView tv_hora;
        public TextView tv_tipo;

        public MyViewHolder(View v) {
            super(v);
            tv_fecha = v.findViewById(R.id.tv_fecha);
            tv_nombre_complejo = v.findViewById(R.id.tv_nombre_complejo);
            tv_hora = v.findViewById(R.id.tv_hora);
            tv_tipo = v.findViewById(R.id.tv_tipo);
        }
    }
}
