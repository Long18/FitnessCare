package com.william.Fitness.Login;

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

import com.william.Fitness.R;

import java.util.Calendar;

public class Register_part_two extends AppCompatActivity {
    ImageView btnBack;
    Button next;
    TextView titleText, slideText, login;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_two);

        //Hooks
        btnBack = findViewById(R.id.btn_arrow_back_register);
        next = findViewById(R.id.btnNext);
        login = findViewById(R.id.Loginbtn);
        titleText = findViewById(R.id.title_res);
        //slideText = findViewById(R.id.slider_desc);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);



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

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btnNext), "transition_next_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.image_view), "transition_image");
        pairs[2] = new Pair<View, String>(findViewById(R.id.title_res), "transition_res_title");
        pairs[3] = new Pair<View, String>(findViewById(R.id.btn_arrow_back_register), "transition_back_btn");
        pairs[4] = new Pair<View, String>(findViewById(R.id.Loginbtn), "transition_login_btn");

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
}