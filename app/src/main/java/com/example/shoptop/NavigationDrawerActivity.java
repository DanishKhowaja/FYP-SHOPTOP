package com.example.shoptop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String UserId;


    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_drawer);

        navigationView = findViewById(R.id.nav_view);
        ImageView expand = findViewById(R.id.btnBackForgot);
        drawer = findViewById(R.id.drawer_layout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Buyers");
        UserId = user.getUid();

        View headerview = navigationView.getHeaderView(0);
        TextView headername = headerview.findViewById(R.id.headerName);

        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegisterUser userprofile = snapshot.getValue(RegisterUser.class);

                if(userprofile != null)
                {
                    String FName = userprofile.FName;

                    headername.setText(FName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NavigationDrawerActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });




        auth =FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        activity = this;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        Fragment temp=null;
        switch (item.getItemId()){
            case R.id.delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(NavigationDrawerActivity.this);
                dialog.setTitle("Are You Sure?");
                dialog.setMessage("Deleting this account will result completely removing this account from system and you won't be able to access this app again.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(NavigationDrawerActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(NavigationDrawerActivity.this,Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(NavigationDrawerActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                break;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(NavigationDrawerActivity.this,Login.class);
                startActivity(intent);
                break;
            case  R.id.order:
                Intent intent3  = new Intent(NavigationDrawerActivity.this,Orders.class);
                startActivity(intent3);
        }
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
