package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.common.api.Response;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText username,pass;
    Button Login;
    String l_name="",l_pass="";

    public static final String LOGIN_URL="https://vlearndroidrun.000webhostapp.com/login.php";
    public static final String KEY_NAME="name";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="name";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private boolean loggedIn=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.L_username);
        pass=findViewById(R.id.L_password);
        Login=findViewById(R.id.login);

        l_name=username.getText().toString();
        l_pass=pass.getText().toString();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginmet();

            }
        });
    }
    private void loginmet() {

        final String name = username.getText().toString();
        final String password = pass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                   // Toast.makeText(login.this,"fkng",Toast.LENGTH_LONG).show();
                    @Override
                    public void onResponse(String response) {



                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            SharedPreferences sharedPreferences = login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(EMAIL_SHARED_PREF, name);

                            editor.commit();

                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_NAME, name);
                prams.put(KEY_PASSWORD, password);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
  /*  @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        }
    }*/
}
