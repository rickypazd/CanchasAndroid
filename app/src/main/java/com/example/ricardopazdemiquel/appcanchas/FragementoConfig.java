package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

import static android.content.Context.MODE_PRIVATE;


public class FragementoConfig extends Fragment {

private TextView tv_nombre_conf;
private ProfilePictureView iv_foto_perfil;
private Button btn_logout;
    public FragementoConfig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragemento_config, container, false);
        tv_nombre_conf=view.findViewById(R.id.tv_nombre_conf);
        iv_foto_perfil=view.findViewById(R.id.iv_foto_perfil);
        btn_logout=view.findViewById(R.id.btn_logout);
        Profile prof=Profile.getCurrentProfile();
        tv_nombre_conf.setText(prof.getName());
        iv_foto_perfil.setProfileId(prof.getId());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getActivity(),login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;

    }

}
