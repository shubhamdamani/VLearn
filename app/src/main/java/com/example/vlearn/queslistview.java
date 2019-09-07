package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//UNUSED ACTIVITY

public class queslistview extends AppCompatActivity {

    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
   // questionadapter contactAdapter;
    Question_adapter adapter;
    private RecyclerView recyclerView;
    List<questionfetch> mquestionfetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queslistview);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        json_string=getIntent().getExtras().getString("json_data");
        String Topic,Q_Id,Question,User_Id;
        mquestionfetch =new ArrayList<>();

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

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                mquestionfetch.add(contacts);
                adapter = new Question_adapter(this, mquestionfetch);
                recyclerView.setAdapter(adapter);
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }










    }
}
