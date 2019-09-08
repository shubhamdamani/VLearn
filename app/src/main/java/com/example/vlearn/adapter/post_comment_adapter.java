package com.example.vlearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vlearn.QuestionDetail;
import com.example.vlearn.R;
import com.example.vlearn.object.getComment_data;
import com.example.vlearn.questionfetch;

import java.util.List;


public class post_comment_adapter extends RecyclerView.Adapter<post_comment_adapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<getComment_data> productList;

    //getting the context and product list with constructor
    public post_comment_adapter(Context mCtx, List<getComment_data> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public post_comment_adapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_comment_card, parent, false);
        return new post_comment_adapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(post_comment_adapter.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        getComment_data product = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_username.setText(product.getUsername());
        holder.txt_comment.setText(product.getComment());




    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_username, txt_comment;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_username = itemView.findViewById(R.id.tvusername);
            txt_comment = itemView.findViewById(R.id.tvComment);


        }


    }


}