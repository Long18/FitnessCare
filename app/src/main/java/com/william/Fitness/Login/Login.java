package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNum, password;
    RelativeLayout progressbar;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        countryCodePicker = findViewById(R.id.phone_numer_log);
        phoneNum = findViewById(R.id.textInputPhone);
        password = findViewById(R.id.textInputPassword);
        btnLogin = findViewById(R.id.btnLogin);

        FirebaseApp.initializeApp(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    public void userLogin(){


        if(!validateFields()){
            return;
        }

        //get data
        String mPhoneNumber = phoneNum.getEditText().getText().toString().trim();
        String mPassword = password.getEditText().getText().toString().trim();

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

                    String systemPassword = snapshot.child(getPhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(mPassword)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        startActivity(new Intent(Login.this, MainActivity.class));
                        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);

                        Toast.makeText(Login.this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(Login.this,"Sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this,"Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateFields() {

        String phoneNo = phoneNum.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (phoneNo.isEmpty()){
            phoneNum.setError("Số điện thoại không được để trống");
            phoneNum.requestFocus();
            return false;
        }else if (pass.isEmpty()){
            password.setError("Mật khẩu không được để trống");
            password.requestFocus();
            return false;
        }else{
            phoneNum.setError(null);
            phoneNum.setErrorEnabled(false);
            return true;
        }
    }


    public void Register(View View){
        startActivity(new Intent(Login.this, Register.class));
        overridePendingTransition(R.anim.top_to_bottom,R.anim.stay);
    }

    public void ResetPass(View view){
        startActivity(new Intent(Login.this, Selection.class));
    }


   
}