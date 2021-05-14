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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.william.Fitness.Database.SessionManager;
import com.william.Fitness.MainActivity;
import com.william.Fitness.ProfileActivity;
import com.william.Fitness.R;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNum, password;
    ImageView btnGoogle;
    LoginButton btnFacebook;
    RelativeLayout progressbar;
    Button btnLogin;
    CheckBox checkBox;
    TextInputEditText phone, pass;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;


    //Not allow user access when they login
    // This class still have bug, when closing app, the data will disappear
   /* @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        mAuth.addAuthStateListener(authStateListener);
    }*/

    @Override
    protected void onStop() {
        super.onStop();

        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        btnGoogle = findViewById(R.id.google_login);
        btnFacebook = findViewById(R.id.btnFacebook);
        countryCodePicker = findViewById(R.id.phone_numer_log);
        phoneNum = findViewById(R.id.textInputPhone);
        password = findViewById(R.id.textInputPassword);
        checkBox = findViewById(R.id.ckb_remeber);
        phone = findViewById(R.id.txtPhone);
        pass = findViewById(R.id.txtPassword);

        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebook.setReadPermissions("email","public_profile");
        btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_SHORT).show();
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };


        FirebaseApp.initializeApp(this);
        createRequest();

        //Remember button on Click
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.KEY_REMEMBER_ME);
        if (sessionManager.checkRemember()) {
            HashMap<String, String> rememberMe = sessionManager.rememberMeClick();
            phone.setText(rememberMe.get(SessionManager.REMEMBER_PHONENUMBER));
            pass.setText(rememberMe.get(SessionManager.REMEMBER_PASSWORD));
        }

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    /*
    Login Authentication
            With Facebook Account
    */

    private void handleFacebookToken(AccessToken accessToken) {

        Toast.makeText(Login.this, "Token: " + accessToken, Toast.LENGTH_SHORT).show();

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Toast.makeText(Login.this, "Login Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        mCallbackManager.onActivityResult(resultCode, resultCode, data);
        super.onActivityReenter(resultCode, data);
    }


    /*
    Login Authentication
            With Google Account
    */

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
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
                            Toast.makeText(Login.this, "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

   /*
    Login Normal
            With Phone Number
    */

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
            sessionManager.createRememberMeSession(mPhoneNumber, mPassword);
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
                        String mUserName = snapshot.child(getPhoneNumber).child("userName").getValue(String.class);
                        String mEmail = snapshot.child(getPhoneNumber).child("email").getValue(String.class);
                        String mPhoneNo = snapshot.child(getPhoneNumber).child("phoneNo").getValue(String.class);
                        String mPassword = snapshot.child(getPhoneNumber).child("password").getValue(String.class);
                        String mDate = snapshot.child(getPhoneNumber).child("date").getValue(String.class);
                        String mGender = snapshot.child(getPhoneNumber).child("gender").getValue(String.class);

                        //Create Database Store
                        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USER);
                        sessionManager.createLoginSession(mFullName, mUserName, mEmail, mPhoneNo, mPassword, mDate, mGender);


                        startActivity(new Intent(Login.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

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