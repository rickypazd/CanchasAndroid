package com.example.ricardopazdemiquel.appcanchas.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SPref {

    public static JSONObject getUsr_log(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("myPref", context.MODE_PRIVATE);
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
