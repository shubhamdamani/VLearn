package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Date;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    EditText post_content,post_title;
    Button b_submit;
    String P_content,P_topic,P_title;
    String P_date,user_id;
    public String[] Topics = { "A", "B", "C", "D", "F" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        post_content=findViewById(R.id.Post_content);
        b_submit=findViewById(R.id.Post_submit);
        post_title=findViewById(R.id.Post_title);

        spinner =findViewById(R.id.topic);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Post_content_fun();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+Topics[position] ,Toast.LENGTH_SHORT).show();
        P_topic=Topics[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public void Post_content_fun()
    {
        P_content=post_content.getText().toString().trim();
        //P_topic
        user_id=USER_Class.getLoggedUserId();
        Date date = new Date();
         P_date = DateFormat.getDateTimeInstance().format(date);
        P_title=post_title.getText().toString().trim();
        Toast.makeText(AddPost.this,P_content+P_title+P_topic,Toast.LENGTH_LONG).show();
        if(!P_content.equals("")&&!P_title.equals("")&&!P_topic.equals(""))
        {
            //post question
            new AddPost.BackgroundTask().execute();


        }else{
            Toast.makeText(AddPost.this,"Enter Your Question",Toast.LENGTH_SHORT).show();
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>        //this class is background task performing which will connect app to our database(php script of required) and then php script will run our queryand make changes in database
    {
        String addQuestion_url="https://vlearndroidrun.000webhostapp.com/addPost.php";



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
                        URLEncoder.encode("Post_Topic","UTF-8")+"="+URLEncoder.encode(P_topic,"UTF-8")+"&"+
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

            return "hi";
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

            Toast.makeText(AddPost.this,result,Toast.LENGTH_LONG).show();
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

