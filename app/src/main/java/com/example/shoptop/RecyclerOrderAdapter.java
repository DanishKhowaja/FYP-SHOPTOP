package com.example.shoptop;

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

public class RecyclerOrderAdapter extends FirebaseRecyclerAdapter<OrderModel, RecyclerOrderAdapter.ViewHolder> {

    public RecyclerOrderAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OrderModel model) {
        holder.Name.setText(model.getName());
        holder.Count.setText(model.getCount());
        holder.Price.setText(model.getPrice());
        holder.TotalAmount.setText(model.getTotalAmount());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrcv,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Count, Price, TotalAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.ordername);
            Count = itemView.findViewById(R.id.ordercount);
            Price = itemView.findViewById(R.id.orderprice);
            TotalAmount = itemView.findViewById(R.id.ordertotalprice);

        }
    }
}
