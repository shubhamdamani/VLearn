package com.example.vlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class login extends AppCompatActivity {
   SpotsDialog dialog;
    EditText userame,pass;
    Button Login,gotoRegister;
    String l_name="",l_pass="";
    public String LOGGED_USER_EMAIL;
    public static final String LOGIN_URL="https://vlearndroidrun.000webhostapp.com/login.php";
    public static final String KEY_NAME="name";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="name";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final Boolean IS_LOGGED_IN=false;
   private boolean loggedIn=false;
    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String emyl;
    public String USER_ID,USER_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userame=findViewById(R.id.L_username);
        pass=findViewById(R.id.L_password);
        Login=findViewById(R.id.login);
        gotoRegister=findViewById(R.id.goto_register);
        emyl=userame.getText().toString().trim();

        l_name=userame.getText().toString();
        l_pass=pass.getText().toString();

       // SharedPreferences sharedPreferences = login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
       // SharedPreferences.Editor editor = sharedPreferences.edit();
       // editor.clear();
      //  editor.commit();


      //  dialog=new SpotsDialog(this);
/*
        SharedPreferences sharedPreferences = login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();*/
      /*  SharedPreferences sharedPreferences = login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(SHARED_PREF_NAME))
        {
            Toast.makeText(login.this,"here",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(login.this,MainActivity.class);
            startActivity(i);
        }*/





        //Login process and fetching data of user is performed here
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  //              dialog.show();
//                dialog.setMessage("Logging in");


                loginmet();

            }
        });
        //If User want to create new account this register button send intent to registerActivity
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,register.class);
                LOGGED_USER_EMAIL=l_name;
                startActivity(i);
            }
        });
        

    }

    //Retrieve Details Of USER from Database using JSON parsing
    public void getDatafromJSON()
    {
       // Toast.makeText(login.this,"hio"+JSON_String,Toast.LENGTH_LONG).show();
        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                USER_NAME=jo.getString("UserName");
                USER_ID=jo.getString("User_Id");
                //User_obj=new USER_Class();
                USER_Class.setLoggedUserId(USER_ID);
                USER_Class.setLoggedUserEmail(emyl);
                USER_Class.setLoggedUserName(USER_NAME);
                Toast.makeText(login.this,"hi",Toast.LENGTH_SHORT).show();
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Thread is created to fetch user info as a JSON data
    //which work in background
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getDetails.php";
        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                //my
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("u_email","UTF-8")+"="+URLEncoder.encode(emyl,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();

                while((json_string=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                JSON_String=stringBuilder.toString().trim();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "fail";
        }
        public BackgroundTask()
        {
            super();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {

            JSON_String=result;

            //Toast.makeText(login.this,"asd"+JSON_String,Toast.LENGTH_SHORT).show();
           getDatafromJSON();
            Toast.makeText(login.this,USER_NAME+" "+USER_Class.getLoggedUserId(),Toast.LENGTH_SHORT).show();
            Intent i=new Intent(login.this,MainActivity.class);
//            dialog.dismiss();
           startActivity(i);
            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    //To check user is already logged in or not
    private void loginmet() {

        final String name = userame.getText().toString();
        emyl=name;
        final String password = pass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){
                           SharedPreferences sharedPreferences = login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("LOGGEDIN", true);
                            editor.putString(EMAIL_SHARED_PREF, emyl);
                            //final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                           // SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("Registered", true);
                            editor.putString("USER_NAME",name);
                            editor.putString("USER_PASS",password);
                            editor.putString("USER_EML",emyl);
                            editor.putString("USER_ID",USER_ID);
                            editor.commit();
                           /* editor.apply();*/
                            new login.BackgroundTask().execute();

                        }else{
                            // If invalid Email or Password is entered
                            Toast.makeText(login.this, "Invalid Email or password", Toast.LENGTH_LONG).show();
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
    //If user is already Logged in then User data will be collected and
    // stored in USER_Class and then Intent to MainActivity
    @Override
    protected void onResume() {
        super.onResume();
        /*Boolean Registered;
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Registered = sharedPref.getBoolean("Registered", false);*/
        if(true){
            loginmet();
            //Intent intent = new Intent(login.this, MainActivity.class);
            //startActivity(intent);
        }
    }
}
