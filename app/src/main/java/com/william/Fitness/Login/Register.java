package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.william.Fitness.MainActivity;
import com.william.Fitness.Model.User;
import com.william.Fitness.R;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone, mAddress, mBirth;
    Button btnRegister;
    TextView btnLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference reference;

    ImageView btnBack, image;
    Button next;
    TextView title, login;

    TextInputLayout fullName, username, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        //Hooks
        btnBack = findViewById(R.id.btn_back_pressed);
        next = findViewById(R.id.btn_next);
        login = findViewById(R.id.Loginbtn);
        image = findViewById(R.id.image_view);
        title = findViewById(R.id.title_res);

        mFullName = findViewById(R.id.txtName);
        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
        mPhone = findViewById(R.id.txtPhone);
        btnLogin = findViewById(R.id.Loginbtn);

        fullName = findViewById(R.id.textInputName);
        username = findViewById(R.id.textInputUserName);
        email = findViewById(R.id.textInputEmail);
        password = findViewById(R.id.textInputPassword);

        //fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();




        /*btnRegister.setOnClickListener(new View.OnClickListener() {
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
               *//* fAuth.createUserWithEmailAndPassword(email,password)
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
        });*//*


                reference = FirebaseDatabase.getInstance().getReference().child("Account");
                //get all the values

                User user = new User(email, fName, phone, password,address,birthday);
                reference.child(phone).setValue(user);


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
        });*/

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);
        finish();
    }

    public void callNextSignupScreen(View view) {

        if(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), Register_part_two.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btnNext), "transition_next_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.image_view), "transition_image");
        pairs[2] = new Pair<View, String>(findViewById(R.id.title_res), "transition_res_title");
        pairs[3] = new Pair<View, String>(findViewById(R.id.btn_arrow_back_register), "transition_back_btn");
        pairs[4] = new Pair<View, String>(findViewById(R.id.Loginbtn), "transition_login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
        startActivity(intent, options.toBundle());


        /*startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);*/
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Họ và tên không được để trống");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        //Giới hạn kí tự/ khoảng trắng từ 1-20

        if (val.isEmpty()) {
            username.setError("Username không được để trống");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username quá dài");
            return false;
        }
        /*else if (val.matches(checkspaces)) {
            username.setError("Username không được có khoảng trắng");
            return false;
        }*/
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        //Giới hạn kí tự dành cho email

        if (val.isEmpty()) {
            email.setError("Email không được để trống");
            return false;
        }
        /*else if (val.matches(checkEmail)) {
            email.setError("Email không được có khoảng trắng");
            return false;
        } */
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //Phải có ít nhất 1 số
                //"(?=.*[a-z])" +         //Phải có ít nhất 1 từ viết thường
                //"(?=.*[A-Z])" +         //Phải có ít nhất 1 từ viết hoa
                "(?=.*[a-zA-Z])" +      //Tất cả các từ
                //"(?=.*[@#$%^&+=])" +    //Phải có ít nhất 1 kí tự
                "(?=S+$)" +             //Không được có khoảng trắng
                ".{4,}" +               //Phải có ít nhất 6 kí tự
                "$";

        if (val.isEmpty()) {
            password.setError("Mật khẩu không được để trống");
            return false;
        }
        /*else if (!val.matches(checkPassword)) {
            password.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        }*/
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}