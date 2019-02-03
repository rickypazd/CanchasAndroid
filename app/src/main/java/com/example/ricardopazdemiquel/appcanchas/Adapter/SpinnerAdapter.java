package com.example.ricardopazdemiquel.appcanchas.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ricardopazdemiquel.appcanchas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpinnerAdapter extends BaseAdapter {

    Activity activity;
    JSONArray jsonArray;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity activity, JSONArray jsonArray) {
        this.activity = activity;
        this.jsonArray = jsonArray;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {

            JSONObject proyecto = null;
            try {
            proyecto = (JSONObject) jsonArray.get(i);
            int a = proyecto.getInt("ID");
            return a ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = inflater.inflate(R.layout.spinner_row , null);
        TextView textView = row.findViewById(R.id.titlerow);
        try {

                    JSONObject proyecto = null;
                    proyecto = (JSONObject) jsonArray.get(i);
                    textView.setText(proyecto.getString("TIPO"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        return row;
    }
}
