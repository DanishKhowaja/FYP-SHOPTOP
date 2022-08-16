package com.example.shoptop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerSupplierCattleAdapter extends FirebaseRecyclerAdapter<CattleModel, RecyclerSupplierCattleAdapter.ViewHolder> {

    public RecyclerSupplierCattleAdapter(@NonNull FirebaseRecyclerOptions<CattleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerSupplierCattleAdapter.ViewHolder holder, int position, @NonNull CattleModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
        holder.Price.setText(Integer.toString(model.getPrice()));
        Glide.with(holder.Image.getContext()).load(model.getCpurl()).into(holder.Image);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cattles");
        databaseReference= getRef(position);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Cattles");
        final String mykey = databaseReference.getKey();
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference1.child(mykey).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suppliercattlercv,parent,false);
        return new RecyclerSupplierCattleAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image, Delete;
        TextView Name, Address, Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.cattleimage);
            Delete = itemView.findViewById(R.id.deletecattle);
            Name = itemView.findViewById(R.id.cattlename);
            Address = itemView.findViewById(R.id.cattleaddress);
            Price = itemView.findViewById(R.id.cattleprice);
        }
    }
}
