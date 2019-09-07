package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class post_adapter extends RecyclerView.Adapter<post_adapter.ProductViewHolder> {
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Post_content> productList;

    //getting the context and product list with constructor
    public post_adapter(Context mCtx, List<Post_content> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public post_adapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post_layout, parent, false);
        return new post_adapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(post_adapter.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Post_content product = productList.get(position);

        //binding the data with the viewholder views
        holder.post_user.setText(product.getPost_user());
        holder.artical.setText(product.getArtical());
        holder.upvote.setText(Integer.toString(product.getUpvote()));
       holder.downvote.setText(Integer.toString(product.getDownvotes()));



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView post_user, artical, upvote, downvote;

        public ProductViewHolder(View itemView) {
            super(itemView);

            post_user = itemView.findViewById(R.id.post_username);
            artical = itemView.findViewById(R.id.artical);
            upvote = itemView.findViewById(R.id.nup);
            downvote = itemView.findViewById(R.id.ndown);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PostDetail.class);
                    intent.putExtra("User_Id",txt_userId.getText());
                    intent.putExtra("Q_Id",txt_Q_Id.getText());
                    //Toast.makeText(v.getContext(), "Q_Id : " + txt_Q_Id.getText(), Toast.LENGTH_LONG).show();
                    intent.putExtra("Question",txt_ques.getText());
                    intent.putExtra("Topic",txt_Topic.getText());
                    v.getContext().startActivity(intent);


                }
            });*/

        }
    }
}
