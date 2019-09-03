package com.example.vlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class questionadapter extends ArrayAdapter {


    List list=new ArrayList();

    public questionadapter(@NonNull Context context, int resource) {



        super(context, resource);
    }


    public void add(questionfetch object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row=convertView;
        ContactHolder contactHolder;

        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row=layoutInflater.inflate(R.layout.ques_layout,parent,false);

            contactHolder=new ContactHolder();
            contactHolder.tx_Topic=row.findViewById(R.id.tx_Topic);
            contactHolder.tx_Question=row.findViewById(R.id.tx_Question);
            contactHolder.tx_Q_Id=row.findViewById(R.id.tx_Q_Id);
            contactHolder.tx_User_Id=row.findViewById(R.id.tx_User_Id);
            row.setTag(contactHolder);



        }else{
            contactHolder=(ContactHolder)row.getTag();

        }

        questionfetch contacts=(questionfetch) this.getItem(position);
        contactHolder.tx_Topic.setText(contacts.getTopic());
        contactHolder.tx_User_Id.setText(contacts.getUser_Id());
        contactHolder.tx_Q_Id.setText(contacts.getQ_Id());
        contactHolder.tx_Question.setText(contacts.getQuestion());




        return row;
    }

    static class ContactHolder{

        TextView tx_Topic,tx_User_Id,tx_Q_Id,tx_Question;


    }



}
