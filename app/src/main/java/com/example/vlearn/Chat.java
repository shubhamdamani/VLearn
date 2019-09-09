package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vlearn.PersonalInfo.UpdateInfo;
import com.example.vlearn.chatWithAdmin.chatWithAdmin;

public class Chat extends AppCompatActivity {
    Button chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat=findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Chat.this, chatWithAdmin.class);
                startActivity(i);
            }
        });
    }
}
