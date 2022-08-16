package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cattle extends AppCompatActivity {

    RecyclerCattleAdapter recyclerCattleAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle);

        searchView = findViewById(R.id.csearch);
        recyclerView = findViewById(R.id.rcvcattle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CattleModel> options =
                new FirebaseRecyclerOptions.Builder<CattleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Cattles"), CattleModel.class)
                        .build();

        recyclerCattleAdapter = new RecyclerCattleAdapter(options);
        recyclerView.setAdapter(recyclerCattleAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<CattleModel> options =
                new FirebaseRecyclerOptions.Builder<CattleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Cattles").orderByChild("Address").startAt(s).endAt(s+"\uf8ff"), CattleModel.class)
                        .build();

        recyclerCattleAdapter = new RecyclerCattleAdapter(options);
        recyclerCattleAdapter.startListening();
        recyclerView.setAdapter(recyclerCattleAdapter);
    }


    @Override
    public void onStart() {
    super.onStart();
    recyclerCattleAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerCattleAdapter.stopListening();
    }
}