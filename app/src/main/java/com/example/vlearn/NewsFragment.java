package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    public static final String CHECK_URL="https://vlearndroidrun.000webhostapp.com/check_user.php";
    public static final String KEY_NAME="name";
    public static final String KEY_EMAIL="email";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="name";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";

    String retrieve;
    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    Integer USER_ID=1;
    JSONObject jsonObject;
    // questionadapter contactAdapter;
    Question_adapter adapter;
    private RecyclerView recyclerView;
    List<questionfetch> mquestionfetch;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View v= inflater.inflate(R.layout.fragment_news,container,false);
        met();


        return v;
    }

    public void ret()
    {
        Integer integer=77;
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(integer,USER_ID);
    }


    public void met()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //retrieve=response.trim();
                        //JSON_String=retrieve;
                        ret();

                       // fun();

                       /* if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            Toast.makeText(getContext(),"User already exists ",Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                          //  editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            //editor.putString(EMAIL_SHARED_PREF, name);

                            editor.commit();
                            //  Intent intent = new Intent(register.this, login.class);
                            //startActivity(intent);
                        }else{
                            //reg();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                //  prams.put(KEY_NAME, name);
                //prams.put(KEY_EMAIL, Email);

                return prams;
            }
        };
        //    RequestQueue requestQueue = Volley.newRequestQueue(this);
        //   requestQueue.add(stringRequest);



    }

    public void fun()
    {
        json_string=JSON_String;

        String Topic,Q_Id,Question,User_Id;
        //String



        mquestionfetch =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                retrieve=jo.getString("TopicStr");              //YAHA PE TOPIC VAALI STRING LI H JSON FORMAT SE CONVERT HO GAYI HAI


                /*Topic=jo.getString("Topic");
                User_Id=jo.getString("User_Id");
                Q_Id=jo.getString("Q_Id");
                Question=jo.getString("Question");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                mquestionfetch.add(contacts);
                adapter = new Question_adapter(getContext(), mquestionfetch);
                recyclerView.setAdapter(adapter);*/
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

   //aage vaala comment galat h     //SHAYAD YE CLASS USE NH HOGI BECAUSE JAB POST KARENGE TABHI ECHO LE LENGE

    /*/*  static*/ class BackgroundTask extends AsyncTask<Integer,Void,String> ///KOI DIKKAT AAE TOH STATIC HATA DENA
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserTopics.php";


        @Override
        protected String doInBackground(Integer... integers) {

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
