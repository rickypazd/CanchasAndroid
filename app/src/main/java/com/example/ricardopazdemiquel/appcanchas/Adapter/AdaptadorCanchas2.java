package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.Listener.Canchas_AdapterClick;
import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.detalleCancha;
import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AdaptadorCanchas2 extends RecyclerView.Adapter<AdaptadorCanchas2.MyViewHolder> {

    private JSONArray listaCanchas;
    private Context contexto;
    private Canchas_AdapterClick listener;

    public AdaptadorCanchas2() {
    }

    public AdaptadorCanchas2(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.listaCanchas = lista;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cancha, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        try {
            final JSONObject cancha = listaCanchas.getJSONObject(i);
            //imgCancha.setImageResource(cancha.getImagen());
            URL url = null;

            holder.tvNombre.setText(cancha.getString("NOMBRE"));
            holder.tvDireccion.setText(cancha.getString("DIRECCION"));
            holder.tvNumero.setText(cancha.getString("TELEFONO"));
            holder.tvCorreo.setText(cancha.getString("CORREO"));

            holder.btn_ver.setTag(cancha.getInt("ID"));
            //holder.text_propiedad.setTag(obj.getInt("id"));
            holder.btn_ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_casa = (int) view.getTag();
                    //Toast.makeText(contexto, "id" + sfs, Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(contexto,detalleCancha.class);
                    try {
                        intent.putExtra("id_complejo", cancha.getInt("ID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    contexto.startActivity(intent);
                }
            });

            holder.imgCancha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(contexto, detalleCancha.class);
                    try {
                        inten.putExtra("id_complejo", cancha.getInt("ID"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    contexto.startActivity(inten);
                }
            });
            if (cancha.getString("FOTO_PERFIL").length() > 0) {
                Rect rect = new Rect(holder.imgCancha.getLeft(), holder.imgCancha.getTop(), holder.imgCancha.getRight(), holder.imgCancha.getBottom());
                holder.imgCancha.setImageUrl(contexto.getResources().getString(R.string.url_foto) + cancha.getString("B64"), rect);
                //  new AsyncTaskLoadImage(imgCancha).execute(contexto.getResources().getString(R.string.url_foto)+cancha.getString("B64"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*@Override
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
    }*/

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listaCanchas.length();
    }

   /* public Filter getFilter() {
        return mFilter;
    }

    public void FilterTextShared(String nombre){
        this.nombre = nombre;
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
                    //filterableString = list.getJSONObject(i).getString("NOMBRE");
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
            filterData=new JSONArray();
            filterData =(JSONArray) results.values;
            notifyDataSetChanged();

        }

    }*/

    public class AsyncTaskLoadImage extends AsyncTask<String, String, Bitmap> {
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
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
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


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public SmartImageView imgCancha;
        public TextView tvNombre;
        public TextView tvDireccion;
        public TextView tvNumero;
        public TextView tvCorreo;
        public Button btn_ver;

        public MyViewHolder(View v) {
            super(v);
            imgCancha = v.findViewById(R.id.imgCancha);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvDireccion = v.findViewById(R.id.tvDireccion);
            tvNumero = v.findViewById(R.id.tvNumero);
            tvCorreo = v.findViewById(R.id.tvCorreo);
            btn_ver = v.findViewById(R.id.btn_ver_complejo);
        }
    }

}
