package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dmax.dialog.SpotsDialog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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

import static java.lang.Character.toUpperCase;

public class addquestion extends AppCompatActivity {
    TextView prof_icon;
    EditText Q_txt;
    Button b_Post;
    String QTxt;
    SpotsDialog dialog;
    ImageButton btn_close;
    private RelativeLayout addQuestionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Q_txt=findViewById(R.id.Q_txt);
        b_Post=findViewById(R.id.Q_post);
        prof_icon=findViewById(R.id.prof_icon);
        btn_close=findViewById(R.id.close_btn);
        addQuestionView=findViewById(R.id.addQuestionView);
        dialog=new SpotsDialog(this);

        String username=USER_Class.getLoggedUserName();
        Character name=username.charAt(0);
        name=toUpperCase(name);
        prof_icon.setText(name.toString());

        b_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                QTxt=Q_txt.getText().toString().trim();
                PostQuestion();                 // post question to database
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void PostQuestion()
    {

        if(!QTxt.equals(""))
        {
            //post question
            new addquestion.BackgroundTask().execute();


        }else{
            dialog.dismiss();
            Toast.makeText(addquestion.this,"Enter Your Question",Toast.LENGTH_SHORT).show();
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>        /*this class is performing background task which will connect
                                                                    app to our databasethrough php script*/
    {
        String addQuestion_url="https://vlearndroidrun.000webhostapp.com/addQuestion.php";



        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(addQuestion_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8")+"&"+
                        URLEncoder.encode("Q_text","UTF-8")+"="+URLEncoder.encode(QTxt,"UTF-8");
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
            dialog.dismiss();

           if(result==null){
               Snackbar snackbar=Snackbar.make(addQuestionView,"No Internet Connection",Snackbar.LENGTH_LONG);
               snackbar.show();
           }else{
               Snackbar snackbar=Snackbar.make(addQuestionView,"Question Uploaded",Snackbar.LENGTH_LONG);
               snackbar.show();
               finish();
           }



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
