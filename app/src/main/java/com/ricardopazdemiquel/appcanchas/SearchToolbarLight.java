package com.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardopazdemiquel.appcanchas.Adapter.AdapterSearchCanchas;
import com.ricardopazdemiquel.appcanchas.Adapter.AdapterSuggestionSearch;
import com.ricardopazdemiquel.appcanchas.Utiles.SPref;
import com.ricardopazdemiquel.appcanchas.Utiles.ViewAnimation;
import com.ricardopazdemiquel.appcanchas.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import complementos.Tools;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchToolbarLight extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_search;
    private ImageButton bt_clear;

    private ProgressBar progress_bar;
    private LinearLayout lyt_no_result;

    private RecyclerView recyclerSuggestion;
    private AdapterSuggestionSearch mAdapterSuggestion;
    private AdapterSearchCanchas adapter;
    private LinearLayout lyt_suggestion;
    private JSONArray arr;
    private JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar_light);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icon_left_arrow);
        //Tools.setSystemBarColor(this, R.color.grey_5);
        //Tools.setSystemBarLight(this);
    }

    private void initComponent() {
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        lyt_no_result = (LinearLayout) findViewById(R.id.lyt_no_result);

        lyt_suggestion = (LinearLayout) findViewById(R.id.lyt_suggestion);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(textWatcher);

        bt_clear = (ImageButton) findViewById(R.id.bt_clear);
        bt_clear.setVisibility(View.GONE);

        try {
            arr = new JSONArray(getIntent().getStringExtra("obj"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerSuggestion = (RecyclerView) findViewById(R.id.recyclerSuggestion);
        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(this));
        recyclerSuggestion.setHasFixedSize(true);

        adapter = new AdapterSearchCanchas(this, arr);
        recyclerSuggestion.setAdapter(adapter);
        //set data and list adapter suggestion
       /* mAdapterSuggestion = new AdapterSuggestionSearch(this);
        recyclerSuggestion.setAdapter(mAdapterSuggestion);
        showSuggestionSearch();*/
        adapter.setOnItemClickListener(new AdapterSearchCanchas.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String viewModel, int pos) {
                et_search.setText(viewModel);

                // Recogemos el intent que ha llamado a esta actividad.
                Intent i = getIntent();
                // Le metemos el resultado que queremos mandar a la
                // actividad principal.
                i.putExtra("RESULTADO", pos);
                // Establecemos el resultado, y volvemos a la actividad
                // principal. La variable que introducimos en primer lugar
                // "RESULT_OK" es de la propia actividad, no tenemos que
                // declararla nosotros.
                setResult(RESULT_OK, i);
                finish();


                /*Fragment fragmentoGenerico = null;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentoGenerico = new detalleCancha(pos);
                if (fragmentoGenerico != null) {
                    fragmentManager.beginTransaction().replace(R.id.fragmentoContenedor, fragmentoGenerico).commit();
                }*/

                //ViewAnimation.collapse(lyt_suggestion);
                //Toast.makeText(getApplicationContext(), viewModel +" "+pos, Toast.LENGTH_SHORT).show();
                //hideKeyboard();
                //searchAction();
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    //searchAction();
                    return true;
                }
                return false;
            }
        });

       /* et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSuggestionSearch();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            }
        });*/
    }

    private void showSuggestionSearch() {
        mAdapterSuggestion.refreshItems();
        ViewAnimation.expand(lyt_suggestion);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
                adapter.setCountries(arr);
            } else {
                bt_clear.setVisibility(View.VISIBLE);
                processoQuery(c.toString());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    private void processoQuery(String s) {
        final JSONArray list = arr;
        int count = list.length();
        final JSONArray newArry = new JSONArray();
        String filterableString;
        for (int i = 0; i < count; i++) {
            try {
                filterableString = list.getJSONObject(i).getString("NOMBRE");
                if (filterableString.toLowerCase().contains(s.toLowerCase())) {
                    newArry.put(list.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.setCountries(newArry);
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        ViewAnimation.collapse(lyt_suggestion);
        lyt_no_result.setVisibility(View.GONE);

        final String query = et_search.getText().toString().trim();
        if (!query.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress_bar.setVisibility(View.GONE);
                    lyt_no_result.setVisibility(View.VISIBLE);
                }
            }, 2000);
            mAdapterSuggestion.addSearchHistory(query);
        } else {
            Toast.makeText(this, "Please fill search input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
