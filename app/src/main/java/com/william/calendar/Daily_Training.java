package com.william.calendar;

import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.william.calendar.Database.CalendarDB;
import com.william.calendar.Model.Exercise;
import com.william.calendar.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountDown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id = 0, limit_time = 0;

    List<Exercise> list = new ArrayList<>();

    CalendarDB calendarDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);
        
        initData();

        calendarDB = new CalendarDB(this);


        
        btnStart = (Button)findViewById(R.id.btnStart);

        ex_image = (ImageView)findViewById(R.id.detail_image);

        txtCountDown = (TextView)findViewById(R.id.txtCountdown);
        txtGetReady = (TextView)findViewById(R.id.txtGetReady);
        txtTimer = (TextView)findViewById(R.id.timer);
        ex_name = (TextView)findViewById(R.id.titlename);

        layoutGetReady = (LinearLayout)findViewById(R.id.layout_get_ready);

        progressBar = (MaterialProgressBar)findViewById(R.id.progressBar);

        //Set data
        progressBar.setMax(list.size());
        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().toString().toLowerCase().equals("start")){
                    showGetReady();
                    btnStart.setText("done");

                }else if(btnStart.getText().toString().toLowerCase().equals("done")){

                    if(calendarDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if(calendarDB.getSettingMode() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if(calendarDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();


                    restTimeCountDown.cancel();


                    if (ex_id < list.size()){
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }else showFinished();

                }else
                    if(calendarDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if(calendarDB.getSettingMode() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if(calendarDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if (ex_id < list.size())
                        setExerciseInformation(ex_id);
                    else showFinished();
            }
        });

    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountDown.start();

        txtGetReady.setText("REST TIME");
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                txtCountDown.setText(""+(millisUntilFinished-1000)/1000 );
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if (ex_id < list.size()){
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if(calendarDB.getSettingMode() == 0)
            exercisesEasyModeCountDown.start();
            else if(calendarDB.getSettingMode() == 1)
            exercisesMediumModeCountDown.start();
            else if(calendarDB.getSettingMode() == 2)
            exercisesHardModeCountDown.start();

            //Set data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        }
        else
            showFinished();
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISHED!!");
        txtCountDown.setText("Congratulation!! \n You're done today.");
        txtCountDown.setTextSize(20);

        calendarDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());

    }

    //Countdown
    CountDownTimer exercisesEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" +(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" +(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" +(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else {
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountDown.setText("" +millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
           setExerciseInformation(ex_id);
           showExercises();
        }
    };

    private void setExerciseInformation(int id) {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);


    }


    private void initData() {
        list.add(new Exercise(R.drawable.low_lunge_pose, "Low Lunge Pose"));
        list.add(new Exercise(R.drawable.king_pigeon_pose, "King pigeon Pose"));
        list.add(new Exercise(R.drawable.facing_dog_pose, "Facing Dog Pose"));
        list.add(new Exercise(R.drawable.cobra_pose, "Cobra Pose"));
        list.add(new Exercise(R.drawable.camel_pose, "Camel Pose"));


    }
}