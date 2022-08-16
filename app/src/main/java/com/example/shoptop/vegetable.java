package com.example.shoptop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class vegetable extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerVegAdapter recyclerVegAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public vegetable() {
    }

    // TODO: Rename and change types and number of parameters
    public static vegetable newInstance(String param1, String param2) {
        vegetable fragment = new vegetable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vegetable, container, false);
        searchView = view.findViewById(R.id.vegsearch);

        recyclerView = view.findViewById(R.id.rcvveg);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<VegModel> options =
                new FirebaseRecyclerOptions.Builder<VegModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Vegetables"), VegModel.class)
                        .build();

        recyclerVegAdapter = new RecyclerVegAdapter(options);
        recyclerView.setAdapter(recyclerVegAdapter);

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



        return view;
    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<VegModel> options =
                new FirebaseRecyclerOptions.Builder<VegModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Vegetables").orderByChild("vname").startAt(s).endAt(s+"\uf8ff"), VegModel.class)
                        .build();

        recyclerVegAdapter = new RecyclerVegAdapter(options);
        recyclerVegAdapter.startListening();
        recyclerView.setAdapter(recyclerVegAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        recyclerVegAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerVegAdapter.stopListening();
    }
}