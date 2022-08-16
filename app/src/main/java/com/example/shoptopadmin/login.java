package com.example.shoptopadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Button Login;
    TextInputEditText Email, Password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.btnlogin);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

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

        mAuth.signInWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        Intent intent = new Intent(login.this,Dashboard.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(login.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void onBackPressed() {
        finishAffinity();
    }
}