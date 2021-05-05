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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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
import com.william.Fitness.Database.SessionManager;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNum, password;
    RelativeLayout progressbar;
    Button btnLogin;
    CheckBox checkBox;
    TextInputEditText phone, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        countryCodePicker = findViewById(R.id.phone_numer_log);
        phoneNum = findViewById(R.id.textInputPhone);
        password = findViewById(R.id.textInputPassword);
        checkBox = findViewById(R.id.ckb_remeber);
        phone = findViewById(R.id.txtPhone);
        pass = findViewById(R.id.txtPassword);

        FirebaseApp.initializeApp(this);

        //Remember button on Click
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.KEY_REMEMBER_ME);
        if (sessionManager.checkRemember()){
            HashMap<String,String> rememberMe = sessionManager.rememberMeClick();
            phone.setText(rememberMe.get(SessionManager.REMEMBER_PHONENUMBER));
            pass.setText(rememberMe.get(SessionManager.REMEMBER_PASSWORD));
        }



    }

    public void userLogin(View view) {


        if (!validateFields()) {
            return;
        }

        //get data
        String mPhoneNumber = phoneNum.getEditText().getText().toString().trim();
        String mPassword = password.getEditText().getText().toString().trim();

        if (mPhoneNumber.charAt(0) == '0') {
            mPhoneNumber = mPhoneNumber.substring(1);
        }

        String getPhoneNumber = "+" + countryCodePicker.getFullNumber() + mPhoneNumber;

        if (checkBox.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.KEY_REMEMBER_ME);
            sessionManager.createRememberMeSession(mPhoneNumber,mPassword);
        }

        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(getPhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNum.setError(null);
                    phoneNum.setErrorEnabled(false);

                    String systemPassword = snapshot.child(getPhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(mPassword)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        //Get Information User in Database
                        String mFullName = snapshot.child(getPhoneNumber).child("fullName").getValue(String.class);
                        String mUserName = snapshot.child(getPhoneNumber).child("username").getValue(String.class);
                        String mEmail = snapshot.child(getPhoneNumber).child("email").getValue(String.class);
                        String mPhoneNo = snapshot.child(getPhoneNumber).child("phoneNo").getValue(String.class);
                        String mPassword = snapshot.child(getPhoneNumber).child("password").getValue(String.class);
                        String mDate = snapshot.child(getPhoneNumber).child("date").getValue(String.class);
                        String mGender = snapshot.child(getPhoneNumber).child("gender").getValue(String.class);

                        //Create Database Store
                        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USER);
                        sessionManager.createLoginSession(mFullName, mUserName, mEmail, mPhoneNo, mPassword, mDate, mGender);


                        startActivity(new Intent(Login.this, MainActivity.class));
                        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);

                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(Login.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
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

        if (phoneNo.isEmpty()) {
            phoneNum.setError("Số điện thoại không được để trống");
            phoneNum.requestFocus();
            return false;
        } else if (pass.isEmpty()) {
            password.setError("Mật khẩu không được để trống");
            password.requestFocus();
            return false;
        } else {
            phoneNum.setError(null);
            phoneNum.setErrorEnabled(false);
            return true;
        }
    }


    public void Register(View View) {
        startActivity(new Intent(Login.this, Register.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.stay);
    }

    public void ResetPass(View view) {
        startActivity(new Intent(Login.this, Selection.class));
    }


}