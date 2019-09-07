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

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText username,email,pass,re_pass;
    Button register;
    String r_name="",r_email="",r_pass="",ure_pass="";
    public static final String CHECK_URL="https://vlearndroidrun.000webhostapp.com/check_user.php";
    public static final String KEY_NAME="name";
    public static final String KEY_EMAIL="email";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="name";

    Button temp,ques;

    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private boolean loggedIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.username);
        email=findViewById(R.id.Email);
        pass=findViewById(R.id.Password);
        re_pass=findViewById(R.id.Re_Password);
        register=findViewById(R.id.register);
        temp=findViewById(R.id.gotemp);
        ques=findViewById(R.id.question);

        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(register.this,questionActivity.class);
                startActivity(i);
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(register.this,interests.class);
                startActivity(i);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                           //Register button Listener
                r_name=username.getText().toString();
                r_email=email.getText().toString();
                r_pass=pass.getText().toString();
                ure_pass=re_pass.getText().toString();
                if(!r_name.equals("")&&!r_email.equals("")&&!r_pass.equals("")&&!ure_pass.equals(""))
                {
                    reg_check();
                }else{
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void reg(){

        if(r_pass.equals(ure_pass)) {
            String method="register";
            BackgroundTask backgroundTask=new BackgroundTask(getBaseContext());
            backgroundTask.execute(method,r_name,r_email,r_pass);
        }else{
            Toast.makeText(getApplicationContext(),"Enter Same Password in Both Fields",Toast.LENGTH_SHORT).show();
        }
    }

    //This method check if user already exist or not
    // if not then it will create new account
    private void reg_check() {

        final String name = username.getText().toString();
        final String Email = email.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            Toast.makeText(register.this,"User already exists ",Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPreferences = register.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(EMAIL_SHARED_PREF, name);

                            editor.commit();
                            //if user exist already then intent send to the login activity
                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                        }else{
                            reg();
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
                prams.put(KEY_EMAIL, Email);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


   /* @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(register.this, MainActivity.class);
            startActivity(intent);
        }
    }*/
}
