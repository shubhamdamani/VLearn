package com.example.vlearn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Question_adapter extends RecyclerView.Adapter<Question_adapter.ProductViewHolder> {


    //this context we will use to inflate the layout
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

        //binding the data with the viewholder views
        holder.txt_userId.setText(product.getUser_Id());
        holder.txt_ques.setText(product.getQuestion());
        holder.txt_Topic.setText(product.getTopic());
        holder.txt_Q_Id.setText(product.getQ_Id());



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userId, txt_ques, txt_Topic, txt_Q_Id;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_userId = itemView.findViewById(R.id.tx_User_Id);
            txt_ques = itemView.findViewById(R.id.tx_Question);
            txt_Q_Id = itemView.findViewById(R.id.tx_Q_Id);
            txt_Topic = itemView.findViewById(R.id.tx_Topic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuestionDetail.class);
                    intent.putExtra("User_Id",txt_userId.getText());
                    intent.putExtra("Q_Id",txt_Q_Id.getText());
                    //Toast.makeText(v.getContext(), "Q_Id : " + txt_Q_Id.getText(), Toast.LENGTH_LONG).show();
                    intent.putExtra("Question",txt_ques.getText());
                    intent.putExtra("Topic",txt_Topic.getText());
                    v.getContext().startActivity(intent);


                }
            });

        }
    }


}