package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    List<MenuContent> menuContentList;
    Context context;
    OnNoteListener onNoteListener;

    public MenuAdapter(List<MenuContent> menuContentList, Context context, OnNoteListener onNoteListener) {
        this.menuContentList = menuContentList;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_view,parent,false);
        return new MenuViewHolder(v,onNoteListener);
    }

    @Override
    public int getItemCount() {
        return menuContentList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.name.setText(menuContentList.get(position).getName());
        holder.price.setText(String.valueOf(menuContentList.get(position).getPrice()));
        Glide.with(this.context).load(menuContentList.get(position).getImage()).into(holder.imageView);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView name;
        TextView price;
        OnNoteListener onNoteListener;

        public MenuViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(1025,520));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getLayoutPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}
