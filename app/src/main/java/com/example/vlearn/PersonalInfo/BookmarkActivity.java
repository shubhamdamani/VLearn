package com.example.vlearn.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vlearn.R;

public class BookmarkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView=findViewById(R.id.Bookmark_recyclerview);
        recyclerView.setHasFixedSize(true);
    }
}
