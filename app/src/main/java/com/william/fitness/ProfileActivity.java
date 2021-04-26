package com.william.fitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.william.fitness.Login.Login;

public class ProfileActivity extends AppCompatActivity {
    TextView btnHome, txtName, txtNumber,txtAddress,txtBirth, txtEmail, txtNameUser,txtEmailUser;
    String name, number,email,address,birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // btnHome = findViewById(R.id.btnHome);
        txtName = findViewById(R.id.txtName);
        txtNumber = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtBirth = findViewById(R.id.txtBirth);
        txtEmail = findViewById(R.id.txtEmail);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtEmailUser = findViewById(R.id.txtEmailUser);

        setSupportActionBar(toolbar);


    }
    private void showAllUserData() {
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        number = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        address = intent.getStringExtra("address");
        birth = intent.getStringExtra("birthday");

        txtName.setText(MainActivity.name);
        txtNumber.setText(MainActivity.number);
        txtEmail.setText(MainActivity.email);
        txtAddress.setText(MainActivity.address);
        txtBirth.setText(MainActivity.birth);

        txtNameUser.setText(MainActivity.name);
        txtEmailUser.setText(MainActivity.email);

    }


}