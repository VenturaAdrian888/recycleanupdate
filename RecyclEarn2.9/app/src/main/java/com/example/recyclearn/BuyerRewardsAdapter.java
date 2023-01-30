package com.example.recyclearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerRewardsAdapter extends  RecyclerView.Adapter<BuyerRewardsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Buyer_Rewards> list;




    public BuyerRewardsAdapter(Context context, ArrayList<Buyer_Rewards> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.buyerproductitem, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Buyer_Rewards user = list.get(position);
        holder.productTitle.setText(user.getProductTitle());
        holder.productPoints.setText(user.getProductPoints());
        Glide.with(context).load(list.get(position).getProductIcon()).into(holder.productIcon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView productTitle, productPoints;
        CircleImageView productIcon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPoints = itemView.findViewById(R.id.productPoints);
            productIcon = itemView.findViewById(R.id.productIcon);
        }
    }
}
