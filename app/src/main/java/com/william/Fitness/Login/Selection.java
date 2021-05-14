package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
    TextView  desc;
    RelativeLayout phone,mail;
    Button btnPhone, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        //Hooks
        phone = findViewById(R.id.relativeLayout_phone);
        mail = findViewById(R.id.relativeLayout_mail);
        desc = findViewById(R.id.desc_selection);
        btnPhone = findViewById(R.id.btn_colection_phone);
        btnEmail = findViewById(R.id.btn_colection_email);

        animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);

        mail.setAnimation(animation);
        phone.setAnimation(animation);
        desc.setAnimation(animation);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneSelection();
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    public void phoneSelection(){
        startActivity(new Intent(Selection.this, ForgetPassword.class));
    }

    public void showDialog() {
        final Dialog noFunction = new Dialog(Selection.this, R.style.df_dialog);
        noFunction.setContentView(R.layout.dialog_no_function);
        noFunction.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noFunction != null && noFunction.isShowing()) {
                    noFunction.dismiss();
                }
            }
        });
        noFunction.show();


    }
}