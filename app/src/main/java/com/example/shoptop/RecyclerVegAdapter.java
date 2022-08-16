package com.example.shoptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVegAdapter extends FirebaseRecyclerAdapter<VegModel, RecyclerVegAdapter.ViewHolder> {

    public RecyclerVegAdapter(@NonNull FirebaseRecyclerOptions<VegModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull VegModel model) {
        holder.VegName.setText(model.getVname());
        holder.VegAddress.setText(model.getVaddress());
        holder.VegPrice.setText(Integer.toString(model.getVprice()));
        holder.VegQty.setText(model.getVquantity());
        Glide.with(holder.Image.getContext()).load(model.getVpurl()).into(holder.Image);

        holder.veglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.vegetables,new ViewVeg(model.getVname(),model.getVaddress(),model.getVprice(),model.getVpurl(), model.getUserid())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.veg_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image;
        TextView VegName, VegAddress, VegPrice, VegQty;
        LinearLayout veglayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.vegimg);
            VegName = itemView.findViewById(R.id.name1);
            VegAddress = itemView.findViewById(R.id.address1);
            VegPrice = itemView.findViewById(R.id.price1);
            VegQty = itemView.findViewById(R.id.quantity1);

            veglayout = itemView.findViewById(R.id.vegrcvlayout);

        }
    }
}
