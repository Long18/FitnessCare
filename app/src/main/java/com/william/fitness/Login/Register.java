package com.william.fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.william.fitness.MainActivity;
import com.william.fitness.Model.User;
import com.william.fitness.R;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone, mAddress,mBirth;
    Button btnRegister;
    TextView btnLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        mFullName = findViewById(R.id.txtName);
        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
        mPhone = findViewById(R.id.txtPhone);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.Loginbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fName = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String address = "";
                String birthday = "";

                //Condition register
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email không được để trống!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Mật khẩu không được để trống!");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Mật khẩu phải trên 6 ký tự.");
                }
                if (mPhone.length() < 10) {
                    mPassword.setError("Số điện thoại phải trên 10 số.");
                }


                //Condition firebase register
               /* fAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,
                                            "Đăng ký thành công.",Toast.LENGTH_SHORT).show();

                                    //Add user to database
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("Users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fName", mFullName);
                                    user.put("phone", phone);
                                    user.put("email", email);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Add user Success, ID user is: " + userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Error!!" +
                                            task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference().child("Account");
                //get all the values
                String key = reference.push().getKey();


                com.william.fitness.Model.User user = new User(fName, phone, email, password,address,birthday);
                reference.child(email).setValue(user);

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom,R.anim.bottom_to_top);
        finish();
    }
}