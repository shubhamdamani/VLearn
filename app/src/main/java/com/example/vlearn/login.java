package com.example.vlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.databinding.DataBindingUtil;
import dmax.dialog.SpotsDialog;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import com.example.vlearn.databinding.ActivityLoginBinding;



public class login extends AppCompatActivity {
   SpotsDialog dialog;
    EditText userame,pass;
    TextView gotoRegister;
    String l_name="",l_pass="";
    private ActivityLoginBinding mBinding;




    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String emyl;
    String USER_ID,USER_NAME;
    String password;


    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        rootView=findViewById(R.id.rootView);







        userame=findViewById(R.id.L_username);
         pass=findViewById(R.id.L_password);

        gotoRegister=findViewById(R.id.goto_register);
        emyl=userame.getText().toString().trim();
        password=pass.getText().toString().trim();







        //If User want to create new account this register button sends user to registerActivity
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,register.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        

    }
    public void load(View view) {
        l_name=userame.getText().toString();
        l_pass=pass.getText().toString();

        new login.BackgroundTask().execute();
        animateButtonWidth();

        fadeOutTextAndShowProgressDialog();


    }



    private void fadeOutTextAndShowProgressDialog() {
        mBinding.text.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                })
                .start();
    }

    private void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(mBinding.login.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mBinding.login.getLayoutParams();
                layoutParams.width = val;
                mBinding.login.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private void showProgressDialog() {
        mBinding.progressBar.setAlpha(1f);
        mBinding.progressBar
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mBinding.progressBar.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();

                fadeOutProgressDialog();


            }
        }, 400);
        delayedStartNextActivity();
    }
    private void failedAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();

                fadeOutProgressDialog();


            }
        }, 400);
    }

    private void revealButton() {
        mBinding.login.setElevation(0f);

        mBinding.reveal.setVisibility(VISIBLE);

        int cx = mBinding.reveal.getWidth();
        int cy = mBinding.reveal.getHeight();


        int x = (int) (getFabWidth() / 2 + mBinding.login.getX());
        int y = (int) (getFabWidth() / 2 + mBinding.login.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator reveal = ViewAnimationUtils
                .createCircularReveal(mBinding.reveal, x, y, getFabWidth(), finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset(animation);
            }

            private void reset(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.reveal.setVisibility(INVISIBLE);
                mBinding.text.setVisibility(VISIBLE);
                mBinding.text.setAlpha(1f);
                mBinding.login.setElevation(4f);
                ViewGroup.LayoutParams layoutParams = mBinding.login.getLayoutParams();
                layoutParams.width = (int) (getResources().getDisplayMetrics().density * 300);
                mBinding.login.requestLayout();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog() {
        mBinding.progressBar.animate().alpha(0f).setDuration(350).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
    }


    private void delayedStartNextActivity() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("login","going to main");
                Intent i=new Intent(login.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        }, 0);
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.progress_width);
    }






    //Retrieve Details Of USER from Database using JSON parsing
    public void getDatafromJSON()
    {
        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                USER_NAME=jo.getString("UserName");
                USER_ID=jo.getString("User_Id");
                Log.d("login:        ",USER_NAME+" "+USER_ID);
                if(USER_NAME.equals("no") || USER_ID.equals("no"))
                {
                    Snackbar snackbar=Snackbar.make(rootView,"Login Failed! Enter Correct Details",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else {
                    USER_Class.setLoggedUserId(USER_ID);
                    USER_Class.setLoggedUserEmail(l_name);
                    USER_Class.setLoggedUserName(USER_NAME);
                }
                count++;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Thread is created to fetch user info as  JSON data
    //which works in background
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/loginCheck.php";
        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                //my
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(l_name,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(l_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();

                while((json_string=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                JSON_String=stringBuilder.toString().trim();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "fail";
        }
        public BackgroundTask()
        {
            super();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSON_String = result;

                Log.d("json", JSON_String);
                getDatafromJSON();
                if (USER_NAME.equals("no") || USER_ID.equals("no")) {
                    failedAction();


                } else {
                    nextAction();

                }
            }catch (Exception e)
            {
                failedAction();
                Snackbar snackbar=Snackbar.make(rootView,"No Internet Connection",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}
