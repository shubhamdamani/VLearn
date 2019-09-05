package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addquestion extends AppCompatActivity {
    TextView tag;
    EditText Q_txt;
    Button b_Post;
    String QTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        tag=findViewById(R.id.tag);
        Q_txt=findViewById(R.id.Q_txt);
        b_Post=findViewById(R.id.Q_post);

        b_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostQuestion();
            }
        });
    }
    public void PostQuestion()
    {
        QTxt=Q_txt.getText().toString().trim();
        if(!QTxt.equals(""))
        {
            //post question


        }else{
            Toast.makeText(addquestion.this,"Enter Your Question",Toast.LENGTH_SHORT).show();
        }
    }
}
