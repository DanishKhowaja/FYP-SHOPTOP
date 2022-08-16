package com.example.shoptop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


public class Fruit extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerFruitAdapter recyclerFruitAdapter;
    FirebaseUser user;
    DatabaseReference reference;
    String UserId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fruit() {
    }

    // TODO: Rename and change types and number of parameters
    public static Fruit newInstance(String param1, String param2) {
        Fruit fragment = new Fruit();
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
        View view = inflater.inflate(R.layout.fragment_fruit, container, false);


        searchView = view.findViewById(R.id.search);

        recyclerView = view.findViewById(R.id.rcvfruits);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        FirebaseRecyclerOptions<FruitsModel> options =
                new FirebaseRecyclerOptions.Builder<FruitsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Fruits"), FruitsModel.class)
                        .build();

        recyclerFruitAdapter = new RecyclerFruitAdapter(options);
        recyclerView.setAdapter(recyclerFruitAdapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Buyers").child(UserId).child("CartItems");

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
        FirebaseRecyclerOptions<FruitsModel> options =
                new FirebaseRecyclerOptions.Builder<FruitsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Fruits").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), FruitsModel.class)
                        .build();

        recyclerFruitAdapter = new RecyclerFruitAdapter(options);
        recyclerFruitAdapter.startListening();
        recyclerView.setAdapter(recyclerFruitAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerFruitAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerFruitAdapter.stopListening();
    }


    public void switchFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fruits, fragment)
                .addToBackStack(null)
                .commit();


    }
    public void onBackPressed() {
        switchFragment(new DashboardFragment());
    }
}