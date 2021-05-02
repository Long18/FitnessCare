package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import com.william.Fitness.Model.User;
import com.william.Fitness.R;

import java.util.concurrent.TimeUnit;


public class Verify_OTP extends AppCompatActivity {
    PinView pinView;
    String codeSystem;
    String fullName, phoneNo, email, username, password, date, gender, ToDO;
    TextView otpDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        //Hooks
        pinView = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_desc);


        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        ToDO = getIntent().getStringExtra("ToDO");

        otpDescriptionText.setText("Nhập mã số bạn vừa nhận được qua số điện thoại: " + phoneNo);

        //sendCode(phoneNo);
        storeNewUserDate();
    }


    private void sendCode(String phoneNumber) {
        FirebaseApp.initializeApp(Verify_OTP.this);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60,TimeUnit.SECONDS,Verify_OTP.this,mCallbacks);
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
                        pinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Verify_OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        /*PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSystem, code);
        signInWithPhoneAuthCredential(credential);*/

        Toast.makeText(Verify_OTP.this,fullName + "\n"+
                email +"\n"+
                username +"\n"+
                password +"\n"+
                date +"\n"+
                gender +"\n"+
                phoneNo +"\n"+
                ToDO , Toast.LENGTH_SHORT).show();

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseApp.initializeApp(Verify_OTP.this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            storeNewUserDate();
                            Toast.makeText(Verify_OTP.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Verify_OTP.this, "Không thể xác thực, hãy thử lại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUserDate() {

        FirebaseApp.initializeApp(Verify_OTP.this);
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        User addNewUser = new User(fullName, username, email, phoneNo, password, date, gender);

        reference.child(phoneNo).setValue(addNewUser);


    }


    public void callNextScreen(View view) {
        String code = pinView.getText().toString();
        if (code.isEmpty()) {
            verifyCode(code);
        }
    }
}