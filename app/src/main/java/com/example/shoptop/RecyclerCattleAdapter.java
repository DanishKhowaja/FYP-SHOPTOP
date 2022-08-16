package com.example.shoptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCattleAdapter extends FirebaseRecyclerAdapter<CattleModel,RecyclerCattleAdapter.ViewHolder> {

    public RecyclerCattleAdapter(@NonNull FirebaseRecyclerOptions<CattleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull CattleModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
        holder.Price.setText(Integer.toString(model.getPrice()));
        holder.Details.setText(model.getDetail());
        Glide.with(holder.Cattleimg.getContext()).load(model.cpurl).into(holder.Cattleimg);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Address, Price, Details;
        ImageView Cattleimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.cname);
            Address = itemView.findViewById(R.id.caddress);
            Price = itemView.findViewById(R.id.cprice);
            Details = itemView.findViewById(R.id.cdetails);
            Cattleimg = itemView.findViewById(R.id.cattleimg);

        }
    }
}
