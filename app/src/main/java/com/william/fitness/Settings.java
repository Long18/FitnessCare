package com.william.fitness;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.william.fitness.Database.CalendarDB;

import java.util.Calendar;
import java.util.Date;

public class Settings extends Fragment {

    Button btnSave;
    RadioButton rdbEasy, rdbMedium,rdbHard;
    RadioGroup rdbGroup;
    CalendarDB calendarDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        //Init View
        btnSave = view.findViewById(R.id.btnSave);

        rdbGroup = view.findViewById(R.id.rdbGroup);
        rdbEasy = view.findViewById(R.id.rdbEasy);
        rdbMedium = view.findViewById(R.id.rdbMedium);
        rdbHard = view.findViewById(R.id.rdbHard);

        switchAlarm = view.findViewById(R.id.switchAlarm);

        timePicker = view.findViewById(R.id.timePicker);

        calendarDB = new CalendarDB(getActivity());

        int mode = calendarDB.getSettingMode();
        setRadioButton(mode);



        //Event call

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWorkOutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(getActivity(), "Saved !!!!", Toast.LENGTH_SHORT).show();
            }
        });



        return view;



    }





    private void saveAlarm(boolean checked) {
        if (checked){
            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(getActivity(),AlarmNotification.class);
            pendingIntent = PendingIntent.getBroadcast(getActivity(),1,intent,0);

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
            Intent intent = new Intent(getActivity(),AlarmNotification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
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