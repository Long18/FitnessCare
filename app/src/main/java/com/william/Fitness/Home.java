package com.william.Fitness;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.william.Fitness.Adapter.HomeAdapter.CategoriesAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedTutorial;
import com.william.Fitness.Adapter.HomeAdapter.MostViewedAdapter;
import com.william.Fitness.Login.WelcomeStartUpScreen;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menu;
    LinearLayout contentView;

    //Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    static final float END_SCALE = 0.7f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //Hooks
        featuredRecycler = (RecyclerView) view.findViewById(R.id.rcv_featured);
        mostViewedRecycler = (RecyclerView) view.findViewById(R.id.rcv_most_view);
        categoriesRecycler = (RecyclerView) view.findViewById(R.id.rcv_categories);
        menu = (ImageView) view.findViewById(R.id.btn_menu);
        contentView = (LinearLayout) view.findViewById(R.id.contentView);


        drawerLayout = (DrawerLayout) view.findViewById(R.id.draw_layout);
        navigationView = (NavigationView) view.findViewById(R.id.ngv_view);


        navigationDraw();


        //Functions
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();


        return view;
    }

    private void navigationDraw() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    private void categoriesRecycler() {
        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_profile_user, "Alo alo", "Viet vo day"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(featuredTutorialArrayList);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_profile_user, "Alo alo", "Viet vo day"));

        adapter = new MostViewedAdapter(featuredTutorialArrayList);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_profile_user, "Alo alo", "Viet vo day"));

        adapter = new FeaturedAdapter(featuredTutorialArrayList);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_categories:
                startActivity(new Intent(getActivity().getApplicationContext(), AllCategories.class));
                break;

        }

        return true;
    }
}
