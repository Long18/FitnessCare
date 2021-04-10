package com.william.fitness;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.william.fitness.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Button btnExercise,btnSetting,btnCalendar, btnlogout;
    ImageView btnTraining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnExercise = findViewById(R.id.btnExercise);
        btnSetting = findViewById(R.id.btnSetting);
        btnTraining = findViewById(R.id.btnTraining);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnlogout = findViewById(R.id.btnLogout);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Daily_Training.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        btnExercise.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListExercise.class);
                int REQUEST_CODE =9;
                startActivityForResult(intent,REQUEST_CODE);
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

    }



}