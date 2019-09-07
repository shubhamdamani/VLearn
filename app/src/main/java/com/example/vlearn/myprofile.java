package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vlearn.PersonalInfo.UpdateInfo;

public class myprofile extends AppCompatActivity {

    Button btnUpdInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        btnUpdInfo=findViewById(R.id.bPersonal);
        btnUpdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(myprofile.this, UpdateInfo.class);
                startActivity(i);
            }
        });
    }
}
