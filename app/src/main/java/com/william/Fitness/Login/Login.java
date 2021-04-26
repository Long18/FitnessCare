package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button btnLogin;
    FirebaseAuth fAuth;
    TextView resetPassword;
    ProgressBar progressBar;
//region Close Activity form ( Change to Fragment )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        resetPassword = findViewById(R.id.txtresetpass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //Condition register
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email không được để trống!");
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Mật khẩu không được để trống!");
                    return;
                }
                else if(password.length() < 6){
                    mPassword.setError("Mật khẩu phải trên 6 ký tự");
                }
                else {
                    isUser();
                }
                progressBar.setVisibility(View.VISIBLE);


                //Authenticate the account
//                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            isUser();
//                            Toast.makeText(Login.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
//                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            finish();
//                        }else{
//                            Toast.makeText(Login.this, "Đăng nhập thất bại!" +
//                             task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });

            }
        });
    }
//endregion


    public void Register(View View){
        startActivity(new Intent(Login.this, Register.class));
        overridePendingTransition(R.anim.top_to_bottom,R.anim.stay);
    }

    public void ResetPass(View view) {
        EditText resetPassword = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Lấy lại mật khẩu?");
        passwordResetDialog.setMessage("Nhập địa chỉ email để lấy lại mật khẩu");
        passwordResetDialog.setView(resetPassword);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = resetPassword.getText().toString();
                fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this,
                                "Đã gửi đường dẫn đặt lại mật khẩu tại email của bạn!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,
                                "Không gửi được link: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            isUser();
//            startActivity(new Intent(Login.this.getApplicationContext(), MainActivity.class));
//            finish();
//        }
    }

    private void isUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Account");
        String emailLogin = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        //reference.orderByChild("phone").orderByChild("email");

        Query checkUser = reference.orderByChild("phone").equalTo(emailLogin);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String passwordDB = dataSnapshot.child(emailLogin).child("password").getValue(String.class);
                    if(passwordDB.equals(password)) {
                        String nameDB = dataSnapshot.child(emailLogin).child("name").getValue(String.class);
                        String birthdayDB = dataSnapshot.child(emailLogin).child("birth").getValue(String.class);
                        String emailDB = dataSnapshot.child(emailLogin).child("email").getValue(String.class);
                        String phoneDB = dataSnapshot.child(emailLogin).child("phone").getValue(String.class);
                        String addressDB = dataSnapshot.child(emailLogin).child("address").getValue(String.class);

//                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//
//                        intent.putExtra("name",nameDB);
//                        intent.putExtra("birth",birthdayDB);
//                        intent.putExtra("email",emailDB);
//                        intent.putExtra("phone",phoneDB);
//                        intent.putExtra("address",addressDB);
//
//
//                        startActivity(intent);
                        MainActivity.name = nameDB;
                        MainActivity.birth = birthdayDB;
                        MainActivity.email = emailDB;
                        MainActivity.number = phoneDB;
                        MainActivity.address = addressDB;
                        Toast.makeText(Login.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        mPassword.setError("Wrong Password");
                        mPassword.requestFocus();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


   
}