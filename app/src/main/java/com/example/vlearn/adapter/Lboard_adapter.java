package com.example.vlearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vlearn.PersonalInfo.FollowUser;
import com.example.vlearn.QuestionDetail;
import com.example.vlearn.R;
import com.example.vlearn.object.Lboard_user;
import com.example.vlearn.questionfetch;

import java.util.List;

import static java.lang.Character.toUpperCase;


public class Lboard_adapter extends RecyclerView.Adapter<Lboard_adapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Lboard_user> productList;

    //getting the context and product list with constructor
    public Lboard_adapter(Context mCtx, List<Lboard_user> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public Lboard_adapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_board_card, parent, false);
        return new Lboard_adapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Lboard_adapter.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Lboard_user product = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_name.setText(product.getUserName());
        holder.txt_email.setText(product.getUserEmail());
        holder.txt_Id.setText(product.getUserId());

        Character name=product.getUserName().charAt(0);
        name=toUpperCase(name);
        holder.prof_icon.setText(name.toString());


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_email, txt_Id,prof_icon;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.u_name);
            txt_email = itemView.findViewById(R.id.u_email);
            txt_Id = itemView.findViewById(R.id.u_id);
            prof_icon = itemView.findViewById(R.id.prof_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FollowUser.class);
                    intent.putExtra("User_Id", txt_Id.getText());
                    intent.putExtra("UserName", txt_name.getText());
                    v.getContext().startActivity(intent);


                }
            });

        }
    }
}

