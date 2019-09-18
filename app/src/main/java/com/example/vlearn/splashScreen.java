package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

public class splashScreen extends AppCompatActivity {
    TextView app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        app_name=findViewById(R.id.app_name);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // Start your app main activity
                Intent i = new Intent(splashScreen.this, login.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(app_name,"app_nameTransition");
                pairs[1]=new Pair<View,String>(app_name,"logoTransition");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(splashScreen.this,pairs);
                startActivity(i,options.toBundle());
                finish();
            }
        }, 3000);
    }                // This method will be executed once the timer is over


}

