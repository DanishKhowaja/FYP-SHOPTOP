package com.example.shoptop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerSupplierFruitAdapter extends FirebaseRecyclerAdapter<FruitsModel,RecyclerSupplierFruitAdapter.ViewHolder> {

    public RecyclerSupplierFruitAdapter(@NonNull FirebaseRecyclerOptions<FruitsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerSupplierFruitAdapter.ViewHolder holder, int position, @NonNull FruitsModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
        holder.Price.setText(Integer.toString(model.getPrice()));
        holder.Quantity.setText(model.getQuantity());
        Glide.with(holder.Image.getContext()).load(model.getPurl()).into(holder.Image);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Fruits");
        databaseReference= getRef(position);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Fruits");
        final String mykey = databaseReference.getKey();
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference1.child(mykey).removeValue();
            }
        });

//        holder.supplierfruitlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.supplierfruits,new ViewFruit(model.getName(),model.getAddress(),model.getPrice(),model.getPurl())).addToBackStack(null).commit();
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplierfruitrcv,parent,false);
        return new RecyclerSupplierFruitAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image, Delete;
        TextView Name, Address, Price, Quantity;
        LinearLayout supplierfruitlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.supplierfruitimage);
            Delete = itemView.findViewById(R.id.deleteproduct);
            Name = itemView.findViewById(R.id.supplierfruitname);
            Address = itemView.findViewById(R.id.supplierfruitaddress);
            Price = itemView.findViewById(R.id.supplierfruitprice);
            Quantity = itemView.findViewById(R.id.supplierfruitquantity);

            supplierfruitlayout = itemView.findViewById(R.id.fruitrcvlayout);
        }
    }
}
