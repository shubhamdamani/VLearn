package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.lang.Character.toUpperCase;

public class AnswerForQuestionAdapter extends RecyclerView.Adapter<AnswerForQuestionAdapter.ProductViewHolder> {


    //this context will be used to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<AnswerForQuestionCard> productList;

    //getting the context and product list with constructor
    public AnswerForQuestionAdapter(Context mCtx, List<AnswerForQuestionCard> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_for_ques_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        AnswerForQuestionCard product = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_userId.setText(product.getUser_Id());
        holder.txt_username.setText(product.getUserName());
        holder.txt_Answer.setText(product.getAnswer());
        Character name=product.getUserName().charAt(0);
        name=toUpperCase(name);
        holder.prof_icon.setText(name.toString());


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userId, txt_username, txt_Answer,prof_icon;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_userId = itemView.findViewById(R.id.ans_User_Id);
            txt_username = itemView.findViewById(R.id.ans_user_name);
            txt_Answer = itemView.findViewById(R.id.answer);
            prof_icon = itemView.findViewById(R.id.prof_icon);

        }
    }


}