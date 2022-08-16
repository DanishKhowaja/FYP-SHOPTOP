package com.example.shoptop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewVeg extends Fragment {
    String name,address, vpurl, userid;
    int price;
    ImageView Add;
    ImageView Sub;
    TextView textcount;
    int count = 0;
    FirebaseUser user;
    DatabaseReference reference;
    Button AddToCart;
    String UserId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewVeg() {
    }
    public ViewVeg(String name,String address, int price, String vpurl, String userid) {
        this.name=name;
        this.address=address;
        this.price = price;
        this.vpurl = vpurl;
        this.userid = userid;
    }

    // TODO: Rename and change types and number of parameters
    public static ViewVeg newInstance(String param1, String param2) {
        ViewVeg fragment = new ViewVeg();
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
        View view = inflater.inflate(R.layout.fragment_view_veg, container, false);
        TextView Name = view.findViewById(R.id.vegname);
        TextView Address = view.findViewById(R.id.vegmarket);
        TextView Price = view.findViewById(R.id.vegprice);
        ImageView vegviewimg = view.findViewById(R.id.viewvegimage);

        Name.setText(name);
        Address.setText(address);
        Price.setText(Integer.toString(price));
        Glide.with(getContext()).load(vpurl).into(vegviewimg);

        Add = view.findViewById(R.id.add1);
        Sub = view.findViewById(R.id.sub1);
        textcount = view.findViewById(R.id.textViewCount1);
        AddToCart = view.findViewById(R.id.addtocart1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        UserId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Buyers").child(UserId).child("CartItems");

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                textcount.setText(Integer.toString(count));
            }
        });

        Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <=1) {
                    count =1;
                }
                else {
                    count --;
                    textcount.setText(Integer.toString(count));
                }
            }
        });

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartModel cartModel = new CartModel();
                cartModel.setName(name);
                cartModel.setPrice(price);
                cartModel.setCount(Integer.parseInt(textcount.getText().toString()));
                cartModel.setPurl(vpurl);
                cartModel.setUserid(userid);
                reference.push().setValue(cartModel);
                Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();

            }
        });




        return view;
    }
}