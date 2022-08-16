package com.example.shoptopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {

    LinearLayout SUPPLIER;
    LinearLayout BUYER;
    LinearLayout Markets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SUPPLIER = findViewById(R.id.supplier);
        BUYER = findViewById(R.id.buyer);
        Markets = findViewById(R.id.market);

        SUPPLIER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Suppliers.class);
                startActivity(intent);
            }
        });

        BUYER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Users.class);
                startActivity(intent);
            }
        });
        Markets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, RegisterMarkets.class);
                startActivity(intent);
            }
        });
    }
}