package com.example.vlearn.chatWithAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.vlearn.Admin.AdminBasic;
import com.example.vlearn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends AppCompatActivity {
    private RecyclerView recyclerView;
    private chatUserAdapter userAdapter;
    private List<chatUser> mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        recyclerView = (RecyclerView) findViewById(R.id.admin_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        mUsers=new ArrayList<>();
        readUsers();
    }
    private void readUsers(){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    chatUser user=dataSnapshot1.getValue(chatUser.class);
                    //Toast.makeText(AdminPanel.this,"use:"+user.getId(),Toast.LENGTH_SHORT).show();
                    if(!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);

                    }

                }
                userAdapter =new chatUserAdapter(getBaseContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AdminPanel.this,"Some error occured",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
