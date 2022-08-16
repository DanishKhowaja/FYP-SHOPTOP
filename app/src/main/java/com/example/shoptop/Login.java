package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button Login, SupplierLogin;
    TextView Forgot, SupplierForgot, Signup;

    TextInputEditText Email, Password, SupplierEmail, SupplierPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.btnlogin);
        SupplierLogin = findViewById(R.id.supplierlogin);
        Forgot = findViewById(R.id.txtforgot);
        SupplierForgot = findViewById(R.id.supplierforgot);
        Signup = findViewById(R.id.signup);
        LinearLayout buyerlayout = findViewById(R.id.BuyerLayout);
        LinearLayout supplierlayout = findViewById(R.id.SupplierLayout);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.pass);

        SupplierEmail = findViewById(R.id.supplieremail);
        SupplierPassword = findViewById(R.id.supplierpass);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        TabLayout tabLayout = findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabLayout.setBackgroundResource(R.drawable.bg_btnleft);
                    buyerlayout.setVisibility(View.VISIBLE);
                    supplierlayout.setVisibility(View.GONE);
                }
                else
                {
                    tabLayout.setBackgroundResource(R.drawable.bg_btnright);
                    buyerlayout.setVisibility(View.GONE);
                    supplierlayout.setVisibility(View.VISIBLE);
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

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });

        SupplierLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierLogin();

            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        SupplierForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, BuyerCreateAccount.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin() {

        String EMAIL = Email.getText().toString().trim();
        String PASS = Password.getText().toString().trim();

        if  (EMAIL.isEmpty())
        {
            Email.setError("Enter Your Email Address");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches())
        {
            Email.setError("Please Provide Valid Email");
            Email.requestFocus();
            return;
        }
        if  (PASS.isEmpty())
        {
            Password.setError("Enter Your Password");
            Password.requestFocus();
            return;
        }
        if  (PASS.length() < 8)
        {
            Password.setError("Please Enter Correct Password");
            Password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        Intent intent = new Intent(Login.this,Home.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Login.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(Login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

    private void SupplierLogin() {

        String SupplierEMAIL = SupplierEmail.getText().toString().trim();
        String SupplierPASS = SupplierPassword.getText().toString().trim();

        if  (SupplierEMAIL.isEmpty())
        {
            SupplierEmail.setError("Enter Your Email Address");
            SupplierEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(SupplierEMAIL).matches())
        {
            SupplierEmail.setError("Please Provide Valid Email");
            SupplierEmail.requestFocus();
            return;
        }
        if  (SupplierPASS.isEmpty())
        {
            SupplierPassword.setError("Enter Your Password");
            SupplierPassword.requestFocus();
            return;
        }
        if  (SupplierPASS.length() < 8)
        {
            SupplierPassword.setError("Please Enter Correct Password");
            SupplierPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(SupplierEMAIL,SupplierPASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        Intent intent = new Intent(Login.this,SupplierDashboard.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Login.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(Login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

    public void onBackPressed() {
        finishAffinity();
    }
}