package com.william.Fitness.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.william.Fitness.Calendar;
import com.william.Fitness.Daily_Training;
import com.william.Fitness.Home;
import com.william.Fitness.ListExercise;
import com.william.Fitness.ProfileUser;

public class ViewMenuAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 5;
    public ViewMenuAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Home();
            case 1:
                return new Calendar();
            case 2:
                return new Daily_Training();
            case 3:
                return new ListExercise();
            case 4:
                return new ProfileUser();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
