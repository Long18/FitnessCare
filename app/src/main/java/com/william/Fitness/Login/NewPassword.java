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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.william.Fitness.R;

public class NewPassword extends AppCompatActivity {
    ImageView imageView;
    TextView title, decs;
    TextInputLayout newPass, rePass;
    Button btnNext;
    Animation animation;

    final String ONE_DIGIT = "^(?=.*[0-9]).{6,}$";
    final String ONE_LOWER_CASE = "^(?=.*[a-z]).{6,}$";
    final String ONE_UPPER_CASE = "^(?=.*[A-Z]).{6,}$";
    final String ONE_SPECIAL_CHAR = "^(?=.*[@#$%^&+=]).{6,}$";
    final String NO_SPACE = "^(?=\\S+$).{6,}$";
    final String MIN_CHAR = "^[a-zA-Z0-9._-].{5,}$";
    final String EMAIL_VALIDATE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        FirebaseAuth.getInstance().signOut();
    }

    public void btnSetPassword(View view) {
        if (!validatePassword()) {
            return;
        }

        String newPassword = newPass.getEditText().getText().toString().trim();
        String phoneNumber = getIntent().getStringExtra("phoneNo");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNumber).child("password").setValue(newPassword);

        startActivity(new Intent(getApplicationContext(), ForgetSuccess.class));
        finish();

    }

    private boolean validatePassword() {
        String val = newPass.getEditText().getText().toString().trim();
       // String val1 = rePass.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            newPass.setError("Mật khẩu không được để trống!");
            return false;
        }/* else if (val1 != val) {
            rePass.setError("Nhập lại mật khẩu phải trùng với mật khẩu mới!");
            return false;
        }*/ else if (!val.matches(MIN_CHAR)) {
            newPass.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        } else if (!val.matches(ONE_DIGIT)) {
            newPass.setError("Mật khẩu phải có ít nhất 1 chữ số!");
            return false;
        } else if (!val.matches(ONE_LOWER_CASE)) {
            newPass.setError("Mật khẩu phải có ít nhất 1 chữ thường!");
            return false;
        } else if (!val.matches(ONE_UPPER_CASE)) {
            newPass.setError("Mật khẩu phải có ít nhất 1 chữ viết hoa!");
            return false;
        } else if (!val.matches(ONE_SPECIAL_CHAR)) {
            newPass.setError("Mật khẩu phải có ít nhất 1 kí tự đặc biệt!");
            return false;
        } else if (!val.matches(NO_SPACE)) {
            newPass.setError("Mật khẩu không được để khoảng cách!");
            return false;
        } else {
            newPass.setError(null);
           // rePass.setError(null);
            newPass.setErrorEnabled(false);
           // rePass.setErrorEnabled(false);
            return true;
        }


    }

    public void backActivity(View view) {
        onBackPressed();
    }
}