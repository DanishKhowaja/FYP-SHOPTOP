package com.example.shoptopadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MarketForm extends AppCompatActivity {

    ImageView BackArrow;
    EditText MName;
    EditText MAddress;
    Button btnsave;

    DatabaseReference databaseReference;
    MarketModel marketModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_form);

        BackArrow = findViewById(R.id.mfback);
        MName = findViewById(R.id.marketname);
        MAddress = findViewById(R.id.marketaddress);
        btnsave = findViewById(R.id.btnsave);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketForm.this, RegisterMarkets.class);
                startActivity(intent);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = MName.getText().toString();
                String Address = MAddress.getText().toString();

                marketModel = new MarketModel();
                databaseReference = FirebaseDatabase.getInstance().getReference("Markets");

                if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(Address)) {
                    // if the text fields are empty 
                    // then show the below message.
                    Toast.makeText(MarketForm.this, "Complete All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add 
                    // data to our database.
                    addDatatoFirebase(Name, Address);
                }
                
            }
        });


    }

    private void addDatatoFirebase(String Name, String Address) {
        marketModel.setName(Name);
        marketModel.setAddress(Address);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(Name).setValue(marketModel);
                Toast.makeText(MarketForm.this, "data added", Toast.LENGTH_SHORT).show();
                MName.setText("");
                MAddress.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MarketForm.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}