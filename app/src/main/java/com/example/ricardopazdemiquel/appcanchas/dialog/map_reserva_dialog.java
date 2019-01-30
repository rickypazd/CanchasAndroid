package com.example.ricardopazdemiquel.appcanchas.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.detalleCancha;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Edson on 02/12/2017.
 */

@SuppressLint("ValidFragment")
public class map_reserva_dialog extends DialogFragment implements View.OnClickListener {

    private JSONObject obj;
    private Button btn_reservar;
    public TextView tvNombre;
    public TextView tvDireccion;
    public TextView tvNumero;
    public TextView tvCorreo;

    @SuppressLint("ValidFragment")
    public map_reserva_dialog(JSONObject obj) {
        this.obj = obj;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmanetstyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.map_reserva_dialog, null);
        builder.setView(view);

        tvNombre = view.findViewById(R.id.tvNombre);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvNumero = view.findViewById(R.id.tvNumero);
        tvCorreo = view.findViewById(R.id.tvCorreo);
        btn_reservar = view.findViewById(R.id.btn_reservar);

        try {
            tvNombre.setText(obj.getString("NOMBRE"));
            tvDireccion.setText(obj.getString("DIRECCION"));
            tvNumero.setText(obj.getString("TELEFONO"));
            tvCorreo.setText(obj.getString("CORREO"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_reservar.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reservar) {
            Fragment fragmentoGenerico = null;
            FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
            try {
                fragmentoGenerico = new detalleCancha(obj.getInt("ID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (fragmentoGenerico != null) {
                fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();
            }
            dismiss();
        }
    }

 }


