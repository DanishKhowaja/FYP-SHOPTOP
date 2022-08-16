package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewOrder extends AppCompatActivity {

    TextView Productname, Count, Price, TotalPrice, BuyerName, BuyerAddress, BuyerContact;
    String BuyerID, Buyername, Buyeradd, Buyernumber;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Productname = findViewById(R.id.productname);
        Count = findViewById(R.id.productcount);
        Price = findViewById(R.id.productprice);
        TotalPrice = findViewById(R.id.producttotal);
        BuyerName = findViewById(R.id.buyername);
        BuyerAddress = findViewById(R.id.buyeraddress);
        BuyerContact = findViewById(R.id.buyernumber);

        Bundle bundle = getIntent().getExtras();

        Productname.setText(bundle.getString("Name"));
        Count.setText(bundle.getString("Count"));
        Price.setText(bundle.getString("Price"));
        TotalPrice.setText(bundle.getString("Total Amount"));
        BuyerID= bundle.getString("BuyerID");
        getData();
    }

    private void getData() {
        databaseReference =FirebaseDatabase.getInstance().getReference("Buyers");
        databaseReference.child(BuyerID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot snapshot = task.getResult();
                        Buyername = String.valueOf(snapshot.child("FName").getValue());
                        Buyeradd = String.valueOf(snapshot.child("Address").getValue());
                        Buyernumber = String.valueOf(snapshot.child("ContactNumber").getValue());
                        BuyerName.setText(Buyername);
                        BuyerAddress.setText(Buyeradd);
                        BuyerContact.setText(Buyernumber);
                    }
                }
            }
        });
    }
}