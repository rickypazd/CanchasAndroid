package com.example.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class FragmentoFiltroCancha extends Fragment implements View.OnClickListener {

    private EditText etNombreCancha, etZonaDireccion;
    private ImageButton btnNombreCancha, btnZonaDireccion;
    private CheckBox chkWifi, chkPastoSintetico, chkChurrasqueras;
    private SeekBar seekBarPrecio;
    private Button btnBuscar;

    public FragmentoFiltroCancha() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_filtro_cancha, container, false);

        etNombreCancha = view.findViewById(R.id.etNombreCancha);
        etZonaDireccion = view.findViewById(R.id.etZonaDireccion);
        btnNombreCancha = view.findViewById(R.id.btnNombreCancha);
        btnZonaDireccion = view.findViewById(R.id.btnZonaDireccion);
        chkWifi = view.findViewById(R.id.chkWifi);
        chkPastoSintetico = view.findViewById(R.id.chkPastoSintetico);
        chkChurrasqueras = view.findViewById(R.id.chkChurrasqueras);
        seekBarPrecio = view.findViewById(R.id.seekBarPrecio);
        btnBuscar = view.findViewById(R.id.btnBuscar);

        btnNombreCancha.setOnClickListener(this);
        btnZonaDireccion.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNombreCancha:
                buscarNombreCancha();
                break;

            case R.id.btnZonaDireccion:
                buscarZonaDireccion();
                break;

            case R.id.btnBuscar:
                buscar();
                break;
        }
    }

    private void buscar() {
        String nombreCancha = etNombreCancha.getText().toString();
        String zonaDireccion = etZonaDireccion.getText().toString();
        boolean conWifi = chkWifi.isChecked();
        boolean conPastoSintetico = chkPastoSintetico.isChecked();
        boolean conChurrasquera = chkChurrasqueras.isChecked();
        int precio = seekBarPrecio.getProgress();

        String seleccionJson = "{" +
                "nombreCancha: " + nombreCancha + ", " +
                "zonaDireccion: " + zonaDireccion + ", " +
                "wifi: " + conWifi + ", " +
                "pastoSintetico: " + conPastoSintetico + ", " +
                "conChurrasquera: " + conChurrasquera + ", " +
                "precio: " + precio +
                "}";

        Toast.makeText(getActivity().getBaseContext(), seleccionJson, Toast.LENGTH_SHORT).show();
    }

    private void buscarZonaDireccion() {
        Toast.makeText(getActivity().getBaseContext(),
                "Lógica de la busqueda de la zona o direccion", Toast.LENGTH_SHORT).show();
    }

    private void buscarNombreCancha() {
        Toast.makeText(getActivity().getBaseContext(),
                "Lógica de la busqueda del nombre de la cancha", Toast.LENGTH_SHORT).show();
    }

}
