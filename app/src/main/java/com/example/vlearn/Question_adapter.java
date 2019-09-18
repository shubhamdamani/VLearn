package com.example.vlearn;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.lang.Character.toUpperCase;

public class Question_adapter extends RecyclerView.Adapter<Question_adapter.ProductViewHolder> {


    //we will use this context  to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<questionfetch> productList;

    //getting the context and product list with constructor
    public Question_adapter(Context mCtx, List<questionfetch> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ques_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        questionfetch product = productList.get(position);

        holder.container.setAnimation(AnimationUtils.loadAnimation(mCtx,R.anim.fade_scale_animation));
        //binding the data with the viewholder views
        holder.txt_username.setText(product.getUser_Id());
        holder.txt_ques.setText(product.getQuestion());
        holder.txt_Topic.setText(product.getTopic());
        holder.txt_Q_Id.setText(product.getQ_Id());

        Character name=product.getUser_Id().charAt(0);
        name=toUpperCase(name);
        holder.prof_icon.setText(name.toString());



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_username, txt_ques, txt_Topic, txt_Q_Id,prof_icon;
        LinearLayout container;

        public ProductViewHolder(View itemView) {
            super(itemView);

            container=itemView.findViewById(R.id.queslin);
            txt_username = itemView.findViewById(R.id.tx_User_Id);
            txt_ques = itemView.findViewById(R.id.tx_Question);
            txt_Q_Id = itemView.findViewById(R.id.tx_Q_Id);
            txt_Topic = itemView.findViewById(R.id.tx_Topic);
            prof_icon=itemView.findViewById(R.id.prof_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuestionDetail.class);
                    intent.putExtra("User_Id",txt_username.getText());
                    intent.putExtra("Q_Id",txt_Q_Id.getText());
                    intent.putExtra("Question",txt_ques.getText());
                    intent.putExtra("Topic",txt_Topic.getText());

                    Pair[] pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(prof_icon,"pImageTransition");
                    pairs[1]=new Pair<View,String>(txt_username,"pNameTransition");
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation((Activity)mCtx,pairs);
                    v.getContext().startActivity(intent,options.toBundle());



                }
            });

        }
    }


}