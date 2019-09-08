package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vlearn.model.Feed;
import com.example.vlearn.model.MainRediffFeed;
import com.example.vlearn.model.NYTimesFeed;
import com.example.vlearn.model.YahooFeed;
import com.example.vlearn.model.channel.Item;
import com.example.vlearn.model.channelYahoo.ItemYahoo;
import com.example.vlearn.model.entry.Entry;
import com.example.vlearn.model.rediffitem.RediffItem;

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
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    public static final String CHECK_URL="https://vlearndroidrun.000webhostapp.com/check_user.php";
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://www.reddit.com/r/";
    private static final String BASE_URL_NYTIMES = "https://rss.nytimes.com/services/xml/rss/nyt/";
    private static final String BASE_URL_YAHOO="https://yahoo.com/";
    private static final String BASE_URL_REDIFF="http://www.rediff.com/rss/";
    private Button btnLoadFeed;
    private EditText mFeedName;
    private ListView listView;
    ArrayList<Post> posts;
    SpotsDialog dialog;

    String retrieve;
    String json_string;
    String JSON_String;
    JSONArray jsonArray;
    int USER_ID=1;
    JSONObject jsonObject;


    //har site se info nikal rhe h interest k according site automatically select ho rhi h
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dialog=new SpotsDialog(getContext());
        dialog.show();

        View v= inflater.inflate(R.layout.fragment_news,container,false);       //fragment ka view for other purpose
        btnLoadFeed = (Button)v.findViewById(R.id.btnRefreshFeed);
        mFeedName = (EditText)v.findViewById(R.id.etFeedName);
        posts = new ArrayList<Post>();


        listView = (ListView)v.findViewById(R.id.listView);
        btnLoadFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedName = mFeedName.getText().toString();
                if(!feedName.equals("")){
                    posts.clear();

                }

            }
        });

        ret();
        //dialog.dismiss();


        return v;

    }

    private void LoadNewsFeed() {

        HashMap<Character,String> map = new HashMap<>();
        map.put('A',"Physics");
        map.put('B',"Maths");
        map.put('C',"Computer");
        map.put('D',"Science");
        map.put('E',"Politics");
        map.put('F',"Business");
        map.put('G',"Technology");
        map.put('H',"Sports");
        map.put('I',"Movies");
        map.put('J',"Music");

        String s = retrieve;            //Fetch from Table Topics from User_Id
        Character c;
        for(int i=0;i<s.length();i++){

            c = s.charAt(i);
            AddFeed(c,map.get(c));

        }


    }

    private void AddFeed(Character c,String s) {

        switch(c)
        {
            case 'I':
                initRediff("moviescolumnrss");
            case 'J':
                initYahoo(s);
            case 'H':
            case 'G':
            case 'F':
            case 'E':
            case 'D':
                initNYTimes(s);
            case 'C':
            case 'B':
            case 'A':
                init(s);
        }

    }

    private void init(String currentFeed){

        Retrofit retrofit = new Retrofit.Builder()                                          //retrofit xml parsing m help krega
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);                           //apni requirement k hisaab se feedAPI m given exact link se connection establish ho raha h

        Call<Feed> call = feedAPI.getFeed(currentFeed);

        call.enqueue(new Callback<Feed>() {                                      // getting our objects(here entry) of news feed
            @Override
            public void onResponse(Call<Feed> call, retrofit2.Response<Feed> response) {        //conversion of retrofit object to our class feed object


                List<Entry> entrys = response.body().getEntrys();


                for(int i=0;i<entrys.size();i++) {

                    ExtractXML extractXML1 = new ExtractXML(entrys.get(i).getContent(), "<a href=");
                    List<String> postContent = extractXML1.start();

                    ExtractXML extractXML2 = new ExtractXML(entrys.get(i).getContent(), "<img src=");
                    try {
                        postContent.add(extractXML2.start().get(0));
                    }
                    catch (NullPointerException e){          // null image
                        Log.d(TAG,"onResponse : NULL Pointer Exception : "+ e.getMessage());
                        postContent.add(null);
                    }
                    catch (IndexOutOfBoundsException e) {    // no image tag
                        Log.d(TAG,"onResponse : No image tag Exception : "+ e.getMessage());
                        postContent.add(null);
                    }
                    int lastPosition = postContent.size()-1;
                    try {
                        Post newPost = new Post(entrys.get(i).getTitle(),entrys.get(i).getAuthor().getName(),entrys.get(0).getUpdated(),postContent.get(0),postContent.get(lastPosition));
                        posts.add(newPost);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                }


                setListView();

            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "Failure : Unable to retrieve RSS Feeds "+t.getMessage());
                //   Toast.makeText(MainActivity.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });
    }



    private void initRediff(String currentFeed){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_REDIFF)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedRediffAPI feedAPI = retrofit.create(FeedRediffAPI.class);

        Call<MainRediffFeed> call = feedAPI.getFeed(currentFeed);

        call.enqueue(new Callback<MainRediffFeed>() {
            @Override
            public void onResponse(Call<MainRediffFeed> call, retrofit2.Response<MainRediffFeed> response) {

                List<RediffItem> entrys = response.body().getmChannel().getItemRediff();


                for(int i=0;i<entrys.size();i++) {
                    try {
                        Post newPost = new Post(entrys.get(i).getTitle(),entrys.get(i).getDesc(),entrys.get(0).getAuth(),entrys.get(i).getLink(),entrys.get(i).getImag());
                        posts.add(newPost);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                }

                for(int j=0;j<posts.size();j++){

                    Log.d(TAG,"PostURL :"+posts.get(j).getPostURL()+
                            "\nThumbnailURL = "+posts.get(j).getThumbnailURL()+
                            "\nTitle : "+ posts.get(j).getTitle()+
                            "\nAuthor : "+posts.get(j).getAuthor()+
                            "\nUpdated : "+posts.get(j).getDate_updated()+
                            "\n");
                }


                setListView();





            }

            @Override
            public void onFailure(Call<MainRediffFeed> call, Throwable t) {
                Log.e(TAG, "Failure : Unable to retrieve RSS Feeds "+t.getMessage());
                //  Toast.makeText(MainActivity.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });
    }



    private void initNYTimes(String currentFeed){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NYTIMES)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedNYTimesAPI feedNYTimesAPI = retrofit.create(FeedNYTimesAPI.class);

        Call<NYTimesFeed> call = feedNYTimesAPI.getFeed(currentFeed);



        call.enqueue(new Callback<NYTimesFeed>() {
            @Override
            public void onResponse(Call<NYTimesFeed> call, retrofit2.Response<NYTimesFeed> response) {


                List<Item> items = response.body().getmChannel().getItems();


                for(int i=0;i<items.size();i++) {


                    try {
                        Post newPost = new Post(items.get(i).getTitle(),items.get(i).getCreator(),items.get(i).getLastBuildDate(),items.get(i).getLink(),"");
                        posts.add(newPost);
                    }catch (NullPointerException e){
                        Log.d(TAG,"GADBADI");
                        e.printStackTrace();
                    }

                }




                setListView();


            }

            @Override
            public void onFailure(Call<NYTimesFeed> call, Throwable t) {
                Log.e(TAG, "Failure : Unable to retrieve RSS Feeds "+t.getMessage());
                //   Toast.makeText(MainActivity.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });

    }



    private void initYahoo(String currentFeed){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_YAHOO)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedYahooAPI feedAPIk = retrofit.create(FeedYahooAPI.class);

        Call<YahooFeed> call = feedAPIk.getFeed(currentFeed);

        call.enqueue(new Callback<YahooFeed>() {
            @Override
            public void onResponse(Call<YahooFeed> call, retrofit2.Response<YahooFeed> response) {


                List<ItemYahoo> items = response.body().getmChannel().getItems();



                for(int i=0;i<items.size();i++) {



                    try {
                        Post newPost = new Post(items.get(i).getTitle(),"Yahoo",items.get(i).getLastBuildDate(),items.get(i).getLink(),"");
                        posts.add(newPost);
                    }catch (NullPointerException e){
                        Log.d(TAG,"GADBADI");
                        e.printStackTrace();
                    }

                }


                setListView();




            }

            @Override
            public void onFailure(Call<YahooFeed> call, Throwable t) {
                Log.e(TAG, "Failure : Unable to retrieve RSS Feeds "+t.getMessage());
                //  Toast.makeText(MainActivity.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });
    }




    private void setListView(){

        CustomListAdapter customListAdapter = new CustomListAdapter(getContext(),R.layout.card_layout_news,posts);
        listView.setAdapter(customListAdapter);

    }

    /*----------------------------------------------*/

    public void met()
    {
        Log.d("Met : ","Met STARTED");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_URL,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        Log.d("Entered Response : ", "Now call ret && fun");
                        ret();

//                        fun();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY ERROR : ","Volley error ");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();

                return prams;
            }
        };




    }

    public void ret()
    {
        Integer integer=77;
        NewsFragment.BackgroundTask backgroundTask=new NewsFragment.BackgroundTask();
        backgroundTask.execute(integer,USER_ID);
    }

    /*/*  static*/ class BackgroundTask extends AsyncTask<Integer,Void,String> ///KOI DIKKAT AAE TOH STATIC HATA DENA
    {
        String json_url="https://vlearndroidrun.000webhostapp.com/getUserTopics.php";


        @Override
        protected String doInBackground(Integer... integers) {

            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                //my
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_ID+"","UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                //
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

            JSON_String=result;
           // dialog.dismiss();

            getUserTopicString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void getUserTopicString() {
        json_string = JSON_String;



        try {
            jsonObject = new JSONObject(JSON_String);

            int count = 0;
            jsonArray = jsonObject.getJSONArray("server_response");

            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                retrieve = jo.getString("TopicStr");              //YAHA PE TOPIC VAALI STRING LI H JSON FORMAT SE CONVERT HO GAYI HAI
                LoadNewsFeed();
                count++;


            }
            dialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*  -----------------------------------------------*/

    }



}
