package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText username,email,pass,re_pass;
    Button register;
    String r_name="",r_email="",r_pass="",ure_pass="";
    //public static final String CHECK_URL="https://vlearndroidrun.000webhostapp.com/check_user.php";
    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String success;



    Button temp,ques;

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


        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(register.this,interests.class);
                i.putExtra("operation","insert_interest");
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
                        new register.BackgroundTask().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


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
                success=jo.getString("success");

                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/register.php";
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
                String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(r_email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(r_pass,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(r_name,"UTF-8");
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

            Log.d("josn",JSON_String);
            //Toast.makeText(login.this,"asd"+JSON_String,Toast.LENGTH_SHORT).show();
            getDatafromJSON();
            if(success.equals("no"))
            {
                Toast.makeText(register.this,"REGISTRATION FAILED",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(register.this,"REGISTRATION SUCCESS",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(register.this,interests.class);
                startActivity(i);
            }


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }




}
