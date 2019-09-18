package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.PersonalInfo.BookmarkActivity;
import com.example.vlearn.PersonalInfo.FollowUser;
import com.example.vlearn.PersonalInfo.UpdateInfo;

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

public class myprofile extends AppCompatActivity {

    Button btnUpdInfo,btnUpdinterest,btnBookmark,btnMyPost,btnProLogout;
    TextView no_followers,no_following;

    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        btnUpdInfo=findViewById(R.id.bPersonal);
        btnUpdinterest=findViewById(R.id.bUpdinterest);
        btnBookmark=findViewById(R.id.bBookmark);
        btnMyPost=findViewById(R.id.bMypost);
        btnProLogout=findViewById(R.id.prologout);
        btnProLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_Class.setLoggedUserEmail("");
                USER_Class.setLoggedUserId("");
                USER_Class.setLoggedUserName("");
                Intent i=new Intent(myprofile.this,login.class);
                startActivity(i);
                finish();
            }
        });

        no_followers=findViewById(R.id.no_followers);
        no_following=findViewById(R.id.no_following);

        new BackgroundTask().execute();

        no_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myprofile.this,Followers.class));
            }
        });

        no_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myprofile.this,Following.class));
            }
        });

        btnUpdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(myprofile.this, UpdateInfo.class);//to start
                startActivity(i);
            }
        });
        btnUpdinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(myprofile.this,interests.class);
                i.putExtra("operation","up_interest");
                startActivity(i);

            }
        });
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(myprofile.this, BookmarkActivity.class);
                    startActivity(i);

            }
        });

        btnMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(myprofile.this, FollowUser.class);
                i.putExtra("UserName",USER_Class.getLoggedUserName());
                i.putExtra("User_Id",USER_Class.getLoggedUserId());
                startActivity(i);
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
                String N_followers=jo.getString("Followers");
                String N_following=jo.getString("Following");
                no_followers.setText(N_followers);
                no_following.setText(N_following);
                //Toast.makeText(login.this,"hi",Toast.LENGTH_SHORT).show();
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserFollow.php";
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
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8");
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
            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void onBackPressed(){

        startActivity(new Intent(myprofile.this,MainActivity.class));
        finish();
        return;
    }


}
