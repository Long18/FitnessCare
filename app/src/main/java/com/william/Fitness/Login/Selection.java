package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.Fitness.R;

public class Selection extends AppCompatActivity {


    Animation animation;
    TextView title, desc;
    RelativeLayout phone,mail;
    Button btnPhone, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        //Hooks
        phone = findViewById(R.id.relativeLayout_phone);
        mail = findViewById(R.id.relativeLayout_mail);
        title = findViewById(R.id.title_selection);
        desc = findViewById(R.id.desc_selection);
        btnPhone = findViewById(R.id.btn_colection_phone);

        animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);

        mail.setAnimation(animation);
        phone.setAnimation(animation);
        title.setAnimation(animation);
        desc.setAnimation(animation);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneSelection();
            }
        });

    }

    public void phoneSelection(){
        startActivity(new Intent(Selection.this, Selection.class));
    }
}