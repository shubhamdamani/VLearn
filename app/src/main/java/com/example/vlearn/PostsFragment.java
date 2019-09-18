package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import dmax.dialog.SpotsDialog;

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
import java.util.List;

public class PostsFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Post_content> mPostContent;
    post_adapter adapter;
    String JSON_String,json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String posts;
    Integer up=0,down=0;
    SpotsDialog dialog;
    String sortType;
    Button B_postArtical;
    String sendTopic="";
    FloatingActionButton fab;
    EditText searchinput;

    private Spinner spinner;
    public String[] Topics = { "Sort by","Popular", "Date", "Followers"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_posts,container,false);

        searchinput=v.findViewById(R.id.searchbar);

        recyclerView = v.findViewById(R.id.post_recycler);
        dialog=new SpotsDialog(getContext());
        dialog.show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        sortType="-1";
        new PostsFragment.BackgroundTask().execute();



        fab =  v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                startActivity(new Intent(getContext(),AddPost.class));
            }
        });

        searchinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinner =v.findViewById(R.id.Sortby);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, Topics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                String selected = parentView.getItemAtPosition(position).toString();
                Context context = parentView.getContext();
                CharSequence text = selected;
                int duration = Toast.LENGTH_SHORT;

                switch (position){
                    case 0:
                        sortType="0";//sort according to date
                        Toast.makeText(getContext(),"date",Toast.LENGTH_LONG).show();
                        new PostsFragment.BackgroundTask().execute();
                        break;
                    case 1:
                        sortType="1";//sort according to popular
                        Toast.makeText(getContext(),"popular",Toast.LENGTH_LONG).show();
                        new PostsFragment.BackgroundTask().execute();
                        break;
                    case 2:
                        sortType="2";//sort according to follower
                        Toast.makeText(getContext(),"follower",Toast.LENGTH_LONG).show();
                        new PostsFragment.BackgroundTask().execute();
                        break;
                    default:
                        sortType="2";//sort according to follower
                        new PostsFragment.BackgroundTask().execute();
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });



        return v;
    }


    public void fun()                   //PARSING JSON OBJECT TO NORMAL STRING AND SHIFTING TO CARDVIEW
    {
        json_string=JSON_String;

        String Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName;
        Integer Upvotes,Downvotes;//Bookmark;



        mPostContent =new ArrayList<>();

        try {
            jsonObject=new JSONObject(JSON_String);

            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");       //THIS IS NAME OF OUR JSON ARRAY

            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);  // ARRAY KA SUB-TAG, MATLAB KEY OF REQIRED VALUE
                User_Id=jo.getString("User_Id");
                Post_content=jo.getString("Post");
                Post_Title=jo.getString("Post_Title");
                Post_Date=jo.getString("Post_Date");
                Post_Id=jo.getString("Post_Id");
                Upvotes=jo.getInt("Upvotes");
                Downvotes=jo.getInt("Downvotes");
                Topic=jo.getString("TopicStr");
                sendTopic=Topic;
                UserName=jo.getString("UserName");
               String  Bookmark=jo.getString("BookmarkStatus");

                //questionfetch contacts=new questionfetch(Topic,User_Id,Q_Id,Question);
                Post_content contacts=new Post_content(Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName,Upvotes,Downvotes,Integer.parseInt(Bookmark));
                mPostContent.add(contacts);
                adapter = new post_adapter(getContext(), mPostContent);       //ONE BY ONE PUSHING QUESTIONS TO CARDVIEW
                recyclerView.setAdapter(adapter);
                count++;


            }
            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getPosts.php";


        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8")+"&"+
                        URLEncoder.encode("SortType", "UTF-8") + "=" + URLEncoder.encode(sortType, "UTF-8");
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

            return null;
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

            try
            {
                JSON_String=result;
                fun();
                //if our json converter function throws error means no internet available
            }
            catch (Exception e)
            {
                Snackbar snackbar=Snackbar.make(getActivity().findViewById(R.id.drawer_layout),"No Internet Connection",Snackbar.LENGTH_LONG);
                //if net connection not available
                snackbar.show();
                dialog.dismiss();
            }



            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
