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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

import java.util.Calendar;

public class Register_part_two extends AppCompatActivity {
    ImageView btnBack;
    Button next;
    TextView titleText, slideText, login;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_two);

        //Hooks
        next = findViewById(R.id.btnNext);
        login = findViewById(R.id.Loginbtn);
        titleText = findViewById(R.id.title_res);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);


        mAuth = FirebaseAuth.getInstance();

        createRequest();

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
                            Toast.makeText(Register_part_two.this, "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register_part_two.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signIn(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void callScreenThree(View view) {

        if (!validateGender() | !validateAge()){
            return;
        }

        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day+"/"+month+"/"+year;

        Intent intent = new Intent(getApplicationContext(), Register_part_three.class);

        intent.putExtra("fullName", fullName);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("date", date);
        intent.putExtra("gender", gender);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btnNext), "transition_next_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.image_view), "transition_image");
        pairs[2] = new Pair<View, String>(findViewById(R.id.title_res), "transition_res_title");
        pairs[3] = new Pair<View, String>(findViewById(R.id.Loginbtn), "transition_login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register_part_two.this, pairs);
        startActivity(intent, options.toBundle());


        /*startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);*/
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom, R.anim.bottom_to_top);
        finish();
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Hãy chọn giới tính của bạn", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "Bạn phải trên 14 tuổi mới có thể tham gia vào chương trình", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }



    public void backActivity2(View view) {
        onBackPressed();
    }
}