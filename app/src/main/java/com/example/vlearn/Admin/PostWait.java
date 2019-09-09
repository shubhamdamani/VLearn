package com.example.vlearn.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.vlearn.Post_content;
import com.example.vlearn.R;
import com.example.vlearn.USER_Class;
import com.example.vlearn.adapter.PostWaitAdapter;
import com.example.vlearn.post_adapter;

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

public class PostWait extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    PostWaitAdapter adapter;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String posts;
    Integer up=0,down=0;
    SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_wait);
        recyclerView = findViewById(R.id.Apost_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new BackgroundTask().execute();




    }


    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        json_string=JSON_String;

        String Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,Waiting_Id;
        Integer Upvotes,Downvotes;//Bookmark;



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
               // Post_Id=jo.getString("Post_Id");
               // Upvotes=jo.getInt("Upvotes");
                //Downvotes=jo.getInt("Downvotes");
                Topic=jo.getString("TopicStr");
                UserName=jo.getString("UserName");
                Waiting_Id=jo.getString("Waiting_Id");
                //String  Bookmark=jo.getString("BookmarkStatus");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(Waiting_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,1,1,1);
                mPostContent.add(contacts);
                adapter = new PostWaitAdapter(this, mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }
            //dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getAdminPosts.php";


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
                //
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
            fun();


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
