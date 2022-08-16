package com.example.shoptop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cart extends Fragment implements PaymentResultListener {

    RecyclerView recyclerView;
    ArrayList<CartModel> cartlist;
    RecyclerCartAdapter recyclerCartAdapter;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReference1;
    String userid;
    TextView total;
    Button PlaceOrder;
    CartModel cartModel;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cart() {
    }


    // TODO: Rename and change types and number of parameters
    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        PlaceOrder = view.findViewById(R.id.place_order);
        Checkout.preload(getContext());

        recyclerView = view.findViewById(R.id.recycler_cart);
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyers").child(userid).child("CartItems");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Orders");

        cartlist = new ArrayList<>();
        recyclerCartAdapter = new RecyclerCartAdapter(getContext(),cartlist);
        recyclerView.setAdapter(recyclerCartAdapter);


        total = view.findViewById(R.id.total);

        recyclerCartAdapter.OnDataSend(new RecyclerCartAdapter.OnDataSend() {
            @Override
            public void onSend(int TotalRate) {
                total.setText(String.valueOf(TotalRate));
            }
        });

        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Amount =total.getText().toString();
                makepayment(Amount);
//                OrderModel orderModel = new OrderModel();
                String Name = cartModel.getName();
                String price = String.valueOf(cartModel.getPrice());
                String count = String.valueOf(cartModel.getCount());
                String supplierid = cartModel.getUserid();
                int totalamount = (cartModel.getPrice())*(cartModel.getCount());
                String TotalAmount = String.valueOf(totalamount);
//                orderModel.setName(Name);
//                orderModel.setPrice(price);
//                orderModel.setCount(count);
//                databaseReference1.setValue(orderModel);

                Map<String,Object> map = new HashMap<>();
                map.put("Name",Name);
                map.put("Price",price);
                map.put("Count",count);
                map.put("SupplierID",supplierid);
                map.put("TotalAmount",TotalAmount);
                map.put("BuyerID",userid);
                databaseReference1.push().setValue(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        databaseReference.removeValue();
                        Toast.makeText(getContext(), "data inserted", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });

//                databaseReference1.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        databaseReference1.push().setValue(orderModel);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                Toast.makeText(getContext(), total.getText(), Toast.LENGTH_SHORT).show();
            }
        });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    cartModel = dataSnapshot.getValue(CartModel.class);
                    cartlist.add(cartModel);
                }
                recyclerCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

    private void makepayment( String amount) {
        final Context cart = getContext();

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_nblqmD02kre73o");
        checkout.setImage(R.drawable.logo);

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "ShopTop");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "PKR");
            options.put("amount", finalAmount+"");//pass amount in currency subunits
            options.put("prefill.email", "cs1812108@szabist.pk");
            options.put("prefill.contact","3402612584");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open((Activity) cart, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}