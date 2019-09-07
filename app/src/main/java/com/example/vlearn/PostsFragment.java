package com.example.vlearn;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    post_adapter adapter;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String posts;
    Integer up=0,down=0;

    Button B_postArtical;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_posts,container,false);
        new PostsFragment.BackgroundTask().execute();
        B_postArtical=v.findViewById(R.id.post_artical);
        recyclerView = v.findViewById(R.id.post_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        B_postArtical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddPost.class));
            }
        });


        /*mPostContent =new ArrayList<>();
        Post_content contacts=new Post_content("vivek","hello World",10,2);
        mPostContent.add(contacts);
        adapter = new post_adapter(getContext(), mPostContent);
        adapter = new post_adapter(getContext(), mPostContent);
        contacts=new Post_content("vivek1","hello World1",1,2);
        mPostContent.add(contacts);
        adapter = new post_adapter(getContext(), mPostContent);
        recyclerView.setAdapter(adapter);

        */

        return v;
    }

    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        json_string=JSON_String;

        String TopicStr,Post_Id,Post_Title,Post,Post_Date,User_Id;
        Integer Upvotes,Downvotes;



        mPostContent =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");       //THIS IS NAME OF OUR JSON ARRAY

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                TopicStr=jo.getString("TopicStr");                        // ARRAY KA SUB-TAG, MATLAB KEY OF REQIRED VALUE
                User_Id=jo.getString("User_Id");
                Post=jo.getString("Post");
                Post_Date=jo.getString("Post_Date");
                Post_Id=jo.getString("Post_Id");
                Upvotes=jo.getInt("Upvotes");
                Downvotes=jo.getInt("Downvotes");


                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(User_Id,TopicStr,Upvotes,Downvotes);
                mPostContent.add(contacts);
                adapter = new post_adapter(getContext(), mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getPosts.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
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


}
