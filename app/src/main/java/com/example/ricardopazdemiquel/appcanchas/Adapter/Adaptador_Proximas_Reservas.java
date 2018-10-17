package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.Listener.HorasAdapterClick;
import com.example.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Adaptador_Proximas_Reservas extends RecyclerView.Adapter<Adaptador_Proximas_Reservas.MyViewHolder> {

    private JSONArray listaHoras;

    private Context contexto;
    private SimpleDateFormat form;
    private SimpleDateFormat form2;
    private SimpleDateFormat formres;
    LayoutInflater layoutInflater;
    private Calendar cal;
    private JSONArray reservas;

    private HorasAdapterClick listener;
    public Adaptador_Proximas_Reservas() {
    }

    public Adaptador_Proximas_Reservas(Context contexto, JSONArray lista, Calendar cal, JSONArray reservas, HorasAdapterClick listener) {
        this.contexto = contexto;
        this.listaHoras = lista;
        this.cal=cal;
        this.reservas=reservas;
        form=new SimpleDateFormat("HH:mm:ss");
        form2=new SimpleDateFormat("HH:mm");
        formres= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.listener=listener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_hor, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        JSONObject obj = new JSONObject();
        try {
            final JSONObject hora= listaHoras.getJSONObject(i);
            try {
                obj.put("active",false);
                obj.put("disponible",true);
                obj.put("detalle",hora);
                holder.itemView.setTag(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Date fech = form.parse(hora.getString("HORA"));
            fech.setHours(fech.getHours()+1);
            Date fech2 = form.parse(hora.getString("HORA"));
            holder.tvHoraIncio.setText(form2.format(fech2)+" - "+form2.format(fech)+" hrs");
            holder.tvPrecio.setText("Bs. "+hora.getString("PRECIO"));
            JSONObject temp;
            Date datetmp;
            boolean exi=true;
            for (int j = 0; j <reservas.length() ; j++) {
                temp=reservas.getJSONObject(j);
                datetmp=formres.parse(temp.getString("FECHA"));
                if(form.format(fech2).equals(form.format(datetmp))){
                    exi=false;

                    if(temp.getInt("ESTADO")==1){
                        holder.tvPrecio.setText("Pendiente");
                        holder.tvPrecio.setTextSize(11);
                        holder.tvPrecio.setBackgroundColor(Color.rgb(222,222,0));
                        holder.cardviw.setCardBackgroundColor(Color.rgb(88,88,88));

                    }
                    if(temp.getInt("ESTADO")==2){
                        holder.tvPrecio.setText("No Disponible");
                        holder.tvPrecio.setTextSize(11);
                        holder.tvPrecio.setBackgroundColor(Color.rgb(43,255,255));
                    }
                    return;

                }
            }
            //imgCancha.setImageResource(cancha.getImagen());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onClick(hora,v);
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listaHoras.length();
    }

    @Override
    public long getItemId(int i) {
     return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvHoraIncio;
        public TextView tvPrecio;
        public CardView cardviw;
        public LinearLayout llSelect;

        public MyViewHolder(View v) {
            super(v);
             tvHoraIncio = v.findViewById(R.id.tvHoraIncio);
             tvPrecio = v.findViewById(R.id.tvPrecio);
             cardviw= v.findViewById(R.id.cardviw);
             llSelect = v.findViewById(R.id.llSelect);

        }
    }
}
