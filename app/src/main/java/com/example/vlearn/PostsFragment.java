package com.example.vlearn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    post_adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_posts,container,false);

        recyclerView = v.findViewById(R.id.post_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mPostContent =new ArrayList<>();
        Post_content contacts=new Post_content("vivek","hello World",10,2);
        mPostContent.add(contacts);
        adapter = new post_adapter(getContext(), mPostContent);
        adapter = new post_adapter(getContext(), mPostContent);
        contacts=new Post_content("vivek1","hello World1",1,2);
        mPostContent.add(contacts);
        adapter = new post_adapter(getContext(), mPostContent);
        recyclerView.setAdapter(adapter);





        return v;
    }
}
