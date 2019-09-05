package com.example.vlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);





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
