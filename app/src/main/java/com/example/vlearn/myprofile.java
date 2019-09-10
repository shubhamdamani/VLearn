package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vlearn.PersonalInfo.BookmarkActivity;
import com.example.vlearn.PersonalInfo.FollowUser;
import com.example.vlearn.PersonalInfo.UpdateInfo;

public class myprofile extends AppCompatActivity {

    Button btnUpdInfo,btnUpdinterest,btnBookmark,btnMyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        btnUpdInfo=findViewById(R.id.bPersonal);
        btnUpdinterest=findViewById(R.id.bUpdinterest);
        btnBookmark=findViewById(R.id.bBookmark);
        btnMyPost=findViewById(R.id.bMypost);

        btnUpdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(myprofile.this, UpdateInfo.class);//to start
                startActivity(i);
            }
        });
        btnUpdinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(myprofile.this,interests.class);
                i.putExtra("operation","up_interest");
                startActivity(i);

            }
        });
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(myprofile.this, BookmarkActivity.class);
                    startActivity(i);

            }
        });

        btnMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(myprofile.this, FollowUser.class);
                i.putExtra("UserName",USER_Class.getLoggedUserName());
                i.putExtra("User_Id",USER_Class.getLoggedUserId());
                startActivity(i);
            }
        });

    }
}
