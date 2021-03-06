package com.ricardopazdemiquel.appcanchas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorCanchasComplejo;
import com.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class FragmentoCanchas extends Fragment {

    private RecyclerView rv;
    private JSONObject obj_complejo;
    public FragmentoCanchas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragmento_canchas, container, false);
        rv=view.findViewById(R.id.reciclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager mylinear = new LinearLayoutManager(getActivity());
        mylinear.setOrientation(LinearLayoutManager.HORIZONTAL);
        try{
            //obj_complejo=((detalleCancha)getActivity()).getComplejo();
            JSONArray arr = obj_complejo.getJSONArray("CANCHAS");

            AdaptadorCanchasComplejo adaptador = new AdaptadorCanchasComplejo(arr,getContext());
            rv.setAdapter(adaptador);
            rv.setLayoutManager(mylinear);


        }catch (Exception e){

        }
        return view;
    }


}
