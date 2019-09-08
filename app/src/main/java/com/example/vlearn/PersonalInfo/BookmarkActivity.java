package com.example.vlearn.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vlearn.PostDetail;
import com.example.vlearn.Post_content;
import com.example.vlearn.R;
import com.example.vlearn.USER_Class;
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

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    post_adapter adapter;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView=findViewById(R.id.Bookmark_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new BackgroundTask().execute();


    }
    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        //json_string=JSON_String;
        //Toast.makeText(BookmarkActivity.this,"hio"+JSON_String,Toast.LENGTH_LONG).show();
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
                User_Id=jo.getString("UCurrent_Id");
                Post_content=jo.getString("Post");
                Post_Title=jo.getString("Post_Title");
                Post_Date=jo.getString("Post_Date");
                Post_Id=jo.getString("Post_Id");
                Upvotes=jo.getInt("Upvotes");
                Downvotes=jo.getInt("Downvotes");
                Topic=jo.getString("TopicStr");
                UserName=jo.getString("UserName");
                int bookmark=jo.getInt("BookmarkStatus");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,Upvotes,Downvotes,bookmark);
                mPostContent.add(contacts);
                adapter = new post_adapter(getApplicationContext(), mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserBookmarks.php";


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

            //Toast.makeText(BookmarkActivity.this, "userid"+USER_Class.getLoggedUserId(), Toast.LENGTH_SHORT).show();
            JSON_String=result;
            //Toast.makeText(BookmarkActivity.this,"hio"+JSON_String,Toast.LENGTH_LONG).show();
            fun();


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
