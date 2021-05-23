package com.william.Fitness;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.william.Fitness.Database.CalendarDB;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        //Init View
        btnSave = findViewById(R.id.btnSave);

        rdbGroup = findViewById(R.id.rdbGroup);
        rdbEasy = findViewById(R.id.rdbEasy);
        rdbMedium = findViewById(R.id.rdbMedium);
        rdbHard = findViewById(R.id.rdbHard);

        switchAlarm = findViewById(R.id.switchAlarm);

        timePicker = findViewById(R.id.timePicker);

        calendarDB = new CalendarDB(Settings.this);

        int mode = calendarDB.getSettingMode();
        setRadioButton(mode);



        //Event call

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmNotification alarmNotification = new AlarmNotification();
                alarmNotification.onReceive(getApplicationContext(),getIntent());
                saveWorkOutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(getApplicationContext(), "Saved !!!!", Toast.LENGTH_SHORT).show();

            }
        });





    }





    private void saveAlarm(boolean checked) {
        if (checked){
            AlarmManager alarmManager; //= (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            //intent = new Intent(this,AlarmNotification.class);

            intent = new Intent("fitness.william.action.DISPLAY_NOTIFICATION");
            //pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            PendingIntent broadcast = PendingIntent.getBroadcast(Settings.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);


            //Set time
            Calendar calendar = Calendar.getInstance();
            Date toDay = Calendar.getInstance().getTime();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                calendar.set(toDay.getYear(), toDay.getMonth(), toDay.getDay(),
                        timePicker.getHour(), timePicker.getMinute());

                Log.d("DEBUG","The time will be started at: " +
                        timePicker.getHour()+":"+timePicker.getMinute());

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, broadcast);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
            }




        }else {
            // Cancel
            //Intent intent = new Intent(this,AlarmNotification.class);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager; //= (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent;

            //intent = new Intent(this,AlarmNotification.class);

            intent = new Intent("fitness.william.action.DISPLAY_NOTIFICATION");
            //pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

            PendingIntent broadcast = PendingIntent.getBroadcast(Settings.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            //AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(broadcast);

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

    public void backMainActivity(View view) {
        startActivity(new Intent(Settings.this, MainActivity.class));
    }
}