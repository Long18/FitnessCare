package com.william.Fitness;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.william.Fitness.Adapter.HomeAdapter.CategoriesAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedTutorial;
import com.william.Fitness.Adapter.HomeAdapter.MostViewedAdapter;
import com.william.Fitness.Login.Login;
import com.william.Fitness.Login.WelcomeStartUpScreen;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menu, btnchest, btnarms, btnback, btnlegs, btnbut;
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
        btnchest = (ImageView) view.findViewById(R.id.img_chest);
        btnarms = (ImageView) view.findViewById(R.id.img_arms);
        btnback = (ImageView) view.findViewById(R.id.img_back);
        btnlegs = (ImageView) view.findViewById(R.id.img_legs);
        btnbut = (ImageView) view.findViewById(R.id.img_but);
        contentView = (LinearLayout) view.findViewById(R.id.contentView);


        drawerLayout = (DrawerLayout) view.findViewById(R.id.draw_layout);
        navigationView = (NavigationView) view.findViewById(R.id.ngv_view);


        navigationDraw();


        //Functions
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();

        btnchest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnlegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        return view;
    }

    public void showDialog() {
        final Dialog noFunction = new Dialog(getActivity(), R.style.df_dialog);
        noFunction.setContentView(R.layout.dialog_no_function);
        noFunction.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        noFunction.show();
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

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Xin chào", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Đây là", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Bản test", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_username, "Chưa chính thức", "Test"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(featuredTutorialArrayList);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Chức năng này", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Sẽ được", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Phát triển thêm", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_username, "Sau này", "Test"));

        adapter = new MostViewedAdapter(featuredTutorialArrayList);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero, "Cám ơn", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address, "Các bạn", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest, "Đã ủng hộ", "Test"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_username, "Ứng dụng", "Test"));

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
            case R.id.nav_home:
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(getActivity().getApplicationContext(), Login.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(getActivity().getApplicationContext(), WelcomeStartUpScreen.class));
                break;
            case R.id.nav_chest:
                showDialog();
                break;
            case R.id.nav_back:
                showDialog();
                break;
            case R.id.nav_arms:
                showDialog();
                break;
            case R.id.nav_but:
                showDialog();
                break;
            case R.id.nav_share:
                Uri uriFace = Uri.parse("https://www.facebook.com/William.2418/");
                Intent intentFace = new Intent(Intent.ACTION_VIEW, uriFace);
                startActivity(intentFace);
                break;
            case R.id.nav_rate:
                Uri uriGit = Uri.parse("https://github.com/Long18");
                Intent intentGit = new Intent(Intent.ACTION_VIEW, uriGit);
                startActivity(intentGit);
                break;
        }

        return true;
    }
}
