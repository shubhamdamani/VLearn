package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class interests extends AppCompatActivity { //user k topic select krne k liye

    static HashMap<String,Character> TopicToKey;    //Map topic to key
    public StringBuffer Interest_Str = new StringBuffer();
    public String operation;

    static{
        TopicToKey = new HashMap<>();
        TopicToKey.put("Physics",'A');
        TopicToKey.put("Maths",'B');
        TopicToKey.put("Computer",'C');
        TopicToKey.put("Science",'D');
        TopicToKey.put("Politics",'E');
        TopicToKey.put("Business",'F');
        TopicToKey.put("Technology",'G');
        TopicToKey.put("Sports",'H');
        TopicToKey.put("Movies",'I');
        TopicToKey.put("Music",'J');
    }



    MyCustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        Intent i=getIntent();
        operation=i.getStringExtra("operation");


        displayListView();

        checkButtonClick();

    }
    private void displayListView() {

        //Array list of countries
        ArrayList<Topics> interestList = new ArrayList<Topics>();
        Topics interestItem = new Topics("Physics",false);
        interestList.add(interestItem);
        interestItem = new Topics("Maths",false);
        interestList.add(interestItem);
        interestItem = new Topics("Computer",false);
        interestList.add(interestItem);
        interestItem = new Topics("Science",false);
        interestList.add(interestItem);
        interestItem = new Topics("Politics",false);
        interestList.add(interestItem);
        interestItem = new Topics("Business",false);
        interestList.add(interestItem);
        interestItem = new Topics("Technology",false);
        interestList.add(interestItem);
        //interestItem = new Topics("Sports",false);
        //interestList.add(interestItem);
     //   interestItem = new Topics("Movies",false);
       // interestList.add(interestItem);
        //interestItem = new Topics("Music",false);
        //interestList.add(interestItem);


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.topic_info, interestList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                com.example.vlearn.Topics country = (com.example.vlearn.Topics) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Clicked on Row: " + country.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<com.example.vlearn.Topics> {

        private ArrayList<com.example.vlearn.Topics> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<com.example.vlearn.Topics> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<com.example.vlearn.Topics>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {

            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.topic_info, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        com.example.vlearn.Topics country = (com.example.vlearn.Topics) cb.getTag();
                        Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            com.example.vlearn.Topics country = countryList.get(position);
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();

                responseText.append("The following were selected...\n");

                ArrayList<Topics> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Topics topics = countryList.get(i);
                    if(topics.isSelected()){
                        responseText.append("\n" + topics.getName());
                        String str=topics.getName();
                        String key=TopicToKey.get(str).toString();
                        Interest_Str.append(key);
                    }
                }

                Toast.makeText(getApplicationContext(), responseText+" "+Interest_Str, Toast.LENGTH_LONG).show();
                new interests.BackgroundTask().execute();

            }
        });

    }
    class BackgroundTask extends AsyncTask<Void,Void,String>        //this class is background task performing which will connect
            //app to our database(php script of required) and then php script
            // will run our queryand make changes in database
    {


        @Override
        protected String doInBackground(Void... voids) {

            try {String interest_url;
                if(operation.equals("up_interest"))
                {
                    interest_url="https://vlearndroidrun.000webhostapp.com/updateUserTopics.php";
                }else{
                    interest_url="https://vlearndroidrun.000webhostapp.com/addTopics.php";
                }
                URL url=new URL(interest_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("User_Id","UTF-8")+"="+URLEncoder.encode(USER_Class.getLoggedUserId(),"UTF-8")+"&"+
                        URLEncoder.encode("User_Topics","UTF-8")+"="+URLEncoder.encode(Interest_Str.toString(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Posted Successfully";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "failed";
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

            if(result.equals("Posted Successfully"))
            {
                Intent i=new Intent(interests.this,MainActivity.class);
                startActivity(i);
            }
            //JSON_String=result;
            Toast.makeText(interests.this,result,Toast.LENGTH_LONG).show();
            //getDatafromJSON();

            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
