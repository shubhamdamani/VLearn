package com.example.vlearn.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vlearn.R;

public class AdminBasic extends AppCompatActivity {

    Button wait,acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_basic);

        wait=findViewById(R.id.waitlist);
        //acc=findViewById(R.id.accepted);
        wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminBasic.this,PostWait.class);
                startActivity(i);
            }
        });

      /*  acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminBasic.this,PostAcc.class);
                startActivity(i);
            }
        });*/
    }
}
