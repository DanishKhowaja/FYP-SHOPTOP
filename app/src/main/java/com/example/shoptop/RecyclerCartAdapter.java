package com.example.shoptop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerCartAdapter extends RecyclerView.Adapter<RecyclerCartAdapter.ViewHolder>{

    Context context;

    ArrayList<CartModel> list;
    FirebaseUser user;
    DatabaseReference database, reference;
    String userid;
    int TotalPrice;
    OnDataSend dataSend;

//    private ItemClickListener mClickListener;

    public RecyclerCartAdapter(Context context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;

    }

    public void OnDataSend(OnDataSend dataSend) {
        this.dataSend = dataSend;
    }

    @NonNull
    @Override
    public RecyclerCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }
    public interface OnDataSend {
        void onSend(int TotalRate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartModel cartModel = list.get(position);
        int ItemPrice = cartModel.getPrice()*cartModel.getCount();
        holder.Name.setText(cartModel.getName());
        holder.Price.setText(Integer.toString(cartModel.getPrice()));
        holder.P_price.setText(String.valueOf(ItemPrice));
        holder.qty.setText(String.valueOf(cartModel.getCount()));
        Glide.with(holder.CartImage.getContext()).load(cartModel.getPurl()).into(holder.CartImage);
        TotalPrice = TotalPrice + ItemPrice;

        dataSend.onSend(TotalPrice);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Buyers").child(userid).child("CartItems");
        String key = reference.getKey();
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                context.startActivity(intent);
                reference.removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Price, P_price, qty, Delete, totalamount;
        ImageView Add, Sub, CartImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.cartname);
            Price = itemView.findViewById(R.id.cartprice);
            P_price = itemView.findViewById(R.id.pprice);
            CartImage = itemView.findViewById(R.id.cartimg);
            Add = itemView.findViewById(R.id.add);
            Sub = itemView.findViewById(R.id.sub);
            qty = itemView.findViewById(R.id.qty);
            Delete = itemView.findViewById(R.id.delete);
            totalamount = itemView.findViewById(R.id.total);
        }
    }

}
