package com.example.vlearn;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuesFragment extends Fragment {

    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    JSONObject jsonObject;
    // questionadapter contactAdapter;
    Question_adapter adapter;
    private RecyclerView recyclerView;
    List<questionfetch> mquestionfetch;
    Button askQuestion;



    public QuesFragment(){} //CONSTRUCTOR

   // View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        new BackgroundTask().execute();



        /*return*/View v= inflater.inflate(R.layout.fragment_ques,container,false);

        recyclerView = v.findViewById(R.id.my_recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        askQuestion=v.findViewById(R.id.ask_ques);
        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),addquestion.class);



                startActivity(i);
            }
        });

      //  fun();




        return v;





    }

    public void fun()
    {
        json_string=JSON_String;

        String Topic,Q_Id,Question,User_Id;



        mquestionfetch =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                Topic=jo.getString("Topic");
                User_Id=jo.getString("User_Id");
                Q_Id=jo.getString("Q_Id");
                Question=jo.getString("Question");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                mquestionfetch.add(contacts);
                adapter = new Question_adapter(getContext(), mquestionfetch);
                recyclerView.setAdapter(adapter);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


 /*  static*/ class BackgroundTask extends AsyncTask<Void,Void,String> ///KOI DIKKAT AAE TOH STATIC HATA DENA
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/question.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
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
            //TextView tv=findViewById(R.id.tv);
            //tv.setText(result);
            JSON_String=result;
            fun();


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
