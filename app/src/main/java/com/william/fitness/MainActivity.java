package com.william.fitness;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.william.fitness.Adapter.ViewMenuAdapter;

public class  MainActivity extends AppCompatActivity {

    Button btnlogout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    public static String name, number,email,address,birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogout = findViewById(R.id.btnLogout);
        //btnProfile = findViewById(R.id.);
        viewPager = findViewById(R.id.viewpaper);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnvgbar);
        setUpViewpaper();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.ic_Calendar:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.ic_add:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.ic_recent:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.ic_User:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });


    }

    private void setUpViewpaper() {
        ViewMenuAdapter viewPaperAdapter = new ViewMenuAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPaperAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.ic_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.ic_Calendar).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.ic_add).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.ic_recent).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.ic_User).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}