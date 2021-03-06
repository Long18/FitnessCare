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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.william.Fitness.MainActivity;
import com.william.Fitness.Model.User;
import com.william.Fitness.R;

public class Register extends AppCompatActivity {
    ImageView btnBack, image;
    Button next;
    TextView title, login;

    TextInputLayout mFullName, mUsername, mEmail, mPassword;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    final String ONE_DIGIT = "^(?=.*[0-9]).{6,}$";
    final String ONE_LOWER_CASE = "^(?=.*[a-z]).{6,}$";
    final String ONE_UPPER_CASE = "^(?=.*[A-Z]).{6,}$";
    final String ONE_SPECIAL_CHAR = "^(?=.*[@#$%^&+=]).{6,}$";
    final String NO_SPACE = "^(?=\\S+$).{6,}$";
    final String MIN_CHAR = "^[a-zA-Z0-9._-].{5,}$";
    final String EMAIL_VALIDATE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
        mAuth = FirebaseAuth.getInstance();


        mFullName = findViewById(R.id.textInputName);
        mUsername = findViewById(R.id.textInputUserName);
        mEmail = findViewById(R.id.textInputEmail);
        mPassword = findViewById(R.id.textInputPassword);


        createRequest();

    }


    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btnNext), "transition_next_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.image_view), "transition_image");
        pairs[2] = new Pair<View, String>(findViewById(R.id.title_res), "transition_res_title");
        pairs[3] = new Pair<View, String>(findViewById(R.id.Loginbtn), "transition_login_btn");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
        startActivity(intent, options.toBundle());



        /*startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Register.this, "????ng nh???p th??nh c??ng!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "????ng nh???p th???t b???i!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateFullName() {
        String val = mFullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            mFullName.setError("H??? v?? t??n kh??ng ???????c ????? tr???ng");
            return false;
        } else {
            mFullName.setError(null);
            mFullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = mUsername.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            mUsername.setError("Username kh??ng ???????c ????? tr???ng");
            return false;
        }
        if (val.length() > 20) {
            mUsername.setError("Username qu?? d??i");
            return false;

        }
        if (val.length() <= 2) {
            mUsername.setError("Username qu?? ng???n");
            return false;
        }
        if (!val.matches(NO_SPACE)) {
            mUsername.setError("Username kh??ng ???????c c?? kho???ng tr???ng!");
            return false;
        } else {
            mUsername.setError(null);
            mUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = mEmail.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            mEmail.setError("Email kh??ng ???????c ????? tr???ng!");
            return false;
        } else if (!val.matches(EMAIL_VALIDATE)) {
            mEmail.setError("H??y vi???t email ????ng ?????nh d???ng!");
            return false;
        } else {
            mEmail.setError(null);
            mEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = mPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +                                 //Ph???i c?? ??t nh???t 1 s???
                "(?=.*[a-z])" +                                 //Ph???i c?? ??t nh???t 1 t??? vi???t th?????ng
                "(?=.*[A-Z])" +                                 //Ph???i c?? ??t nh???t 1 t??? vi???t hoa
                "(?=.*[a-zA-Z])" +                              //T???t c??? c??c t???
                "(?=.*[@#$%^&+=])" +                            //Ph???i c?? ??t nh???t 1 k?? t??? ?????c bi???t
                //"(?=S+$)" +                                    //Kh??ng ???????c c?? kho???ng tr???ng
                ".{6,}" +                                      //Ph???i c?? ??t nh???t 6 k?? t???
                "$";


        if (val.isEmpty()) {
            mPassword.setError("M???t kh???u kh??ng ???????c ????? tr???ng!");
            return false;
        } else if (!val.matches(MIN_CHAR)) {
            mPassword.setError("M???t kh???u ph???i c?? ??t nh???t 6 k?? t???!");
            return false;
        } else if (!val.matches(ONE_DIGIT)) {
            mPassword.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? s???!");
            return false;
        } else if (!val.matches(ONE_LOWER_CASE)) {
            mPassword.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? th?????ng!");
            return false;
        } else if (!val.matches(ONE_UPPER_CASE)) {
            mPassword.setError("M???t kh???u ph???i c?? ??t nh???t 1 ch??? vi???t hoa!");
            return false;
        } else if (!val.matches(ONE_SPECIAL_CHAR)) {
            mPassword.setError("M???t kh???u ph???i c?? ??t nh???t 1 k?? t??? ?????c bi???t!");
            return false;
        } else if (!val.matches(NO_SPACE)) {
            mPassword.setError("M???t kh???u kh??ng ???????c ????? kho???ng c??ch!");
            return false;
        } else {
            mPassword.setError(null);
            mPassword.setErrorEnabled(false);
            return true;
        }


    }

    public void signIn(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}