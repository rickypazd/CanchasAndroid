package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

public class Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);
        ejecutar();

    }
    public void ejecutar(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferencias = getSharedPreferences("myPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.remove("usr_log");
                editor.commit();
                if (primeraVezEjecutada()) {
                    Intent intent = new Intent(Carga.this, PresentacionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else if(SPref.getUsr_log(Carga.this) == null){
                    Intent intent = new Intent(Carga.this,Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //new CargarListaTask().execute();<]

                }else{
                    Intent intent = new Intent(Carga.this,Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, 3000);

    }
    public boolean primeraVezEjecutada() {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        boolean primeraVez = preferencias.getBoolean("PrimeraVez", false);

        if (!primeraVez) {
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putBoolean("PrimeraVez", true);
            editor.commit();
        }

        return !primeraVez;
    }
    public JSONObject getUsr_log() {
        SharedPreferences preferencias = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);
                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
