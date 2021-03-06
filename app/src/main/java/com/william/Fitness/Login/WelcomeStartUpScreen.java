package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.william.Fitness.Database.CheckInternet;
import com.william.Fitness.Database.SessionManager;
import com.william.Fitness.MainActivity;
import com.william.Fitness.R;

public class WelcomeStartUpScreen extends AppCompatActivity {

    ImageView btnBack, image;
    Button next, btnLogout;
    TextView title, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_start_up_screen);

        btnBack = findViewById(R.id.btn_back_pressed);
        next = findViewById(R.id.btn_next);
        login = findViewById(R.id.Loginbtn);
        btnLogout = findViewById(R.id.btnLogout);
        image = findViewById(R.id.image_view);
        title = findViewById(R.id.title_res);

        SessionManager sessionManager = new SessionManager(WelcomeStartUpScreen.this, SessionManager.SESSION_USER);

        if (sessionManager.checkUserLogin()) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            //if user's not login, they can't see profile settings
            btnLogout.setVisibility(View.GONE);
        }

    }


    public void Logout(View view) {
        SessionManager sessionManager = new SessionManager(WelcomeStartUpScreen.this, SessionManager.SESSION_USER);
        FirebaseAuth.getInstance().signOut();
        sessionManager.checkUserLogout();
        Toast.makeText(WelcomeStartUpScreen.this, "????ng xu???t th??nh c??ng", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDialog() {
        final Dialog noInternet = new Dialog(this, R.style.df_dialog);
        noInternet.setContentView(R.layout.dialog_no_internet);
        noInternet.findViewById(R.id.btnOpenInternet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        noInternet.findViewById(R.id.btnCloseInternet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noInternet != null && noInternet.isShowing()) {
                    noInternet.dismiss();
                }
            }
        });
        noInternet.show();
    }

    public void callLoginScreen(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isNotConnected(this)) {
            showDialog();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login_wlc), "transition_login");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeStartUpScreen.this, pairs);
        startActivity(intent, options.toBundle());
        finish();

    }


    public void callSigupScreen(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isNotConnected(this)) {
            showDialog();
            return;
        }


        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
        finish();
    }
}