package com.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ricardopazdemiquel.appcanchas.R;

public class FragmentoBuscarCancha extends Fragment implements View.OnClickListener {

    private EditText etNombreCancha;
    private ImageButton btnNombreCancha;
    private Button btnBuscar;
    private Spinner spZonas, spCategorias;

    public FragmentoBuscarCancha() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_buscar_cancha, container, false);

        etNombreCancha = view.findViewById(R.id.etNombreCancha);
        btnNombreCancha = view.findViewById(R.id.btnNombreCancha);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        spZonas = view.findViewById(R.id.spZonas);
        spCategorias = view.findViewById(R.id.spCategorias);

        btnNombreCancha.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNombreCancha:
                buscarNombreCancha();
                break;

            case R.id.btnBuscar:
                buscar();
                break;
        }
    }

    private void buscarNombreCancha() {
        Toast.makeText(getActivity().getBaseContext(),
                "Lógica de la busqueda del nombre de la cancha", Toast.LENGTH_SHORT).show();
    }

    private void buscar() {
        String nombreCancha = etNombreCancha.getText().toString();
        String zona = spZonas.getSelectedItem().toString();
        String categoria = spCategorias.getSelectedItem().toString();

        String seleccionJson = "{" +
                "nombreCancha: " + nombreCancha + ", " +
                "zona: "+ zona + ", " +
                "categoria: " + categoria +
                "}";

        Toast.makeText(getActivity().getBaseContext(), seleccionJson, Toast.LENGTH_SHORT).show();
    }

}
