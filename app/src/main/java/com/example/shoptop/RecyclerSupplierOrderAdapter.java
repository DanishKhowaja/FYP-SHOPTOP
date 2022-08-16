package com.example.shoptop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerSupplierOrderAdapter extends FirebaseRecyclerAdapter<OrderModel, RecyclerSupplierOrderAdapter.ViewHolder> {

    public RecyclerSupplierOrderAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OrderModel model) {
        holder.Name.setText(model.getName());
        holder.Count.setText(model.getCount());
        holder.Price.setText(model.getPrice());
        holder.TotalAmount.setText(model.getTotalAmount());
        holder.SuppierOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ViewOrder.class);
                intent.putExtra("Name",model.getName());
                intent.putExtra("Count",model.getCount());
                intent.putExtra("Price",model.getPrice());
                intent.putExtra("Total Amount",model.getTotalAmount());
                intent.putExtra("BuyerID",model.getBuyerID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplierorderrcv,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Count, Price, TotalAmount;
        LinearLayout SuppierOrderLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.supplierordername);
            Count = itemView.findViewById(R.id.supplierordercount);
            Price = itemView.findViewById(R.id.supplierorderprice);
            TotalAmount = itemView.findViewById(R.id.supplierordertotalprice);
            SuppierOrderLayout = itemView.findViewById(R.id.supplierorderlayout);

        }
    }
}
