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
    TextView decs;
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
        decs = findViewById(R.id.desc_new_pass);
        newPass = findViewById(R.id.new_pass);
        rePass = findViewById(R.id.re_pass);
        btnNext = findViewById(R.id.btn_next);

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        imageView.setAnimation(animation);
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
            newPass.setError("M???t kh???u kh??ng ???????c ????? tr???ng!");
            return false;
        }/* else if (val1 != val) {
            rePass.setError("Nh???p l???i m???t kh???u ph???i tr??ng v???i m???t kh???u m???i!");
            return false;
        }*/ else if (!val.matches(MIN_CHAR)) {
            newPass.setError("M???t kh???u ph???i c?? ??t nh???t 6 k?? t???!");
            return false;
        } else if (!val.matches(ONE_DIGIT)) {
            newPass.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? s???!");
            return false;
        } else if (!val.matches(ONE_LOWER_CASE)) {
            newPass.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? th?????ng!");
            return false;
        } else if (!val.matches(ONE_UPPER_CASE)) {
            newPass.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? vi???t hoa!");
            return false;
        } else if (!val.matches(ONE_SPECIAL_CHAR)) {
            newPass.setError("M???t kh???u ph???i c?? ??t nh???t 1 k?? t??? ?????c bi???t!");
            return false;
        } else if (!val.matches(NO_SPACE)) {
            newPass.setError("M???t kh???u kh??ng ???????c ????? kho???ng c??ch!");
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