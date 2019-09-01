package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText username,email,pass,re_pass;
    Button register;
    String r_name="",r_email="",r_pass="",ure_pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.username);
        email=findViewById(R.id.Email);
        pass=findViewById(R.id.Password);
        re_pass=findViewById(R.id.Re_Password);
        register=findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_name=username.getText().toString();
                r_email=email.getText().toString();
                r_pass=pass.getText().toString();
                ure_pass=re_pass.getText().toString();
                if(!r_name.equals("")&&!r_email.equals("")&&!r_pass.equals("")&&!ure_pass.equals(""))
                {
                    reg();
                }else{
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void reg(){

        if(r_pass.equals(ure_pass)) {


            String method="register";
            BackgroundTask backgroundTask=new BackgroundTask(getBaseContext());
            backgroundTask.execute(method,r_name,r_email,r_pass);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Enter Same Password in Both Fields",Toast.LENGTH_SHORT).show();
        }
    }
}
