package com.william.Fitness.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.william.Fitness.MainActivity;
import com.william.Fitness.Model.User;
import com.william.Fitness.R;

import java.util.concurrent.TimeUnit;

public class Register_part_three extends AppCompatActivity {
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;


    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_three);

        //Hooks
        phoneNumber = findViewById(R.id.textInputMobile);
        countryCodePicker = findViewById(R.id.phone_numer);

        mAuth = FirebaseAuth.getInstance();

        createRequest();

    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                            Toast.makeText(Register_part_three.this, "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register_part_three.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);
        finish();
    }

    public void btnSignUp(View view) {
        if (!validatePhoneNumber()) {
            return;
        }

        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String date = getIntent().getStringExtra("date");
        String gender = getIntent().getStringExtra("gender");

        String getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();// Get Phone Num

        if (getUserPhoneNumber.charAt(0) == '0') {
            getUserPhoneNumber = getUserPhoneNumber.substring(1);
        }

        final String phoneNo = "+" + countryCodePicker.getFullNumber() + getUserPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), Verify_OTP.class);

        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("date", date);
        intent.putExtra("gender", gender);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("ToDO", "createNewUser");

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }




    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Số điện thoại không được để trống!");
            return false;
        }
        /*else if (!val.matches(checkspaces)) {
            phoneNumber.setError("Số điện thoại không được phép có khoảng trắng!");
            return false;
        }*/
        else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void backActivity3(View view){
        onBackPressed();
    }

}