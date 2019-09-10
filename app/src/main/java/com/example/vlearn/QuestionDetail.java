package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

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

import com.google.firebase.firestore.auth.User;

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

public class QuestionDetail extends AppCompatActivity {

    String Question, Q_Id, User_Id, Topic;
    SpotsDialog dialog;
    String json_string,JSON_String;                 //this activity is showing answer of particular question and giving option to add answer
    JSONArray jsonArray;
    JSONObject jsonObject;
    TextView txt_quesDisp,txt_user;
    List<AnswerForQuestionCard> mquestionfetch;
    AnswerForQuestionAdapter adapter;
    private RecyclerView recyclerView;
    EditText editanswer;
    Button postanswer;
  //  SpotsDialog dialog;
    String answer;
    public static final String POST_ANSWER_URL="https://vlearndroidrun.000webhostapp.com/addAnswer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        dialog=new SpotsDialog(this);
        //dialog.show();
        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.ques_detail_recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editanswer=findViewById(R.id.editanswer);
        postanswer=findViewById(R.id.postanswer);
        txt_user=findViewById(R.id.username);


        postanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer=editanswer.getText().toString();
                Toast.makeText(QuestionDetail.this,answer,Toast.LENGTH_LONG).show();
                postfun();
               // Intent i=new Intent(QuestionDetail.this,QuestionDetail.class);
                //startActivity(i);
            }
        });
        txt_quesDisp=findViewById(R.id.myQuestion);
        Bundle bundle = getIntent().getExtras();
        Question = bundle.getString("Question");
        Q_Id = bundle.getString("Q_Id");
        User_Id = bundle.getString("User_Id");
        Topic = bundle.getString("Topic");
        txt_user.setText(User_Id);

        txt_quesDisp.setText(Question);



        Toast.makeText(this,Question+" "+Q_Id+ " "+User_Id+" "+Topic,Toast.LENGTH_LONG).show();

        new BackgroundTask().execute();
       //getDatafromJSON() in PostExecute Method Below;


    }



    // ANSWER POST KRNE VALA METHOD POSTFUN

    public void postfun()
    {

        String method="addanswer";
        if(answer.trim().equals(""))
        {
            Toast.makeText(QuestionDetail.this,"plese add anser",Toast.LENGTH_SHORT).show();
            return;
        }Toast.makeText(QuestionDetail.this,Q_Id+"h"+answer+"_"+User_Id,Toast.LENGTH_SHORT).show();

       com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
        hideKeyboard(QuestionDetail.this);
        backgroundTask.execute(method,Q_Id.toString(),answer,USER_Class.getLoggedUserId());

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

    //RETRIEVE DETAILS or Answers
    public void getDatafromJSON()
    {
        Toast.makeText(QuestionDetail.this,"hio"+JSON_String,Toast.LENGTH_LONG).show();
        String answer,user_Id;
        String user_name,q_Id,ans_Id;
        mquestionfetch =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                ans_Id=jo.getString("Ans_Id");
                user_Id=jo.getString("User_Id");
                q_Id=jo.getString("Q_Id");
                user_name=jo.getString("ans_username");
                answer=jo.getString("Answer");
                Toast.makeText(QuestionDetail.this,"answer:"+answer+user_Id,Toast.LENGTH_LONG).show();
                AnswerForQuestionCard contacts=new AnswerForQuestionCard(answer,user_Id,user_name);
                mquestionfetch.add(contacts);
                adapter = new AnswerForQuestionAdapter(this, mquestionfetch);
                recyclerView.setAdapter(adapter);
                count++;


            }
//            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>            //answer upload karega
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getAnswer.php";


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
                String data= URLEncoder.encode("Q_Id","UTF-8")+"="+URLEncoder.encode(Q_Id,"UTF-8");
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

            Toast.makeText(QuestionDetail.this,JSON_String,Toast.LENGTH_LONG).show();
            getDatafromJSON();

            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}