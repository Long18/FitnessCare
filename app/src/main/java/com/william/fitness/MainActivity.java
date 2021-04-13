package com.william.fitness;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.william.fitness.Login.LoginActivity;

public class  MainActivity extends AppCompatActivity {

    Button btnlogout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnlogout = findViewById(R.id.btnLogout);
        //btnProfile = findViewById(R.id.);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnvgbar);

        // Select menu on navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_Calendar:
                        Intent calendar = new Intent(MainActivity.this, Calendar.class);
                        startActivity(calendar);
                        break;
                    case R.id.ic_add:
                        Intent add = new Intent(MainActivity.this, Daily_Training.class);
                        startActivity(add);
                        break;
                    case R.id.ic_recent:
                        Intent recent = new Intent(MainActivity.this, ListExercise.class);
                        startActivity(recent);
                        break;
                    case R.id.ic_User:
                        Intent user = new Intent(MainActivity.this, Settings.class);
                        startActivity(user);
                        break;
                }
                return false;
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //Logout
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
/*
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();//--
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);
                finish();//--
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Daily_Training.class);
                startActivity(intent);
                finish();//--
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                finish();//--
            }
        });

        btnExercise.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListExercise.class);
                int REQUEST_CODE =9;
                startActivityForResult(intent,REQUEST_CODE);
                finish();//--
            }
        });

        */

    }



}