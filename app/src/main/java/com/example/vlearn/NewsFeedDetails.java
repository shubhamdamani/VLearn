package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFeedDetails extends AppCompatActivity {

    TextView title;
    TextView author;
    TextView date_updated;
    ProgressBar mProgressBar;
    TextView description;
    TextView link;
    Spanned Text;
    TextView source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_details);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        source = (TextView)findViewById(R.id.source);
        title = (TextView)findViewById(R.id.title);
        author = (TextView)findViewById(R.id.author);
        date_updated = (TextView)findViewById(R.id.date_updated);
        description = (TextView)findViewById(R.id.description);
        link = (TextView)findViewById(R.id.link);

    //    Toast.makeText(this,"Link : "+getIntent().getStringExtra("link"),Toast.LENGTH_SHORT).show();
        Text = Html.fromHtml(" <br />" +
                "<a href='"+getIntent().getStringExtra("link")+"'>Click here to visit post</a>");

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        date_updated.setText(intent.getStringExtra("updated"));
        description.setText(intent.getStringExtra("description"));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Text);
        source.setText(intent.getStringExtra("source"));




    }
}
