package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.adapter.post_comment_adapter;
import com.example.vlearn.object.getComment_data;

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

import retrofit2.http.POST;

public class PostDetail extends AppCompatActivity  {

    TextView tvUsername,tvArtical,tvTitle;
    Button B_up,B_down,B_post,B_bookmark;
    String username,artical,title,Post_Id;
    EditText txt_comment;
    String PostComment;
    public RecyclerView recyclerView;
    String json_string,JSON_String;                 //this activity is showing answer of particular question and giving option to add answer
    JSONArray jsonArray;
    JSONObject jsonObject;
    List<getComment_data> getCommentData;
    post_comment_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        tvUsername=findViewById(R.id.tvPDUname);
        tvTitle=findViewById(R.id.tvPDTitle);
        tvArtical=findViewById(R.id.tvPDPost);
        B_up=findViewById(R.id.btnPDUp);
        B_down=findViewById(R.id.btnPDDown);
        B_bookmark=findViewById(R.id.bBookmark);

        B_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bookmark_fun();
            }
        });

        B_post=findViewById(R.id.postcomment);
        txt_comment=findViewById(R.id.editcomment);

        recyclerView = (RecyclerView) findViewById(R.id.post_detail_recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i=getIntent();
        username=i.getStringExtra("Post_User");
        artical=i.getStringExtra("Post");
        title=i.getStringExtra("Post_Title");
        Post_Id=i.getStringExtra("Post_Id");
        Toast.makeText(PostDetail.this,Post_Id,Toast.LENGTH_SHORT).show();
        /*getCommentData=new ArrayList<>();
        getComment_data contacts=new getComment_data("user_name","p_comment");
        getCommentData.add(contacts);
        adapter = new post_comment_adapter(this, getCommentData);
        recyclerView.setAdapter(adapter);*/

        tvUsername.setText(username);
        tvArtical.setText(artical);
        tvTitle.setText(title);
        new BackgroundTask().execute();

        B_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostComment=txt_comment.getText().toString().trim();
                postfun();

            }
        });

    }
    public void Bookmark_fun()
    {

        String method="Bookmark";
        com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
        backgroundTask.execute(method,USER_Class.getLoggedUserId(),Post_Id," ");
    }
    public void postfun()
    {

        String method="addComment";
        if(PostComment.equals(""))
        {
            Toast.makeText(PostDetail.this,"plese add anser",Toast.LENGTH_SHORT).show();
            return;
        }
        com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
        backgroundTask.execute(method,USER_Class.getLoggedUserId(),Post_Id,PostComment);

    }
    //RETRIEVE DETAILS or POst comments
    public void getDatafromJSON()
    {
        Toast.makeText(PostDetail.this,"hio"+JSON_String,Toast.LENGTH_LONG).show();
        String user_name,p_comment;
         getCommentData=new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                user_name=jo.getString("UserName");
                p_comment=jo.getString("Comment");

                Toast.makeText(PostDetail.this,"hi"+user_name+p_comment,Toast.LENGTH_LONG).show();
                getComment_data contacts=new getComment_data(user_name,p_comment);
                getCommentData.add(contacts);
                adapter = new post_comment_adapter(this, getCommentData);
                recyclerView.setAdapter(adapter);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>            //answer upload karega
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getComments.php";


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
                String data= URLEncoder.encode("Post_Id","UTF-8")+"="+URLEncoder.encode(Post_Id,"UTF-8");
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

            JSON_String=result;

            Toast.makeText(PostDetail.this,"gghg"+JSON_String,Toast.LENGTH_LONG).show();
            getDatafromJSON();

            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
