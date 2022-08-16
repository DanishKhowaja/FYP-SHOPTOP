package com.example.shoptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class SupplierCattle extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerSupplierCattleAdapter recyclerSupplierCattleAdapter;
    DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;
    FloatingActionButton AddCattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_cattle);

        AddCattle = findViewById(R.id.addcattle);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();

        recyclerView = findViewById(R.id.supplierrcvcattle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CattleModel> options =
                new FirebaseRecyclerOptions.Builder<CattleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Cattles").orderByChild("userid").equalTo(UserId), CattleModel.class)
                        .build();

        recyclerSupplierCattleAdapter = new RecyclerSupplierCattleAdapter(options);
        recyclerView.setAdapter(recyclerSupplierCattleAdapter);

        AddCattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierCattle.this,UploadCattle.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        recyclerSupplierCattleAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerSupplierCattleAdapter.stopListening();
    }
}