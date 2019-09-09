package com.example.vlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vlearn.PersonalInfo.UpdateInfo;
import com.example.vlearn.chatWithAdmin.chatWithAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Chat extends AppCompatActivity {
    Button chat;
    FirebaseAuth mAuth;
    DatabaseReference reference;
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
        login(USER_Class.getLoggedUserEmail(),USER_Class.getLoggedUserName());
    }
    public void login(String Email,String Pass){

        if(Email.equals("")||Pass.equals(""))
        {
            Toast.makeText(Chat.this,"Fill the Email and Password fields",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //checkEmailverification();
                        startActivity(new Intent(Chat.this, chatWithAdmin.class));
                    } else {
                        Toast.makeText(Chat.this, "your account doesnot exist We are registring", Toast.LENGTH_SHORT).show();
                        //Register(Email,);
                    }
                }
            });
        }
    }
    public void Register(final String username,String Email,String Pass){
        if(Email.equals("")||Pass.equals(""))
        {
            Toast.makeText(Chat.this,"Fill the Email and Password fields",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser=mAuth.getCurrentUser();
                        String userid=firebaseUser.getUid();
                        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("id",userid);
                        hashMap.put("username",username);
                        Toast.makeText(Chat.this, "email successfull", Toast.LENGTH_SHORT).show();
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Chat.this, "data successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Chat.this, chatWithAdmin.class));
                                    finish();
                                }
                            }
                        });

                    } else {
                        //Toast.makeText(RegActivity.this, "unsuccessfull", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
