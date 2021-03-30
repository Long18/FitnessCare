package com.william.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.william.calendar.Custome.WorkoutDone;
import com.william.calendar.Database.CalendarDB;


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

        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendar);
        List<String> workoutDay = calendarDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();
        for (String value:workoutDay)
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        materialCalendarView.addDecorator(new WorkoutDone(convertedList));
    }
}