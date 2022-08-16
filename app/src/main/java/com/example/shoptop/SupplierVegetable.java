package com.example.shoptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupplierVegetable extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerSupplierVegAdapter recyclerSupplierVegAdapter;
    DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;
    FloatingActionButton AddVeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_vegetable);

        AddVeg = findViewById(R.id.addveg);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        recyclerView = findViewById(R.id.supplierrcvveg);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<VegModel> options =
                new FirebaseRecyclerOptions.Builder<VegModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Vegetables").orderByChild("userid").equalTo(UserId), VegModel.class)
                        .build();

        recyclerSupplierVegAdapter = new RecyclerSupplierVegAdapter(options);
        recyclerView.setAdapter(recyclerSupplierVegAdapter);

        AddVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierVegetable.this, UploadVegetable.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerSupplierVegAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerSupplierVegAdapter.stopListening();
    }
}