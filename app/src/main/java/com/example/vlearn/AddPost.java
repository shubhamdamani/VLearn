package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.object.key_Topic;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Character.toUpperCase;
import static okhttp3.internal.http.HttpDate.format;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    EditText post_content,post_title;
    TextView prof_icon;
    Button b_submit;
    ImageButton btn_close;
    String P_content,P_topic,P_title;
    String P_date,user_id;
    private RelativeLayout addPostView;
    SpotsDialog dialog;
    public String[] Topics = { "Select Topic","Physics","Maths","Computer", "Science", "Politics", "Business", "Technology" ,"Sports"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        post_content=findViewById(R.id.Post_content);
        b_submit=findViewById(R.id.Post_submit);
        post_title=findViewById(R.id.Post_title);
        prof_icon=findViewById(R.id.prof_icon);
        btn_close=findViewById(R.id.close_addpost);
        dialog=new SpotsDialog(this);

        addPostView=findViewById(R.id.addPostView);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String username=USER_Class.getLoggedUserName();

        Character name=username.charAt(0);
        name=toUpperCase(name);
        prof_icon.setText(name.toString());
        spinner =findViewById(R.id.topic);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                Post_content_fun();
                hideKeyboard(AddPost.this);
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        P_topic=Topics[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public void Post_content_fun()
    {
        P_content=post_content.getText().toString().trim();
       // P_topic=post_content.getText().toString().trim();
        user_id=USER_Class.getLoggedUserId();
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        P_date=sdf.format(date);
        // P_date = DateFormat.getDateTimeInstance().format(date);
        P_title=post_title.getText().toString().trim();
        if(!P_content.equals("")||!P_title.equals("")||!P_topic.equals(""))
        {
            //post question
            new AddPost.BackgroundTask().execute();


        }else{
            dialog.dismiss();
            Toast.makeText(AddPost.this,"Enter Your Question",Toast.LENGTH_SHORT).show();
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>        //this class is background task performing which will connect app to our database(php script of required) and then php script will run our queryand make changes in database
    {
        String addPost_url="https://vlearndroidrun.000webhostapp.com/addToWaitingPosts.php";



        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(addPost_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8")+"&"+
                        URLEncoder.encode("Post_Topic","UTF-8")+"="+URLEncoder.encode(key_Topic.getTopicToKey(P_topic),"UTF-8")+"&"+
                        URLEncoder.encode("Post_Title","UTF-8")+"="+URLEncoder.encode(P_title,"UTF-8")+"&"+
                        URLEncoder.encode("Post_Content","UTF-8")+"="+URLEncoder.encode(P_content,"UTF-8")+"&"+
                        URLEncoder.encode("Post_Date","UTF-8")+"="+URLEncoder.encode(P_date,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Posted Successfully";


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

            try {
                dialog.dismiss();
                if (result.equals("Posted Successfully")) {
                    Snackbar snackbar=Snackbar.make(addPostView,"Uploaded to waitlist",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    finish();
                }
            }catch (Exception e){
                dialog.dismiss();
                Snackbar snackbar=Snackbar.make(addPostView,"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}

