package com.example.shoptopadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerMarketAdapter extends FirebaseRecyclerAdapter<MarketModel,RecyclerMarketAdapter.ViewHolder> {

    public RecyclerMarketAdapter(@NonNull FirebaseRecyclerOptions<MarketModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerMarketAdapter.ViewHolder holder, int position, @NonNull MarketModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
    }

    @NonNull
    @Override
    public RecyclerMarketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketrcv,parent,false);
        return new RecyclerMarketAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.mname);
            Address = itemView.findViewById(R.id.maddress);

        }
    }
}
