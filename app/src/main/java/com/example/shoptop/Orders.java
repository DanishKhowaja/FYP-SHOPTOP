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

public class Orders extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerOrderAdapter recyclerOrderAdapter;
    DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        recyclerView = findViewById(R.id.rcvorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Orders").orderByChild("BuyerID").equalTo(UserId), OrderModel.class)
                        .build();

        recyclerOrderAdapter = new RecyclerOrderAdapter(options);
        recyclerView.setAdapter(recyclerOrderAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        recyclerOrderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerOrderAdapter.stopListening();
    }
}
