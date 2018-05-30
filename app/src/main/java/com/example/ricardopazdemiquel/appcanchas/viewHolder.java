package com.example.ricardopazdemiquel.appcanchas;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class viewHolder extends RecyclerView.ViewHolder {


    public TextView nombre;
    public TextView tipo;
    public TextView desc1;
    public TextView desc2;
    public Button btn_reservar;
    public viewHolder(View v) {
        super(v);
         nombre = v.findViewById(R.id.tv_info_nombre);
         tipo = v.findViewById(R.id.tv_tipo);
         desc1= v.findViewById(R.id.tv_desc1);
         desc2 = v.findViewById(R.id.tv_desc2);
         btn_reservar= v.findViewById(R.id.btn_reservar);
        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(),TablaReserva.class);
                v.getContext().startActivity(inte);
            }
        });
            nombre.setText("");
    }
}
