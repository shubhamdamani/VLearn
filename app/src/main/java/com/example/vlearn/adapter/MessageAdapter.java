package com.example.vlearn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlearn.R;
import com.example.vlearn.object.chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<chat> mChat;
    FirebaseUser fuser;

    public MessageAdapter(Context context,List<chat> mChat){
        this.mChat=mChat;
        this.context=context;


    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        chat chat=mChat.get(position);
        Log.d("mesaage",chat.getMessage());
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;

        public ViewHolder(View itemsView){
            super(itemsView);
            show_message=itemsView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {


        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;

        }else{
            return MSG_TYPE_LEFT;
        }
    }
}


