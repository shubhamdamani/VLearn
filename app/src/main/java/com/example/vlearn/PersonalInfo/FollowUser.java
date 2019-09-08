package com.example.vlearn.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.Post_content;
import com.example.vlearn.R;
import com.example.vlearn.USER_Class;
import com.example.vlearn.adapter.post_comment_adapter;
import com.example.vlearn.object.getComment_data;
import com.example.vlearn.post_adapter;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

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
import java.util.List;

public class FollowUser extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    post_adapter adapter;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String JSON_String1,json_string1;
    JSONArray jsonArray1;
    JSONObject jsonObject1;
    String posts;
    Integer up=0,down=0;
    SpotsDialog dialog;
    String fusername,fuid;
    Button foll;
    TextView tv,tv1,tv2;
    //String  f1,f2;

    public FollowUser(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_user);
        recyclerView=findViewById(R.id.follow_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent in=getIntent();
        fusername=in.getStringExtra("UserName");
        tv=findViewById(R.id.followuname);
        tv1=findViewById(R.id.followers);
        tv2=findViewById(R.id.following);
        foll=findViewById(R.id.btnfollow);
        foll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Follow().execute();
            }
        });

        tv.setText(fusername);
        dialog=new SpotsDialog(this);
        dialog.show();
       // dialog.show();
        fuid=in.getStringExtra("User_Id");
        //Toast.makeText(FollowUser.this,fusername+" "+fuid,Toast.LENGTH_SHORT).show();

        new Back().execute();
        new BackgroundTask().execute();






    }


    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        json_string=JSON_String;

        String Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName;
        Integer Upvotes,Downvotes;



        mPostContent =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");       //THIS IS NAME OF OUR JSON ARRAY

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);  // ARRAY KA SUB-TAG, MATLAB KEY OF REQIRED VALUE
                User_Id=jo.getString("User_Id");
                Post_content=jo.getString("Post");
                Post_Title=jo.getString("Post_Title");
                Post_Date=jo.getString("Post_Date");
                Post_Id=jo.getString("Post_Id");
                Upvotes=jo.getInt("Upvotes");
                Downvotes=jo.getInt("Downvotes");
                Topic=jo.getString("TopicStr");
                UserName=jo.getString("UserName");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,Upvotes,Downvotes);
                mPostContent.add(contacts);
                adapter = new post_adapter(FollowUser.this, mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }

            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void fun1()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        Toast.makeText(FollowUser.this,"hi"+JSON_String1,Toast.LENGTH_SHORT).show();
        json_string1=JSON_String1;

        String Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName;
        Integer Upvotes,Downvotes;



       // mPostContent =new ArrayList<>();

        try {
            jsonObject1=new JSONObject(JSON_String1);


            int count=0;
            jsonArray1=jsonObject1.getJSONArray("server_response");       //THIS IS NAME OF OUR JSON ARRAY

            while(count<jsonArray1.length())
            {
                JSONObject jo=jsonArray1.getJSONObject(count);  // ARRAY KA SUB-TAG, MATLAB KEY OF REQIRED VALUE
                tv1.setText("followers"+jo.getString("Followers"));
                tv2.setText("following"+jo.getString("Following"));
               /* Post_Title=jo.getString("Post_Title");
                Post_Date=jo.getString("Post_Date");
                Post_Id=jo.getString("Post_Id");
                Upvotes=jo.getInt("Upvotes");
                Downvotes=jo.getInt("Downvotes");
                Topic=jo.getString("TopicStr");
                UserName=jo.getString("UserName");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,Upvotes,Downvotes);
                mPostContent.add(contacts);
                adapter = new post_adapter(FollowUser.this, mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);*/
                count++;


            }
            //dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class Back extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserFollow.php";

        @Override
        protected String doInBackground(Void... voids) {


            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(fuid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();

                while((json_string1=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string1+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        public Back(){
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

            JSON_String1=result;
          //  Toast.makeText(FollowUser.this,JSON_String1,Toast.LENGTH_SHORT).show();
            fun1();

           // dialog.dismiss();




            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserPosts.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(fuid,"UTF-8");
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

                return stringBuilder.toString().trim();


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
            fun();






            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    class Follow extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/follow.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8")+"&"+
                        URLEncoder.encode("FUser_Id","UTF-8")+"="+URLEncoder.encode(fuid,"UTF-8");
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

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        public Follow()
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
           // JSON_String=result;
            //fun();
            Toast.makeText(FollowUser.this,"User Followed",Toast.LENGTH_SHORT).show();






            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
