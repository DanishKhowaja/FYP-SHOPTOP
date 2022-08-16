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

public class SupplierFruit extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerSupplierFruitAdapter recyclerSupplierFruitAdapter;
    DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;
    FloatingActionButton AddFruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_fruit);

        AddFruits = findViewById(R.id.addfruit);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        recyclerView = findViewById(R.id.supplierrcvfruits);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<FruitsModel> options =
                new FirebaseRecyclerOptions.Builder<FruitsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Fruits").orderByChild("userid").equalTo(UserId), FruitsModel.class)
                        .build();

        recyclerSupplierFruitAdapter = new RecyclerSupplierFruitAdapter(options);
        recyclerView.setAdapter(recyclerSupplierFruitAdapter);

        AddFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierFruit.this, UploadFruits.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerSupplierFruitAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerSupplierFruitAdapter.stopListening();
    }

}