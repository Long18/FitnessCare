package com.william.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.william.fitness.Custome.WorkoutDone;
import com.william.fitness.Database.CalendarDB;


import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    CalendarDB calendarDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarDB = new CalendarDB(this);

        materialCalendarView = findViewById(R.id.calendar_view);
        List<String> workoutDay = calendarDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();
        for (String value:workoutDay)
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        materialCalendarView.addDecorator(new WorkoutDone(convertedList));

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnvgbar);

        // Select menu on navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(Calendar.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_Calendar:
                        Intent calendar = new Intent(Calendar.this, Calendar.class);
                        startActivity(calendar);
                        break;
                    case R.id.ic_add:
                        Intent add = new Intent(Calendar.this, Daily_Training.class);
                        startActivity(add);
                        break;
                    case R.id.ic_recent:
                        Intent recent = new Intent(Calendar.this, ListExercise.class);
                        startActivity(recent);
                        break;
                    case R.id.ic_User:
                        Intent user = new Intent(Calendar.this, Settings.class);
                        startActivity(user);
                        break;
                }
                return false;
            }
        });


    }
}