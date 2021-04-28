package com.william.Fitness.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.Fitness.R;

public class Register_part_two extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_part_two);

    }


    public void callScreenThree(View view){
        Intent intent = new Intent(getApplicationContext(),Register_part_three.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btnNext), "transition_next_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.image_view), "transition_image");
        pairs[2] = new Pair<View, String>(findViewById(R.id.title_res), "transition_res_title");
        pairs[3] = new Pair<View, String>(findViewById(R.id.btn_arrow_back_register), "transition_back_btn");
        pairs[4] = new Pair<View, String>(findViewById(R.id.Loginbtn), "transition_login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register_part_two.this,pairs);
        startActivity(intent,options.toBundle());


        /*startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);*/
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.top_to_bottom,R.anim.bottom_to_top);
        finish();
    }
}