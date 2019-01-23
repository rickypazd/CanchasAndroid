package com.example.ricardopazdemiquel.appcanchas;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.HttpConnection;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.MethodType;
import com.example.ricardopazdemiquel.appcanchas.clienteHTTP.StandarRequestConfiguration;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import complementos.Contexto;


public class login extends AppCompatActivity {

    private LoginButton loginFacebook;
    private Button login;
    private Button loginpelotead;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        login = findViewById(R.id.fb);
        loginpelotead = findViewById(R.id.loginpelotead);
        loginpelotead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        InitLoginFacebook();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFacebook.callOnClick();
            }
        });
    }


    private void InitLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        loginFacebook = findViewById(R.id.loginFacebook);
        loginFacebook.setReadPermissions("email");

        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                // Application code
                                try {
                                    String id = object.getString("id");
                                    try {
                                        String resp = new get_usr_face(id).execute().get();
                                        if (resp == null) {
                                            Toast.makeText(login.this, "Error al conectarse con el servidor.", Toast.LENGTH_SHORT).show();
                                            LoginManager.getInstance().logOut();
                                        } else {
                                            if (resp.contains("falso")) {
                                                Log.e(Contexto.APP_TAG, "Hubo un error al conectarse al servidor.");
                                            } else {
                                                try {
                                                    JSONObject obj = new JSONObject(resp);
                                                    if (obj.getString("exito").equals("si")) {
                                                        SharedPreferences preferencias = getSharedPreferences("myPref", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferencias.edit();
                                                        editor.putString("usr_log", obj.toString());
                                                        editor.commit();

                                                        Intent intent = new Intent(login.this, Main2Activity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(login.this, Iniciar_cuenta_fb_Activity.class);
                                                        intent.putExtra("usr_face", object.toString());
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        LoginManager.getInstance().logOut();

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,middle_name,last_name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                if (AccessToken.getCurrentAccessToken() == null) {
                    return; // already logged out
                }
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                        LoginManager.getInstance().logInWithReadPermissions(login.this, Arrays.asList("public_profile,email"));

                    }
                }).executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                AccessToken.setCurrentAccessToken(null);
                LoginManager.getInstance().logInWithReadPermissions(login.this, Arrays.asList("public_profile,email,user_birthday"));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class get_usr_face extends AsyncTask<Void, String, String> {
        private String id;

        public get_usr_face(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "get_usuario_face");
            parametros.put("id_usr", id + "");
//            parametros.put("token", Token.currentToken);
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, parametros));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }
}
