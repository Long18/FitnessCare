package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.william.Fitness.MainActivity;
import com.william.Fitness.Model.User;
import com.william.Fitness.R;

import java.util.concurrent.TimeUnit;


public class Verify_OTP extends AppCompatActivity {
    PinView pinView;
    String fullName, phoneNo, email, username, password, date, gender, ToDO;
    TextView otpDescriptionText;
    Button btnSendCode;
    String codeSystem;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        //Hooks
        pinView = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_desc);

        //btnSendCode = findViewById(R.id.btnSendCode);

        FirebaseApp.initializeApp(this);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");


        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        ToDO = getIntent().getStringExtra("ToDO");

        otpDescriptionText.setText("Nhập mã số bạn vừa nhận được qua số điện thoại: " + phoneNo);

        inputUser();
        //sendCode(phoneNo);


    }


    public void inputUser(){

        User addNewUser = new User(fullName, username, email, phoneNo, password, date, gender);

        reference.child(phoneNo).setValue(addNewUser);
    }


    private void sendCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeSystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Verify_OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    public void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (ToDO.equals("updateData")){
                                updateUser();
                            }else {

                                inputUser();
                            }

                            Toast.makeText(Verify_OTP.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Verify_OTP.this, "Không thể xác thực, hãy thử lại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateUser() {
        Intent intent = new Intent(getApplicationContext(),NewPassword.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
        finish();
    }

    public void callNextScreen(View view) {

        /*String code = pinView.getText().toString();
        if (code.isEmpty()) {
            verifyCode(code);
        }*/

        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);
    }

    public void backMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}