package com.example.ricardopazdemiquel.appcanchas.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ricardopazdemiquel.appcanchas.R;
import com.example.ricardopazdemiquel.appcanchas.Utiles.SectionsPageAdapter;


public class SetupViewPager_fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "menuActivity";
    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager);

        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(mViewPager);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mSectionsPagerAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }



    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragment_proximas_reservas(),"Proximas Reservas");
        adapter.addFragment(new FragmentoHistorial(),"Historial");
        viewPager.setAdapter(adapter);
    }


}
