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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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

import static java.lang.Character.toUpperCase;

public class QuestionDetail extends AppCompatActivity {

    String Question, Q_Id, User_Id, Topic;

    String json_string,JSON_String;                 //this activity is showing answer of particular question and giving option to add answer
    JSONArray jsonArray;
    JSONObject jsonObject;
    TextView txt_quesDisp,txt_user,profile_icon;
    List<AnswerForQuestionCard> mquestionfetch;
    AnswerForQuestionAdapter adapter;
    private RecyclerView recyclerView;
    EditText editanswer;
    Button postanswer;

    String answer;
    RelativeLayout rl;
    public static final String POST_ANSWER_URL="https://vlearndroidrun.000webhostapp.com/addAnswer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);


        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.ques_detail_recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rl=findViewById(R.id.quesdet);

        editanswer=findViewById(R.id.editanswer);
        postanswer=findViewById(R.id.postanswer);
        txt_user=findViewById(R.id.username);
        profile_icon=findViewById(R.id.prof_icon);




        postanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer=editanswer.getText().toString();
                postfun();

            }
        });
        txt_quesDisp=findViewById(R.id.myQuestion);
        Bundle bundle = getIntent().getExtras();
        Question = bundle.getString("Question");
        Q_Id = bundle.getString("Q_Id");
        User_Id = bundle.getString("User_Id");
        Topic = bundle.getString("Topic");
        txt_user.setText(User_Id); //User_Id==UserName;
        txt_quesDisp.setText(Question);
        Character name=User_Id.charAt(0);
        name=toUpperCase(name);
        profile_icon.setText(name.toString());


        new BackgroundTask().execute();
       //getDatafromJSON() in PostExecute Method Below;


    }



    // This method is used to post questions

    public void postfun()
    {

        String method="addanswer";
        if(answer.trim().equals(""))
        {
            Toast.makeText(QuestionDetail.this,"please add answer",Toast.LENGTH_SHORT).show();
            return;
        }
       com.example.vlearn.BackgroundTask backgroundTask=new com.example.vlearn.BackgroundTask(getBaseContext());
        backgroundTask.execute(method,Q_Id,answer,USER_Class.getLoggedUserId());
        editanswer.setText("");
        hideKeyboard(QuestionDetail.this);
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

    //RETRIEVE DETAILS of Answers
    public void getDatafromJSON()
    {
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
                AnswerForQuestionCard contacts=new AnswerForQuestionCard(answer,user_Id,user_name);
                mquestionfetch.add(contacts);
                adapter = new AnswerForQuestionAdapter(this, mquestionfetch);
                recyclerView.setAdapter(adapter);
                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>            //uploads answers
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getAnswer.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("Q_Id","UTF-8")+"="+URLEncoder.encode(Q_Id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

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

            try{
                JSON_String=result;
                getDatafromJSON();
            }catch (Exception e){

                Snackbar snackbar=Snackbar.make(rl,"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}