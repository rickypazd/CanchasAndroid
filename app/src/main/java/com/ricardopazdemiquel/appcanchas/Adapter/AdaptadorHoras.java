package com.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ricardopazdemiquel.appcanchas.Listener.HorasAdapterClick;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdaptadorHoras extends RecyclerView.Adapter<AdaptadorHoras.MyViewHolder> {

    private JSONArray listaHoras;

    private Context contexto;
    private SimpleDateFormat form;
    private SimpleDateFormat form2;
    private SimpleDateFormat formres;
    LayoutInflater layoutInflater;
    private Calendar cal;


    private HorasAdapterClick listener;

    public AdaptadorHoras() {
    }

    public AdaptadorHoras(Context contexto, JSONArray lista, Calendar cal, HorasAdapterClick listener) {
        this.contexto = contexto;
        this.listaHoras = lista;
        this.cal = cal;

        form = new SimpleDateFormat("HH:mm:ss");
        form2 = new SimpleDateFormat("HH:mm");
        formres = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.listener = listener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_hor, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int i) {

        try {
            final JSONObject hora = listaHoras.getJSONObject(i);

            JSONArray array = hora.getJSONArray("canchas");

            if (!hora.has("active")) {
                hora.put("active", false);
                listaHoras.put(i, hora);
            }

            Date fech = form.parse(hora.getString("hora"));
            fech.setHours(fech.getHours() + 1);
            Date fech2 = form.parse(hora.getString("hora"));
            holder.tvHoraIncio.setText(form2.format(fech2) + " - " + form2.format(fech) + " hrs");

            final JSONObject obj = get_cancha(array);
            holder.tvPrecio.setText("Bs. " + obj.getString("precio"));
            if (hora.getBoolean("active")) {
                holder.llSelect.setBackgroundColor(Color.rgb(0, 0, 0));
                holder.tvHoraIncio.setTextColor(Color.rgb(255, 255, 255));
            } else {
                holder.llSelect.setBackgroundColor(Color.rgb(255, 255, 255));
                holder.tvHoraIncio.setTextColor(Color.rgb(0, 0, 0));
            }

            switch (obj.getInt("estado")) {
                case 1:
                    holder.llSelect.setBackgroundColor(Color.rgb(255, 255, 0));
                    holder.tvHoraIncio.setTextColor(Color.rgb(0, 0, 0));
                    break;
                case 2:
                    holder.llSelect.setBackgroundColor(Color.rgb(0, 255, 0));
                    holder.tvHoraIncio.setTextColor(Color.rgb(0, 0, 0));
                    break;
            }


            //imgCancha.setImageResource(cancha.getImagen());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(hora, v);
                        try {
                            if (hora.getBoolean("active")) {
                                hora.put("active", false);
                                listaHoras.put(i, hora);
                            } else {
                                hora.put("active", true);
                                listaHoras.put(i, hora);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    int estado;
    JSONObject cancha_temp;
    JSONObject obj;

    public JSONObject get_cancha(JSONArray array) {
        for (int a = 0; a < array.length(); a++) {
            try {
                obj = array.getJSONObject(a);
                estado = obj.getInt("estado");
                if (estado == 0) {
                    return obj;
                    // devuelve cancha
                } else if (estado == 1) {
                    cancha_temp = obj;
                }
                if (a == array.length() - 1) {
                    if (cancha_temp == null) {
                        return obj;
                    } else {
                        return cancha_temp;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
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
            cardviw = v.findViewById(R.id.cardviw);
            llSelect = v.findViewById(R.id.llSelect);


        }
    }
}
