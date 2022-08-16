package com.example.shoptopadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerSupplierAdapter extends FirebaseRecyclerAdapter<BuyerModel, RecyclerSupplierAdapter.ViewHolder> {

    public RecyclerSupplierAdapter(@NonNull FirebaseRecyclerOptions<BuyerModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerSupplierAdapter.ViewHolder holder, int position, @NonNull BuyerModel model) {
        holder.FName.setText(model.getFName());
        holder.LName.setText(model.getLName());
        holder.ContactNumber.setText(model.getContactNumber());
        holder.Address.setText(model.getAddress());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userrcv,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView FName, LName, ContactNumber, Address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FName = itemView.findViewById(R.id.fname);
            LName = itemView.findViewById(R.id.lname);
            ContactNumber = itemView.findViewById(R.id.phnumber);
            Address = itemView.findViewById(R.id.market);
        }
    }
}
