package com.example.shoptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Markets extends AppCompatActivity {

    ImageView BackArrow;
    RecyclerMarketsAdapter recyclerMarketsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markets);

        BackArrow = findViewById(R.id.mback);
        DatabaseReference databaseReference;

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Markets.this, Home.class);
                startActivity(intent);
            }
        });
//        recyclerMarketsAdapter = new RecyclerMarketsAdapter(mdatalist());
        databaseReference = FirebaseDatabase.getInstance().getReference("Markets");
        RecyclerView recyclerView = findViewById(R.id.marketrcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MarketsModel> options =
                new FirebaseRecyclerOptions.Builder<MarketsModel>()
                        .setQuery(databaseReference, MarketsModel.class)
                        .build();

        recyclerMarketsAdapter = new RecyclerMarketsAdapter(options);
        recyclerView.setAdapter(recyclerMarketsAdapter);

        recyclerView.setAdapter(recyclerMarketsAdapter);
    }

    @Override protected void onStart()
    {
        super.onStart();
        recyclerMarketsAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        recyclerMarketsAdapter.stopListening();
    }





//    public ArrayList<MarketsModel> mdatalist()
//    {
//        ArrayList<MarketsModel> holder = new ArrayList<>();
//
//        MarketsModel M1 = new MarketsModel();
//        M1.setMname("TandoAllahyar");
//        M1.setDetails("Loram Ipsum");
//        M1.setTiming("08:00-23:00");
//        holder.add(M1);
//
//        MarketsModel M2 = new MarketsModel();
//        M2.setMname("Karachi");
//        M2.setDetails("Loram Ipsum");
//        M2.setTiming("11:00-20:00");
//        holder.add(M2);
//
//        MarketsModel M3 = new MarketsModel();
//        M3.setMname("ISlamabad");
//        M3.setDetails("Loram Ipsum");
//        M3.setTiming("09:00-23:00");
//        holder.add(M3);
//
//        MarketsModel M4 = new MarketsModel();
//        M4.setMname("MirpurKhas");
//        M4.setDetails("Loram Ipsum");
//        M4.setTiming("08:00-23:00");
//        holder.add(M4);
//
//        MarketsModel M5 = new MarketsModel();
//        M4.setMname("Hyderabad");
//        M4.setDetails("Loram Ipsum");
//        M4.setTiming("08:00-20:00");
//        holder.add(M4);
//
//        return holder;
//    }
}