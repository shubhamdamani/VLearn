package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vlearn.Admin.AdminBasic;

public class AboutVLearn extends AppCompatActivity {

    TextView secret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vlearn);

        secret=findViewById(R.id.btnsecret);
        //secret.setVisibility(View.INVISIBLE);
        //secret.setEnabled(true);
        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s=USER_Class.getLoggedUserId();
                if(s.equals("1"))
                {
                    Intent i=new Intent(AboutVLearn.this, AdminBasic.class);
                    startActivity(i);
                }

            }
        });

    }
    @Override
    public void onBackPressed(){

        startActivity(new Intent(AboutVLearn.this,MainActivity.class));
        finish();
        return;
    }

}
