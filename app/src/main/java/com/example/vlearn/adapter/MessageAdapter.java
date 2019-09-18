package com.example.vlearn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlearn.R;
import com.example.vlearn.object.chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<chat> mChat;
    FirebaseUser fuser;
    DatabaseReference mRef;


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
        AdapterView.OnItemClickListener itemClickListener;

        public ViewHolder(final View itemsView){
            super(itemsView);
            show_message=itemsView.findViewById(R.id.show_message);
            mRef = FirebaseDatabase.getInstance().getReference().child("Chats");
            //getItemId()

            /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
                    new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                            /*itemsView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
*/
                                   /* fuser= FirebaseAuth.getInstance().getCurrentUser();




                                    Query mQ=mRef.child("sender").equalTo(fuser.getUid());
                                    mQ.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Query mQuery=mRef.orderByChild("message").equalTo(show_message.getText().toString());
                                            Toast.makeText(context,mQuery.toString(),Toast.LENGTH_SHORT).show();
                                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot ds:dataSnapshot.getChildren())
                                                    {
                                                        //ds.child("sen")

                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                           // });

                        //}
                    };*/


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


