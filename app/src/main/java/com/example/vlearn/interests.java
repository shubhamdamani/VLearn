package com.example.vlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.ArrayList;

public class interests extends AppCompatActivity { //user k topic select krne k liye
    MyCustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        displayListView();

        checkButtonClick();

    }
    private void displayListView() {

        //Array list of countries
        ArrayList<com.example.vlearn.Topics> countryList = new ArrayList<com.example.vlearn.Topics>();
        com.example.vlearn.Topics country = new com.example.vlearn.Topics("News",false);
        countryList.add(country);
        country = new com.example.vlearn.Topics("Science",false);
        countryList.add(country);
        country = new com.example.vlearn.Topics("Sports",false);
        countryList.add(country);
        country = new com.example.vlearn.Topics("Education",false);
        countryList.add(country);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.topic_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                com.example.vlearn.Topics country = (com.example.vlearn.Topics) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
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

                ArrayList<com.example.vlearn.Topics> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    com.example.vlearn.Topics topics = countryList.get(i);
                    if(topics.isSelected()){
                        responseText.append("\n" + topics.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }
}
