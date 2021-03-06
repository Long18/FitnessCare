package com.william.Fitness;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.william.Fitness.Database.CalendarDB;
import com.william.Fitness.Model.Exercise;
import com.william.Fitness.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Training extends Fragment {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountDown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id = 0, limit_time = 0;

    List<Exercise> list = new ArrayList<>();

    CalendarDB calendarDB;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily__training, container, false);
        initData();
        calendarDB = new CalendarDB(getActivity());


        btnStart = view.findViewById(R.id.btnStart);

        ex_image = view.findViewById(R.id.detail_image);

        txtCountDown = view.findViewById(R.id.txtCountdown);
        txtGetReady = view.findViewById(R.id.txtGetReady);
        txtTimer = view.findViewById(R.id.timer);
        ex_name = view.findViewById(R.id.titlename);

        layoutGetReady = view.findViewById(R.id.layout_get_ready);

        progressBar = (MaterialProgressBar) view.findViewById(R.id.progressBar);

        //Set data
        progressBar.setMax(list.size());
        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().toString().toLowerCase().equals("start")) {
                    showGetReady();
                    btnStart.setText("done");

                } else if (btnStart.getText().toString().toLowerCase().equals("done")) {

                    if (calendarDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if (calendarDB.getSettingMode() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if (calendarDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if (ex_id < list.size()) {
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    } else showFinished();

                } else if (calendarDB.getSettingMode() == 0)
                    exercisesEasyModeCountDown.cancel();
                else if (calendarDB.getSettingMode() == 1)
                    exercisesMediumModeCountDown.cancel();
                else if (calendarDB.getSettingMode() == 2)
                    exercisesHardModeCountDown.cancel();

                restTimeCountDown.cancel();

                if (ex_id < list.size())
                    setExerciseInformation(ex_id);
                else showFinished();
            }
        });
        return view;
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
        new CountDownTimer(500, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtCountDown.setText("" + (millisUntilFinished - 1000) / 1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if (ex_id < list.size()) {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if (calendarDB.getSettingMode() == 0)
                exercisesEasyModeCountDown.start();
            else if (calendarDB.getSettingMode() == 1)
                exercisesMediumModeCountDown.start();
            else if (calendarDB.getSettingMode() == 2)
                exercisesHardModeCountDown.start();

            //Set data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        } else
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

        calendarDB.saveDay("" + Calendar.getInstance().getTimeInMillis());

    }

    //Countdown
    CountDownTimer exercisesEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1) {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            } else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1) {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            } else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText("" + (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size() - 1) {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            } else {
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountDown.setText("" + millisUntilFinished / 1000);
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
        list.add(new Exercise(R.drawable.ex_hit_dat, "H??t ?????t"));
        list.add(new Exercise(R.drawable.ex_bung_red, "G???p b???ng"));
        list.add(new Exercise(R.drawable.ex_chay, "Ch???y"));
        list.add(new Exercise(R.drawable.ex_mong, "Squat"));
        list.add(new Exercise(R.drawable.ex_nguc_2, "T???p Ng???c"));


    }
}