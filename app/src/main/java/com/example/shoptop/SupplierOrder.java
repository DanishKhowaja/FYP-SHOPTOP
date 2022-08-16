package com.example.shoptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupplierOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerSupplierOrderAdapter recyclerSupplierOrderAdapter;
    DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_order);
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        recyclerView = findViewById(R.id.rcvsupplierorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Orders").orderByChild("SupplierID").equalTo(UserId), OrderModel.class)
                        .build();

        recyclerSupplierOrderAdapter = new RecyclerSupplierOrderAdapter(options);
        recyclerView.setAdapter(recyclerSupplierOrderAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerSupplierOrderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerSupplierOrderAdapter.stopListening();
    }
}