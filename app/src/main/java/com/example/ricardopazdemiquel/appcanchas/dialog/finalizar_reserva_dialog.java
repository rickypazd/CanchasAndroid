package com.example.ricardopazdemiquel.appcanchas.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardopazdemiquel.appcanchas.Main2Activity;
import com.example.ricardopazdemiquel.appcanchas.MetodoDePago;
import com.example.ricardopazdemiquel.appcanchas.R;

@SuppressLint("ValidFragment")
public class finalizar_reserva_dialog extends DialogFragment implements View.OnClickListener {

    private static final int RESERVA = 1;
    private static final int PAGO = 2;
    private int tipo;
    private Button btn_listo;
    private TextView text_mensaje;

    @SuppressLint("ValidFragment")
    public finalizar_reserva_dialog(int tipo){
        this.tipo = tipo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmanetstyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragmento_reserva_finalizada, null);
        builder.setView(v);

        btn_listo = v.findViewById(R.id.btn_listo);
        text_mensaje= v.findViewById(R.id.text_mensaje);
        btn_listo.setOnClickListener(this);

        switch (tipo){
            case RESERVA:
                text_mensaje.setText("El administrador se comunicará contigo.");
                break;
            case PAGO:
                text_mensaje.setText("Te esperamos en la cancha.");
                break;
        }
        return builder.create();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_listo){
            Intent intent = new Intent(getActivity(), Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
            dismiss();
        }
    }


}
