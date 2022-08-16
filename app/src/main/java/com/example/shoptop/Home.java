package com.example.shoptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends NavigationDrawerActivity {

    TextView title;
    BottomNavigationView bnv;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);


        FrameLayout mainContent = findViewById(R.id.mainContent);
        getLayoutInflater().inflate(R.layout.activity_home, mainContent);

        title = findViewById(R.id.Ttitle);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();


        bnv =(BottomNavigationView)findViewById(R.id.navigationbar);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;

                switch (item.getItemId()){
                    case R.id.menu_home:
                        temp = new DashboardFragment();
                        title.setText("");
                        break;
//                    case R.id.menu_notification:
//                        temp = new NotificationFragment();
//                        break;
                    case R.id.menu_cart:
                        temp = new Cart();
                        title.setText("Cart");
                        break;
                    case R.id.menu_profile:
                        temp = new ProfileFragment();
                        title.setText("Profile");

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,temp).commit();




                return true;
            }
        });


    }



private long pressedTime;
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            startActivity(getIntent());
        }
        pressedTime = System.currentTimeMillis();
    }

}