package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText username,pass;
    Button Login;
    String uname="",upass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.L_username);
        pass=findViewById(R.id.L_password);
        Login=findViewById(R.id.login);

        uname=username.getText().toString();
        upass=pass.getText().toString();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!uname.equals("")&&!uname.equals("")) {
                    ////login operATION
                }else{
                    Toast.makeText(getApplicationContext(),"Fill all the fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
