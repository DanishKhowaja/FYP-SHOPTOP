package com.example.shoptop;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFruitAdapter extends FirebaseRecyclerAdapter<FruitsModel,RecyclerFruitAdapter.ViewHolder> {
    public RecyclerFruitAdapter(@NonNull FirebaseRecyclerOptions<FruitsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FruitsModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
        holder.Price.setText(Integer.toString(model.getPrice()));
        holder.Quantity.setText(model.getQuantity());
        Glide.with(holder.Image.getContext()).load(model.getPurl()).into(holder.Image);

        holder.fruitlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fruits,new ViewFruit(model.getName(),model.getAddress(),model.getPrice(),model.getPurl(),model.getUserid())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image;
        TextView Name, Address, Price, Quantity;
        LinearLayout fruitlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.fruitimage);
            Name = itemView.findViewById(R.id.name);
            Address = itemView.findViewById(R.id.address);
            Price = itemView.findViewById(R.id.price);
            Quantity = itemView.findViewById(R.id.quantity);

            fruitlayout = itemView.findViewById(R.id.fruitrcvlayout);
        }
    }
}
