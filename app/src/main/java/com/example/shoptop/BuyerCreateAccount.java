package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerCreateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button SIGNUP, SellerSignup;
    TextView Signin;
    ImageView back;

    private TextInputEditText FirstName, LastName, Address, Email, ContactNumber, Password, ConfirmPassword;
    private TextInputEditText SellerFirstName, SellerLastName, SellerAddress, SellerEmail, SellerContactNumber, SellerPassword, SellerConfirmPassword;
    TextInputLayout FNameLayout, LNameLayout, AddressLayout, EmailLayout, NumberLayout, PasswordLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_create_account);
        LinearLayout SupplierRegister = findViewById(R.id.sellerregister);
        LinearLayout BuyerRegister = findViewById(R.id.ll2);

        TabLayout tabLayout = findViewById(R.id.tab_layout2);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabLayout.setBackgroundResource(R.drawable.bg_btnleft);
                    BuyerRegister.setVisibility(View.VISIBLE);
                    SupplierRegister.setVisibility(View.GONE);
                }
                else
                {
                    tabLayout.setBackgroundResource(R.drawable.bg_btnright);
                    BuyerRegister.setVisibility(View.GONE);
                    SupplierRegister.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAuth = FirebaseAuth.getInstance();

        FirstName = findViewById(R.id.fname);
        LastName = findViewById(R.id.lname);
        Address = findViewById(R.id.address);
        Email = findViewById(R.id.email);
        ContactNumber = findViewById(R.id.mobnumber);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.cpassword);

        SellerFirstName = findViewById(R.id.sellerfname);
        SellerLastName = findViewById(R.id.sellerlname);
        SellerAddress = findViewById(R.id.selleraddress);
        SellerEmail = findViewById(R.id.selleremail);
        SellerContactNumber = findViewById(R.id.sellermobnumber);
        SellerPassword = findViewById(R.id.sellerpassword);
        SellerConfirmPassword = findViewById(R.id.sellercpassword);

        FNameLayout =findViewById(R.id.textField1);
        LNameLayout =findViewById(R.id.textField2);
        AddressLayout =findViewById(R.id.textField3);
        EmailLayout =findViewById(R.id.textField4);
        NumberLayout =findViewById(R.id.textField5);
        PasswordLayout =findViewById(R.id.textField6);

        SIGNUP =findViewById(R.id.btnsignup);
        SellerSignup = findViewById(R.id.sellersignup);
        Signin = findViewById(R.id.signin);
        back = findViewById(R.id.back);

        progressBar = findViewById(R.id.progressBar);

        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        SellerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSupplier();
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerCreateAccount.this,Login.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerCreateAccount.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void registerUser()
    {
        String FName = FirstName.getText().toString().trim();
        String LName = LastName.getText().toString().trim();
        String address = Address.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String C_number = ContactNumber.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String CPassword = ConfirmPassword.getText().toString().trim();

        if (FName.isEmpty())
        {
            FirstName.setError("Enter Your First Name");
            FirstName.requestFocus();
            return;
        }
        if (LName.isEmpty())
        {
            LastName.setError("Enter Your Last Name");
            LastName.requestFocus();
            return;
        }
        if  (address.isEmpty())
        {
            Address.setError("Enter Your Address");
            Address.requestFocus();
            return;
        }
        if  (email.isEmpty())
        {
            Email.setError("Enter Your Email Address");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Please Provide Valid Email");
            Email.requestFocus();
            return;
        }
        if  (C_number.isEmpty())
        {
            ContactNumber.setError("Enter Your Contact Number");
            ContactNumber.requestFocus();
            return;
        }
        if(C_number.length() < 11)
        {
            ContactNumber.setError("Phone Number must have 11 Digits");
            ContactNumber.requestFocus();
            return;
        }
        if  (password.isEmpty())
        {
            Password.setError("Enter Your Password");
            Password.requestFocus();
            return;
        }
        if  (password.length() < 8)
        {
            Password.setError("Minimum Password length should be 8 characters");
            Password.requestFocus();
            return;
        }
        if (!CPassword.equals(password))
        {
            ConfirmPassword.setError("Enter Correct Password");
            ConfirmPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            RegisterUser registerUser = new RegisterUser(FName,LName,address,email,C_number);

                            FirebaseDatabase.getInstance().getReference("Buyers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(registerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        if (user.isEmailVerified())
                                        {
                                            Intent intent =new Intent(BuyerCreateAccount.this, Login.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            user.sendEmailVerification();
                                            Toast.makeText(BuyerCreateAccount.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                            Intent intent =new Intent(BuyerCreateAccount.this, Login.class);
                                            startActivity(intent);
                                        }

                                    }
                                    else {
                                        Toast.makeText(BuyerCreateAccount.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(BuyerCreateAccount.this, "Failed to Register account", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
    public void registerSupplier()
    {
        String SellerFName = SellerFirstName.getText().toString().trim();
        String SellerLName = SellerLastName.getText().toString().trim();
        String Selleraddress = SellerAddress.getText().toString().trim();
        String Selleremail = SellerEmail.getText().toString().trim();
        String SellerC_number = SellerContactNumber.getText().toString().trim();
        String Sellerpassword = SellerPassword.getText().toString().trim();
        String SellerCPassword = SellerConfirmPassword.getText().toString().trim();

        if (SellerFName.isEmpty())
        {
            SellerFirstName.setError("Enter Your First Name");
            SellerFirstName.requestFocus();
            return;
        }
        if (SellerLName.isEmpty())
        {
            SellerLastName.setError("Enter Your Last Name");
            SellerLastName.requestFocus();
            return;
        }
        if  (Selleraddress.isEmpty())
        {
            SellerAddress.setError("Enter Your Address");
            SellerAddress.requestFocus();
            return;
        }
        if  (Selleremail.isEmpty())
        {
            Email.setError("Enter Your Email Address");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Selleremail).matches())
        {
            SellerEmail.setError("Please Provide Valid Email");
            SellerEmail.requestFocus();
            return;
        }
        if  (SellerC_number.isEmpty())
        {
            SellerContactNumber.setError("Enter Your Contact Number");
            SellerContactNumber.requestFocus();
            return;
        }
        if(SellerC_number.length() < 11)
        {
            SellerContactNumber.setError("Phone Number must have 11 Digits");
            SellerContactNumber.requestFocus();
            return;
        }
        if  (Sellerpassword.isEmpty())
        {
            SellerPassword.setError("Enter Your Password");
            SellerPassword.requestFocus();
            return;
        }
        if  (Sellerpassword.length() < 8)
        {
            SellerPassword.setError("Minimum Password length should be 8 characters");
            SellerPassword.requestFocus();
            return;
        }
        if (!SellerCPassword.equals(Sellerpassword))
        {
            SellerConfirmPassword.setError("Enter Correct Password");
            SellerConfirmPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Selleremail,Sellerpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            RegisterUser registerUser = new RegisterUser(SellerFName,SellerLName,Selleraddress,Selleremail,SellerC_number);

                            FirebaseDatabase.getInstance().getReference("Suppliers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(registerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        if (user.isEmailVerified())
                                        {
                                            Intent intent =new Intent(BuyerCreateAccount.this, Login.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            user.sendEmailVerification();
                                            Toast.makeText(BuyerCreateAccount.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                            Intent intent =new Intent(BuyerCreateAccount.this, Login.class);
                                            startActivity(intent);
                                        }

                                    }
                                    else {
                                        Toast.makeText(BuyerCreateAccount.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(BuyerCreateAccount.this, "Failed to Register account", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}