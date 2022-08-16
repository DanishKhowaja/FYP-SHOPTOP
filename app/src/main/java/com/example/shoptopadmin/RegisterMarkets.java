package com.example.shoptopadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterMarkets extends AppCompatActivity {

    ImageView BackArrow;
    RecyclerMarketAdapter recyclerMarketAdapter;
    RecyclerView recyclerView;
    FloatingActionButton addmarkets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_markets);
        BackArrow = findViewById(R.id.mback);
        addmarkets = findViewById(R.id.addmarket);
        DatabaseReference databaseReference;

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMarkets.this, Dashboard.class);
                startActivity(intent);
            }
        });

        addmarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMarkets.this, MarketForm.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Markets");
        recyclerView = findViewById(R.id.marketrcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MarketModel> options =
                new FirebaseRecyclerOptions.Builder<MarketModel>()
                        .setQuery(databaseReference, MarketModel.class)
                        .build();

        recyclerMarketAdapter = new RecyclerMarketAdapter(options);
        recyclerView.setAdapter(recyclerMarketAdapter);

    }

    @Override protected void onStart()
    {
        super.onStart();
        recyclerMarketAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        recyclerMarketAdapter.stopListening();
    }

}