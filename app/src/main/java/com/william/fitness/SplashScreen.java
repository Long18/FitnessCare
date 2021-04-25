package com.william.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.fitness.Helper.Tutorial;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;

    ImageView bgImage;
    TextView powered_line;

    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Hooks
        bgImage = findViewById(R.id.background_image);
        powered_line = findViewById(R.id.powered_line);

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);

        //Set Animations to objects
        bgImage.setAnimation(sideAnim);
        powered_line.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIME);
    }
}