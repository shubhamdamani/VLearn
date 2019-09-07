package com.example.vlearn;

import android.content.Context;
import android.os.AsyncTask;
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

public class BackgroundTask extends AsyncTask<String,Void,String> {     //this is for online communication in background
                                                                        //alag thread chalegi background

    Context ctx;
    public BackgroundTask(Context ctx) {
        this.ctx=ctx;
    }
    protected String doInBackground(String... params) {


        String method=params[0];
        if(method.equals("register")){
            String reg_url="https://vlearndroidrun.000webhostapp.com/register.php";
            String name=params[1];
            String email=params[2];
            String password=params[3];

            try {

                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);                        //encoding data and the uploading
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Registration success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("addanswer"))
        {
            String ans_url="https://vlearndroidrun.000webhostapp.com/addAnswer.php";
            String qid=params[1];
            String answer=params[2];
            String userid=params[3];

            try {

                URL url=new URL(ans_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("Q_id","UTF-8")+"="+URLEncoder.encode(qid,"UTF-8")+"&"+
                        URLEncoder.encode("answer","UTF-8")+"="+URLEncoder.encode(answer,"UTF-8")+"&"+
                        URLEncoder.encode("U_id","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Answer Posted";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("addComment"))
        {
            String ans_url="https://vlearndroidrun.000webhostapp.com/addComment.php";
            String user_id=params[1];
            String post_id=params[2];
            String post_comment=params[3];

            try {

                URL url=new URL(ans_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                        URLEncoder.encode("Post_Id","UTF-8")+"="+URLEncoder.encode(post_id,"UTF-8")+"&"+
                        URLEncoder.encode("Comment","UTF-8")+"="+URLEncoder.encode(post_comment,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Comment Posted";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "hi";
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

