package com.example.shoptopadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users extends AppCompatActivity {

    ImageView BackArrow;
    RecyclerBuyerAdapter recyclerBuyerAdapter;
    RecyclerSupplierAdapter recyclerSupplierAdapter;
    RecyclerView recyclerView, recyclerView2;
    FloatingActionButton AddMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        BackArrow = findViewById(R.id.back);
        DatabaseReference databaseReference, dbrf;
//        LinearLayout BuyerUsers = findViewById(R.id.buyerusers);
//        LinearLayout SupplierUsers = findViewById(R.id.supplierusers);
//
//        TabLayout tabLayout = findViewById(R.id.tab_layout1);
//        tabLayout.addTab(tabLayout.newTab());
//        tabLayout.addTab(tabLayout.newTab());
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    tabLayout.setBackgroundResource(R.drawable.btn_left);
//                    BuyerUsers.setVisibility(View.VISIBLE);
//                    SupplierUsers.setVisibility(View.GONE);
//                }
//                else
//                {
//                    tabLayout.setBackgroundResource(R.drawable.btn_right);
//                    BuyerUsers.setVisibility(View.GONE);
//                    SupplierUsers.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Users.this, Dashboard.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers");
//        dbrf = FirebaseDatabase.getInstance().getReference("Suppliers");
        recyclerView = findViewById(R.id.rcvusers);


//        recyclerView2 = findViewById(R.id.supplierrcvusers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<BuyerModel> options =
                new FirebaseRecyclerOptions.Builder<BuyerModel>()
                        .setQuery(databaseReference, BuyerModel.class)
                        .build();

//        FirebaseRecyclerOptions<BuyerModel> options1 =
//                new FirebaseRecyclerOptions.Builder<BuyerModel>()
//                        .setQuery(dbrf, BuyerModel.class)
//                        .build();

        recyclerBuyerAdapter = new RecyclerBuyerAdapter(options);
        recyclerView.setAdapter(recyclerBuyerAdapter);

//        recyclerSupplierAdapter = new RecyclerSupplierAdapter(options1);
//        recyclerView2.setAdapter(recyclerSupplierAdapter);


    }
    @Override protected void onStart()
    {
        super.onStart();
        recyclerBuyerAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        recyclerBuyerAdapter.stopListening();
    }
}