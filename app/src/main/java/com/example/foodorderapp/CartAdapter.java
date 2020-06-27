package com.example.foodorderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ThrowOnExtraProperties;

import java.util.List;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    List<CartItem> list;
    public CartAdapter(List<CartItem> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.name.setText(list.get(position).getItemName());
        int cost;
        int price = Integer.parseInt(list.get(position).getPrice());
        int quantity = Integer.parseInt(list.get(position).getQuantity());
        cost = price * quantity;
        String q = list.get(position).getQuantity();
        String c = "\u20B9 "+ Integer.toString(cost);
        holder.quantity.setText(q);
        holder.cost.setText(c);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView quantity;
        TextView cost;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.quantity);
            cost = itemView.findViewById(R.id.item_price);
        }
    }
}
