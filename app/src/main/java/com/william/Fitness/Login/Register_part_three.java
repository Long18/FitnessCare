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
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.william.Fitness.R;

public class Register_part_three extends AppCompatActivity {
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_three);

        //Hooks
        phoneNumber = findViewById(R.id.textInputMobile);
        countryCodePicker = findViewById(R.id.phone_numer);

    }


    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);
        finish();
    }

    public void btnSignUp(View view) {
        if (!validatePhoneNumber()) {
            return;
        }

        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String date = getIntent().getStringExtra("date");
        String gender = getIntent().getStringExtra("gender");

        String getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();// Get Phone Num

        if (getUserPhoneNumber.charAt(0) == '0') {
            getUserPhoneNumber = getUserPhoneNumber.substring(1);
        }

        final String phoneNo = "+" + countryCodePicker.getFullNumber() + getUserPhoneNumber;


        Intent intent = new Intent(getApplicationContext(), Verify_OTP.class);

        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("date", date);
        intent.putExtra("gender", gender);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("ToDO", "createNewUser");

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Số điện thoại không được để trống!");
            return false;
        }
        /*else if (!val.matches(checkspaces)) {
            phoneNumber.setError("Số điện thoại không được phép có khoảng trắng!");
            return false;
        }*/
        else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}