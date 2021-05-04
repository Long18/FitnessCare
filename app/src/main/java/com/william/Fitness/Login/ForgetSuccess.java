package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.william.Fitness.R;

public class ForgetSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_success);
    }

    public void LoginScreen(View view){
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}