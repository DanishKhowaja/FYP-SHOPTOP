package com.example.shoptop;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class
ProfileFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    String FName,LName,EMAIL,ContactNumber, ADDRESS;

    Button Cancel, Update;

    private String UserId;
    TextInputEditText Name, Email, MobileNumber, Address;
    TextInputLayout AddressLayout, NumberLayout;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Name = view.findViewById(R.id.pname);
        Email = view.findViewById(R.id.pemail);
        MobileNumber = view.findViewById(R.id.pmobnumber);
        Address = view.findViewById(R.id.paddress);
        Update = view.findViewById(R.id.btn_update);
        AddressLayout = view.findViewById(R.id.ptextField4);
        NumberLayout = view.findViewById(R.id.ptextField3);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Buyers");
        UserId = user.getUid();



        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegisterUser userprofile = snapshot.getValue(RegisterUser.class);

                if(userprofile != null)
                {
                    FName = userprofile.FName;
                    LName = userprofile.LName;
                    EMAIL = userprofile.Email;
                    ContactNumber = userprofile.ContactNumber;
                    ADDRESS = userprofile.Address;

                    Name.setText(FName+" "+LName);
                    Email.setText(EMAIL);
                    MobileNumber.setText(ContactNumber);
                    Address.setText(ADDRESS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private boolean isAddressChanged() {
        if (!ADDRESS.equals(Address.getText().toString()))
        {
            reference.child(UserId).child("Address").setValue(Address.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNumberChanged() {
        if (!ContactNumber.equals(MobileNumber.getText().toString()))
        {
            reference.child(UserId).child("ContactNumber").setValue(MobileNumber.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
    public void registerUser()
    {

        String address = Address.getText().toString().trim();
        String C_number = MobileNumber.getText().toString().trim();
        if  (address.isEmpty())
        {
            Address.setError("Enter Your Address");
            Address.requestFocus();
            return;
        }

        if  (C_number.isEmpty())
        {
            MobileNumber.setError("Enter Your Contact Number");
            MobileNumber.requestFocus();
            return;
        }
        if(C_number.length() < 11)
        {
            MobileNumber.setError("Phone Number must have 11 Digits");
            MobileNumber.requestFocus();
            return;
        }
        if (isNumberChanged() || isAddressChanged() )
        {
            Toast.makeText(getContext(), "Data Has been Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Data is same and can not be Updated", Toast.LENGTH_SHORT).show();
        }
    }
}