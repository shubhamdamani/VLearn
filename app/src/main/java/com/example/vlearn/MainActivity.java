package com.example.vlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


                                                                    //teen fragment banae h, or kch nh h
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





      /*  if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new NewsFragment()).commit();}*/

        Fragment f=new NewsFragment();
        Fragment f2=new PostsFragment();
        Fragment f1=new QuesFragment();
        FragmentManager fe=getSupportFragmentManager();
        Fragment active=f;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,f,"1").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,f1,"2").hide(f1).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,f2,"3").hide(f2).commit();


        BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
       // bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

       /* Fragment sel;
        sel=new NewsFragment();
*/



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

                USER_Class.setLoggedUserEmail("");
                USER_Class.setLoggedUserId("");
                USER_Class.setLoggedUserName("");
                Intent i=new Intent(MainActivity.this,login.class);
                startActivity(i);
                finish();
                break;
            }
            case R.id.profileMenu: {
                startActivity(new Intent(MainActivity.this, myprofile.class));
                finish();
                break;
            }
            case R.id.aboutMenu:{
                startActivity(new Intent(MainActivity.this,AboutVLearn.class));
                finish();
                break;
            }
            case R.id.chatMenu:{
                startActivity(new Intent(MainActivity.this,Chat.class));
                finish();
                break;
            }case R.id.leaderMenu:{
                startActivity(new Intent(MainActivity.this,LeaderBoard.class));
                finish();
                break;
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
