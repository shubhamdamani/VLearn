package com.example.vlearn;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.PersonalInfo.FollowUser;
import com.example.vlearn.adapter.post_comment_adapter;
import com.example.vlearn.object.getComment_data;
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

import dmax.dialog.SpotsDialog;
import retrofit2.http.POST;

public class PostDetail extends AppCompatActivity  {

    TextView tvUsername,tvArtical,tvTitle;
    Button B_up,B_down,B_post,B_bookmark,share;
    MaterialFavoriteButton B_mark;
    String username,artical,title,Post_Id;
    EditText txt_comment;
    SpotsDialog dialog;
    String PostComment;
    public RecyclerView recyclerView;
    String json_string,JSON_String;                 //this activity is showing answer of particular question and giving option to add answer
    JSONArray jsonArray;
    JSONObject jsonObject;
    List<getComment_data> getCommentData;
    post_comment_adapter adapter;
    String book_state,sendpost,sendTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        tvUsername=findViewById(R.id.tvPDUname);
        tvTitle=findViewById(R.id.tvPDTitle);
        tvArtical=findViewById(R.id.tvPDPost);
        B_up=findViewById(R.id.btnPDUp);
        B_down=findViewById(R.id.btnPDDown);
        share=findViewById(R.id.share);

        B_bookmark=findViewById(R.id.bBookmark);

        Intent intent=getIntent();
        username=intent.getStringExtra("Post_User");
        Post_Id=intent.getStringExtra("User_Id");
        book_state=intent.getStringExtra("Bookmark");
        sendpost=intent.getStringExtra("Post");
        sendTitle=intent.getStringExtra("Post_Title");
        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(PostDetail.this, FollowUser.class);

                j.putExtra("User_Id",Post_Id);
                j.putExtra("UserName",username);
                startActivity(j);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "AUTHOR:"+username+"\n"+"TITLE:"+sendTitle+"POST:"+sendpost);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);


            }
        });

        B_mark=(MaterialFavoriteButton)findViewById(R.id.bkmark);
        dialog=new SpotsDialog(this);
        dialog.show();

        B_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String method="upvote";
                com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
                backgroundTask.execute(method,USER_Class.getLoggedUserId(),Post_Id,"");

            }
        });
        B_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method="downvote";
                com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
                backgroundTask.execute(method,USER_Class.getLoggedUserId(),Post_Id,"");

            }
        });
        boolean state=false;
        if(book_state.equals("1")){
            state=true;
        }
        B_mark.setFavorite(state);
        B_mark.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                if(favorite)
                    Bookmark_fun();
                else{
                    String method="unBookmark";
                    com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
                    backgroundTask.execute(method,USER_Class.getLoggedUserId(),Post_Id," ");
                }

            }
        });
        B_mark.setOnFavoriteAnimationEndListener(new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
            @Override
            public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {



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
        txt_comment.setText("");
        hideKeyboard(PostDetail.this);
        new BackgroundTask().execute();


    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
            dialog.dismiss();

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
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
