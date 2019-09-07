package com.example.vlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
                                                                    //teen fragment banae h, or kch nh h
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:{

                Intent i=new Intent(MainActivity.this,login.class);
                startActivity(i);
                break;
            }
            case R.id.profileMenu: {
                startActivity(new Intent(MainActivity.this, myprofile.class));
                break;
            }
            case R.id.aboutMenu:{
                startActivity(new Intent(MainActivity.this,AboutVLearn.class));
            }
            case R.id.chatMenu:{
                startActivity(new Intent(MainActivity.this,Chat.class));
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment sel=null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_news:
                            sel=new NewsFragment();
                            break;
                        case R.id.nav_posts:
                            sel=new PostsFragment();
                            break;
                        case R.id.nav_ques:
                            sel=new QuesFragment();
                            break;
                            default:
                                sel=new NewsFragment();

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            sel).commit();
                    return true;
                }
            };
}
