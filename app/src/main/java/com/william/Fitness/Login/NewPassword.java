package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.william.Fitness.R;

public class NewPassword extends AppCompatActivity {
    ImageView imageView;
    TextView title, decs;
    TextInputLayout newPass, rePass;
    Button btnNext;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        FirebaseApp.initializeApp(this);

        //Hooks
        imageView = findViewById(R.id.picture_new_pass);
        title = findViewById(R.id.title_new_pass);
        decs = findViewById(R.id.desc_new_pass);
        newPass = findViewById(R.id.new_pass);
        rePass = findViewById(R.id.re_pass);
        btnNext = findViewById(R.id.btn_next);

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        imageView.setAnimation(animation);
        title.setAnimation(animation);
        decs.setAnimation(animation);
        newPass.setAnimation(animation);
        rePass.setAnimation(animation);
        btnNext.setAnimation(animation);

    }

    public void btnSetPassword(View view) {
        if (!validatePassword()){
            return;
        }

        String newPassword = newPass.getEditText().getText().toString().trim();
        String phoneNumber = getIntent().getStringExtra("phoneNo");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNumber).child("password").setValue(newPassword);

        startActivity(new Intent(getApplicationContext(),ForgetSuccess.class));
        finish();

    }

    private boolean validatePassword() {
        String val = newPass.getEditText().getText().toString().trim();
        String val1 = rePass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //Phải có ít nhất 1 số
                //"(?=.*[a-z])" +         //Phải có ít nhất 1 từ viết thường
                //"(?=.*[A-Z])" +         //Phải có ít nhất 1 từ viết hoa
                "(?=.*[a-zA-Z])" +      //Tất cả các từ
                //"(?=.*[@#$%^&+=])" +    //Phải có ít nhất 1 kí tự
                "(?=S+$)" +             //Không được có khoảng trắng
                ".{4,}" +               //Phải có ít nhất 6 kí tự
                "$";

        if (val.isEmpty()) {
            newPass.setError("Mật khẩu không được để trống");
            rePass.setError("Mật khẩu không được để trống");
            return false;
        }
        /*else if (!val.matches(checkPassword)) {
            mPassword.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        }*/
        else {
            newPass.setError(null);
            rePass.setError(null);
            newPass.setErrorEnabled(false);
            rePass.setErrorEnabled(false);
            return true;
        }
    }

    public void backActivity(View view) {
        onBackPressed();
    }
}