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

import com.google.android.material.textfield.TextInputLayout;
import com.william.Fitness.R;

public class Register_part_three extends AppCompatActivity {
    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_three);

        //Hooks
        phoneNumber = findViewById(R.id.textInputMobile);

    }


    public void onLoginClick(View view){
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom,R.anim.bottom_to_top);
        finish();
    }

    public void btnSignUp(){
        if(!validatePhoneNumber()){
            return;
        }


    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Số điện thoại không được để trống!");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("Số điện thoại không được phép có khoảng trắng!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}