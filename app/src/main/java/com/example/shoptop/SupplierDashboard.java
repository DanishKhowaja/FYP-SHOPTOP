package com.example.shoptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SupplierDashboard extends AppCompatActivity {

    LinearLayout SupplierFrt, SupplierVeg, SupplierCattle, SupplierOrder;
    TextView DateTime;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_dashboard);

        SupplierFrt = findViewById(R.id.supplierfruit);
        SupplierVeg = findViewById(R.id.supplierveg);
        SupplierCattle = findViewById(R.id.suppliercattle);
        SupplierOrder = findViewById(R.id.supplierorder);
        DateTime = findViewById(R.id.datetime);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yy" );
        date = dateFormat.format(calendar.getTime());
        DateTime.setText(date);

        SupplierFrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, SupplierFruit.class);
                startActivity(intent);
            }
        });

        SupplierVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, SupplierVegetable.class);
                startActivity(intent);
            }
        });

        SupplierCattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, SupplierCattle.class);
                startActivity(intent);
            }
        });
        SupplierOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, SupplierOrder.class);
                startActivity(intent);
            }
        });


    }

}