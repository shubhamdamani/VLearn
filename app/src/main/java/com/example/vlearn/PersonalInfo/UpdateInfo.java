package com.example.vlearn.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlearn.R;
import com.example.vlearn.USER_Class;
import com.example.vlearn.addquestion;
import com.example.vlearn.myprofile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateInfo extends AppCompatActivity {

    TextView tvEmail;
    Button btnUPD;
    EditText etName,etPass;
    String s,s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);



        tvEmail=findViewById(R.id.updemail);
        etName=findViewById(R.id.updname);
        etPass=findViewById(R.id.updpassword);
        btnUPD=findViewById(R.id.btnChange);
        tvEmail.setText(USER_Class.getLoggedUserEmail());
        etName.setText(USER_Class.getLoggedUserName());
        //etPass.setText(USER_Class.get);

        btnUPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent i=new Intent(UpdateInfo.this,FollowUser.class);
              //  startActivity(i);
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfo.this);
                builder.setTitle("Confirm Update !");
                builder.setMessage("You are about to update your details. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s=USER_Class.getLoggedUserId();
                        s1=etName.getText().toString();
                        s2=etPass.getText().toString();
                        Toast.makeText(getApplicationContext(), "Profile Updated ", Toast.LENGTH_SHORT).show();
                        if(s2=="")
                        {
                            Toast.makeText(UpdateInfo.this,"PLEASE ENTER PASSWORD",Toast.LENGTH_SHORT).show();
                        }else{

                            new UpdateInfo.BackgroundTask().execute();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Profile not Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();


            }
        });

    }

    class BackgroundTask extends AsyncTask<Void,Void,String>        //this class is background task performing which will connect
            //app to our database(php script of required) and then php script
            // will run our queryand make changes in database
    {
        String updinfo_url="https://vlearndroidrun.000webhostapp.com/updatePersonalDetails.php";



        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(updinfo_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(s,"UTF-8")+"&"+
                        URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(s1,"UTF-8")+"&"+
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(s2,"UTF-8");
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

            Toast.makeText(UpdateInfo.this,result,Toast.LENGTH_LONG).show();
            //getDatafromJSON();
             Intent i=new Intent(UpdateInfo.this, myprofile.class);
            startActivity(i);
            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}
