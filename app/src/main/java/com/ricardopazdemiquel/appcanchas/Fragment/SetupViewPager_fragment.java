package com.ricardopazdemiquel.appcanchas.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.ricardopazdemiquel.appcanchas.Adapter.AdaptadorHistory;
import com.ricardopazdemiquel.appcanchas.R;
import com.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.ricardopazdemiquel.appcanchas.Utiles.SectionsPageAdapter;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import complementos.Contexto;


public class SetupViewPager_fragment extends Fragment {

    private static final String TAG = "menuActivity";
    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mSectionsPagerAdapter = new SectionsPageAdapter(getChildFragmentManager());
        mViewPager = view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment_proximas_reservas(),"Proximas Reservas");
        adapter.addFragment(new FragmentoHistorial(),"Historial");
        viewPager.setAdapter(adapter);
    }


}
