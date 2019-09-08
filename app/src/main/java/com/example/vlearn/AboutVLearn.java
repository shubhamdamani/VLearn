package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vlearn.Admin.AdminBasic;

public class AboutVLearn extends AppCompatActivity {

    Button secret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vlearn);

        secret=findViewById(R.id.btnsecret);
        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s=USER_Class.getLoggedUserEmail();
                if(s.equals("damani.shubham4@gmail.com") || s.equals("utkarsh3210@gmail.com") || s.equals("Email"))
                {
                    Intent i=new Intent(AboutVLearn.this, AdminBasic.class);
                    startActivity(i);
                }

            }
        });

    }
}
