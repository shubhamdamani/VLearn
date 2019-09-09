package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsFeedDetails extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView date_updated;
    ProgressBar mProgressBar;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_details);

        title = (TextView)findViewById(R.id.title);
        author = (TextView)findViewById(R.id.author);
        date_updated = (TextView)findViewById(R.id.date_updated);
        description = (TextView)findViewById(R.id.description);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        date_updated.setText(intent.getStringExtra("updated"));
        description.setText(intent.getStringExtra("description"));




    }
}
