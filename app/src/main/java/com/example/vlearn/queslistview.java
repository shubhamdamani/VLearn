package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class queslistview extends AppCompatActivity {

    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    questionadapter contactAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queslistview);


        listView=findViewById(R.id.listquestion);
        contactAdapter=new questionadapter(this,R.layout.ques_layout);
        listView.setAdapter(contactAdapter);
        json_string=getIntent().getExtras().getString("json_data");
        String Topic,Q_Id,Question,User_Id;


        try {
            jsonObject=new JSONObject(json_string);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                Topic=jo.getString("Topic");
                User_Id=jo.getString("User_Id");
                Q_Id=jo.getString("Q_Id");
                Question=jo.getString("Question");

                questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                contactAdapter.add(contacts);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }










    }
}
