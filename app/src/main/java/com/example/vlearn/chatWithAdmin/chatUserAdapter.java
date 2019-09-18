package com.example.vlearn.chatWithAdmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlearn.R;

import java.util.List;


public class chatUserAdapter extends RecyclerView.Adapter<chatUserAdapter.ViewHolder>{
    private Context context;
    private List<chatUser> mUsers;

    public chatUserAdapter(Context context,List<chatUser> mUsers){
        this.mUsers=mUsers;
        this.context=context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user,parent,false);




        return new chatUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final chatUser user=mUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context , chatWithAdmin.class);
                intent.putExtra("userid",user.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;

        public ViewHolder(View itemsView){
            super(itemsView);
            username=itemsView.findViewById(R.id.username);

        }
    }
}