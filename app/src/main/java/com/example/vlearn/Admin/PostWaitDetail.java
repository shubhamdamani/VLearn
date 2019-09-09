package com.example.vlearn.Admin;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.R;
import com.example.vlearn.USER_Class;

import org.json.JSONArray;
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

public class PostWaitDetail extends AppCompatActivity {

    TextView tv1,tv2,tv3;
    Button b1,b2;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String posts;
    Integer up=0,down=0;
    SpotsDialog dialog;
    String FLAG="0";
    String UserName,Post_Title,Waiting_Id,Post,TopicStr,Upvotes,Downvotes,Bookmark,User_Id,Post_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_wait_detail);






        tv1=findViewById(R.id.tvWait);
        tv2=findViewById(R.id.tvWait2);
        tv3=findViewById(R.id.tvWait3);
        b1=findViewById(R.id.btnDel);
        b2=findViewById(R.id.btnac);
        Intent i=getIntent();
        tv1.setText(i.getStringExtra("UserName"));
        tv2.setText(i.getStringExtra("Post_Title"));
        tv3.setText(i.getStringExtra("Post"));
        User_Id=i.getStringExtra("User_Id");
        UserName=i.getStringExtra("Post_User");
        Post=i.getStringExtra("Post");
        Post_Title=i.getStringExtra("Post_Title");
        TopicStr=i.getStringExtra("TopicStr");
        Waiting_Id=i.getStringExtra("Post_Id");
        Bookmark=i.getStringExtra("Bookmark");
        Post_Date=i.getStringExtra("Post_Date");
        Upvotes=i.getStringExtra("Upvotes");
        Downvotes=i.getStringExtra("Downvotes");

        Log.d("here","TopicStr"+TopicStr+"Waiting_Id"+Waiting_Id+"Post_Date"+Post_Date);
        Log.d("here","Post"+Post+"Upvotes"+Upvotes+"Downvotes"+Downvotes);
        Log.d("here","User_Id"+User_Id+"Post_Title"+Post_Title+"UserName"+UserName);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //delete
                FLAG="0";
                new BackgroundTask().execute();




            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FLAG="1";

                new BackgroundTask().execute();

            }
        });



    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/addAdminPost.php"; //delrte


        @Override
        protected String doInBackground(Void... voids) {

            try {
                Log.d("flag",FLAG);
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                //my
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("Post_Title","UTF-8")+"="+URLEncoder.encode(Post_Title,"UTF-8")+"&"+
                        URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(User_Id,"UTF-8")+"&"+
                        URLEncoder.encode("TopicStr","UTF-8")+"="+URLEncoder.encode(TopicStr,"UTF-8")+"&"+
                        URLEncoder.encode("Post","UTF-8")+"="+URLEncoder.encode(Post,"UTF-8")+"&"+
                        URLEncoder.encode("Post_Date","UTF-8")+"="+URLEncoder.encode(Post_Date,"UTF-8")+"&"+
                        URLEncoder.encode("FLAG","UTF-8")+"="+URLEncoder.encode(FLAG,"UTF-8")+"&"+
                        URLEncoder.encode("Waiting_Id","UTF-8")+"="+URLEncoder.encode(Waiting_Id,"UTF-8");
                Log.d("up   :    ",data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                //
               /* InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();

                while((json_string=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }

                bufferedReader.close();
                inputStream.close();*/
                httpURLConnection.disconnect();
              //  JSON_String=stringBuilder.toString().trim();
                //return stringBuilder.toString().trim();
                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
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
            //TextView tv=findViewById(R.id.tv);
            //tv.setText(result);
            JSON_String=result;
            //Toast.makeText(getContext(),"hi"+JSON_String,Toast.LENGTH_LONG).show();
            //fun();
            Toast.makeText(PostWaitDetail.this,"process done..",Toast.LENGTH_SHORT).show();


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }







}
