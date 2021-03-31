package com.william.calendar.Custome;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.william.calendar.Calendar;

import java.util.HashSet;

public class WorkoutDone implements DayViewDecorator {

    HashSet<CalendarDay> list;
    ColorDrawable colorDrawable;

    public WorkoutDone(HashSet<CalendarDay> list) {
        this.list = list;
        colorDrawable = new ColorDrawable(Color.parseColor("#FF7A7A"));
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return list.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(colorDrawable);
    }
}
