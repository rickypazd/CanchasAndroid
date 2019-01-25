package com.example.ricardopazdemiquel.appcanchas.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.ricardopazdemiquel.appcanchas.R;

/**
 * Created by Edson on 02/12/2017.
 */

@SuppressLint("ValidFragment")
public class map_reserva_dialog extends DialogFragment implements View.OnClickListener {

    private Button btn_cancelar;
    private Button btn_confirmar;
    private double creditos;
    private int tipo;

    private static final int TIPO_TOGO = 2;
    public static String APP_TAG = "registro";

    //private static final String TAG = Confirmar_viaje_Dialog.class.getSimpleName();

    @SuppressLint("ValidFragment")
    public map_reserva_dialog(int tipo, double creditos) {
        this.tipo = tipo;
        this.creditos=creditos;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmanetstyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.map_reserva_dialog, null);
        builder.setView(v);

        btn_cancelar = v.findViewById(R.id.btn_cancelarD);
        btn_confirmar = v.findViewById(R.id.btn_confirmarD);
        btn_cancelar.setOnClickListener(this);
        btn_confirmar.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmarD:
               /* try {
                    if(tipo == TIPO_TOGO){
                        //((PedirSieteTogo)getActivity()).ok_pedir_viaje();
                        dismiss();
                    }else {
                       // ((Calcular_ruta_activity)getActivity()).ok_pedir_viaje();
                        dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                break;
            case R.id.btn_cancelarD:
                dismiss();
                break;
        }
    }

 }


