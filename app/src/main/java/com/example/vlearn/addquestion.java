package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class addquestion extends AppCompatActivity {
    TextView tag;
    EditText Q_txt;
    Button b_Post;
    String QTxt;
    USER_Class User_obj;        //unused object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        tag=findViewById(R.id.tag);
        Q_txt=findViewById(R.id.Q_txt);
        b_Post=findViewById(R.id.Q_post);
      //  User_obj=new USER_Class();
        Toast.makeText(addquestion.this,"user"+USER_Class.getLoggedUserId(),Toast.LENGTH_SHORT).show();

        b_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QTxt=Q_txt.getText().toString().trim();
                PostQuestion();                 // post question to database
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
            Toast.makeText(addquestion.this,"Enter Your Question",Toast.LENGTH_SHORT).show();
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>        //this class is background task performing which will connect
                                                                    //app to our database(php script of required) and then php script
                                                                    // will run our queryand make changes in database
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

            return "jgfksg";
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

            //JSON_String=result;

             Toast.makeText(addquestion.this,result,Toast.LENGTH_LONG).show();
            //getDatafromJSON();
           // Intent i=new Intent(login.this,MainActivity.class);
            //startActivity(i);
            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
