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
import com.google.firebase.FirebaseApp;
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
    TextView btnLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference reference;

    ImageView btnBack, image;
    Button next;
    TextView title, login;

    TextInputLayout mFullName, mUsername, mEmail, mPassword;


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


        mFullName = findViewById(R.id.textInputName);
        mUsername = findViewById(R.id.textInputUserName);
        mEmail = findViewById(R.id.textInputEmail);
        mPassword = findViewById(R.id.textInputPassword);



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

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }


        String fullName = mFullName.getEditText().getText().toString().trim();
        String username = mUsername.getEditText().getText().toString().trim();
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();


        Intent intent = new Intent(getApplicationContext(), Register_part_two.class);

        intent.putExtra("fullName", fullName);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);

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
        String val = mFullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            mFullName.setError("Họ và tên không được để trống");
            return false;
        } else {
            mFullName.setError(null);
            mFullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = mUsername.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        //Giới hạn kí tự/ khoảng trắng từ 1-20

        if (val.isEmpty()) {
            mUsername.setError("Username không được để trống");
            return false;
        } else if (val.length() > 20) {
            mUsername.setError("Username quá dài");
            return false;
        }
        /*else if (val.matches(checkspaces)) {
            username.setError("Username không được có khoảng trắng");
            return false;
        }*/
        else {
            mUsername.setError(null);
            mUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = mEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        //Giới hạn kí tự dành cho email

        if (val.isEmpty()) {
            mEmail.setError("Email không được để trống");
            return false;
        }
        /*else if (val.matches(checkEmail)) {
            mEmail.setError("Email không được có khoảng trắng");
            return false;
        } */
        else {
            mEmail.setError(null);
            mEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = mPassword.getEditText().getText().toString().trim();
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
            mPassword.setError("Mật khẩu không được để trống");
            return false;
        }
        /*else if (!val.matches(checkPassword)) {
            mPassword.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        }*/
        else {
            mPassword.setError(null);
            mPassword.setErrorEnabled(false);
            return true;
        }
    }
}