package com.william.fitness;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.william.fitness.Database.CalendarDB;

import java.util.Calendar;
import java.util.Date;

public class Settings extends AppCompatActivity {

    Button btnSave;
    RadioButton rdbEasy, rdbMedium,rdbHard;
    RadioGroup rdbGroup;
    CalendarDB calendarDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Init View
        btnSave = findViewById(R.id.btnSave);

        rdbGroup = findViewById(R.id.rdbGroup);
        rdbEasy = findViewById(R.id.rdbEasy);
        rdbMedium = findViewById(R.id.rdbMedium);
        rdbHard = findViewById(R.id.rdbHard);

        switchAlarm = findViewById(R.id.switchAlarm);

        timePicker = findViewById(R.id.timePicker);

        calendarDB = new CalendarDB(this);

        int mode = calendarDB.getSettingMode();
        setRadioButton(mode);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnvgbar);

        // Select menu on navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(Settings.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_Calendar:
                        Intent calendar = new Intent(Settings.this, com.william.fitness.Calendar.class);
                        startActivity(calendar);
                        break;
                    case R.id.ic_add:
                        Intent add = new Intent(Settings.this, Daily_Training.class);
                        startActivity(add);
                        break;
                    case R.id.ic_recent:
                        Intent recent = new Intent(Settings.this, ViewExercise.class);
                        startActivity(recent);
                        break;
                    case R.id.ic_User:
                        Intent user = new Intent(Settings.this, Settings.class);
                        startActivity(user);
                        break;
                }
                return false;
            }
        });



        //Event call
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWorkOutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(Settings.this, "Saved !!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveAlarm(boolean checked) {
        if (checked){
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(Settings.this,AlarmNotification.class);
            pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            //Set time
            Calendar calendar = Calendar.getInstance();
            Date toDay = Calendar.getInstance().getTime();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                calendar.set(toDay.getYear(), toDay.getMonth(), toDay.getDay(),
                        timePicker.getHour(), timePicker.getMinute());

                Log.d("DEBUG","The time will be started at: " +
                        timePicker.getHour()+":"+timePicker.getMinute());

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }


        }else {
            // Cancel
            Intent intent = new Intent(Settings.this,AlarmNotification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }
    }

    private void saveWorkOutMode() {
        int selectedID = rdbGroup.getCheckedRadioButtonId();
        if (selectedID == rdbEasy.getId())
            calendarDB.saveSettingMode(0);
        else if (selectedID == rdbMedium.getId())
            calendarDB.saveSettingMode(1);
        else if (selectedID == rdbHard.getId())
            calendarDB.saveSettingMode(2);
    }

    private void setRadioButton(int mode) {
        if (mode == 0)
            rdbGroup.check(R.id.rdbEasy);
        else if ( mode == 1 )
            rdbGroup.check(R.id.rdbMedium);
        else if ( mode == 2)
            rdbGroup.check(R.id.rdbHard);
    }
}