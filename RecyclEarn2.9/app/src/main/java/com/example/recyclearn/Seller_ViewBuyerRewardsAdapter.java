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

public class Seller_ViewBuyerRewardsAdapter extends RecyclerView.Adapter<Seller_ViewBuyerRewardsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Seller_ViewBuyerRewards> list;

    public Seller_ViewBuyerRewardsAdapter(Context context, ArrayList<Seller_ViewBuyerRewards> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(context).inflate(R.layout.sellerviewbuyeritem, parent, false);
        return new MyViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Seller_ViewBuyerRewards user = list.get(position);
        holder.productTitle.setText(user.getProductTitle());
        holder.productPoints.setText(user.getProductPoints());
        holder.buyershopname.setText(user.getBuyershopname());
        Glide.with(context).load(list.get(position).getProductIcon()).into(holder.productIcon);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productTitle, productPoints, buyershopname;
        CircleImageView productIcon;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPoints = itemView.findViewById(R.id.productPoints);
            productIcon = itemView.findViewById(R.id.productIcon);
            buyershopname = itemView.findViewById(R.id.buyershopname);
        }
    }
}
