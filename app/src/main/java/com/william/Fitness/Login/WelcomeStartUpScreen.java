package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.william.Fitness.R;

public class WelcomeStartUpScreen extends AppCompatActivity {

    ImageView btnBack, image;
    Button next, btnInf;
    TextView title,login;


    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_start_up_screen);

        btnBack = findViewById(R.id.btn_back_pressed);
        next = findViewById(R.id.btn_next);
        login = findViewById(R.id.Loginbtn);
        image = findViewById(R.id.image_view);
        title = findViewById(R.id.title_res);




    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login_wlc), "transition_login");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeStartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void callSigupScreen(View view){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }
}