package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

public class ForgetPassword extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNum;
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Hooks
        countryCodePicker = findViewById(R.id.phone_numer_forget);
        phoneNum = findViewById(R.id.textInputPhoneForget);
        btnNext = findViewById(R.id.btnFoget_password_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgetNext();

            }
        });

    }

    public void forgetNext(){
        if (!validateFields()){
            return;
        }

        //get data
        String mPhoneNumber = phoneNum.getEditText().getText().toString().trim();

        if (mPhoneNumber.charAt(0) == '0'){
            mPhoneNumber = mPhoneNumber.substring(1);
        }

        String getPhoneNumber = "+" + countryCodePicker.getFullNumber() + mPhoneNumber;


        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(getPhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNum.setError(null);
                    phoneNum.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(),Verify_OTP.class);
                    intent.putExtra("phoneNo",getPhoneNumber);
                    intent.putExtra("ToDO","updateData");
                    startActivity(intent);
                    finish();
                }else {
                    phoneNum.setError("Tài khoản không tồn tại");
                    phoneNum.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields() {

        String phoneNo = phoneNum.getEditText().getText().toString().trim();

        if (phoneNo.isEmpty()){
            phoneNum.setError("Số điện thoại không được để trống");
            phoneNum.requestFocus();
            return false;
        }else{
            phoneNum.setError(null);
            phoneNum.setErrorEnabled(false);
            return true;
        }
    }
}