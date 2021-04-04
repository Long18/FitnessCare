package com.william.calendar;


import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.william.calendar.Database.CalendarDB;
import com.william.calendar.Utils.Common;

public class ViewExercise extends AppCompatActivity {

    int image_id;
    String name;

    TextView timer,title;
    ImageView detai_image;

    Button btnStart;

    boolean isRunning = false;

    CalendarDB calendarDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        calendarDB = new CalendarDB(this);

        timer = (TextView)findViewById(R.id.timer);
        title = (TextView)findViewById(R.id.title);
        detai_image = (ImageView)findViewById(R.id.detail_image);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isRunning){
                    btnStart.setText("Done!");

                    int timeLimit=0;
                    if (calendarDB.getSettingMode() == 0)
                        timeLimit = Common.TIME_LIMIT_EASY;
                    if (calendarDB.getSettingMode() == 1)
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    if (calendarDB.getSettingMode() == 2)
                        timeLimit = Common.TIME_LIMIT_HARD;

                    new CountDownTimer(timeLimit, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer.setText(""+millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(ViewExercise.this, "Finish!!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                }else {
                    Toast.makeText(ViewExercise.this, "Finish!!!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                isRunning = !isRunning;
            }
        });

        timer.setText("");

        if(getIntent() != null){
            image_id = getIntent().getIntExtra("image_id",-1);
            name = getIntent().getStringExtra("name");

            detai_image.setImageResource(image_id);
            title.setText(name);
        }
    }
}
