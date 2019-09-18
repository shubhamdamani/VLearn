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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import dmax.dialog.SpotsDialog;

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
    SpotsDialog dialog;
    Question_adapter adapter;
    private RecyclerView recyclerView;
    List<questionfetch> mquestionfetch;
    Button askQuestion;
    FloatingActionButton fab;



    public QuesFragment(){} //CONSTRUCTOR

   // View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        new BackgroundTask().execute();
        dialog=new SpotsDialog(getContext());
        dialog.show();//jaise hi is activity pe aaye, question fetch ho jaaye

       // getActivity().onBackPressed();

        View v= inflater.inflate(R.layout.fragment_ques,container,false);

        recyclerView = v.findViewById(R.id.my_recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fab =  v.findViewById(R.id.add_ques);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //  .setAction("Action", null).show();
                Intent i=new Intent(getContext(),addquestion.class);
                startActivity(i);
            }
        });

        return v;





    }

    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        json_string=JSON_String;

        String Topic,Q_Id,Question,User_Id;



        mquestionfetch =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");       //THIS IS NAME OF OUR JSON ARRAY

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                Topic=jo.getString("Topic");                        // ARRAY KA SUB-TAG, MATLAB KEY OF REQIRED VALUE
                User_Id=jo.getString("User_Id");
                Q_Id=jo.getString("Q_Id");
                Question=jo.getString("Question");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                mquestionfetch.add(contacts);
                adapter = new Question_adapter(getContext(), mquestionfetch);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }
            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


  class BackgroundTask extends AsyncTask<Void,Void,String>
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
            try{JSON_String=result;
            fun();}catch (Exception e)
            {
                dialog.dismiss();
                Snackbar snackbar=Snackbar.make(getActivity().findViewById(R.id.drawer_layout),"No Internet Connection",Snackbar.LENGTH_LONG);
                //Toast.makeText(getContext(),"no internet",Toast.LENGTH_SHORT).show();
                snackbar.show();
            }


            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
