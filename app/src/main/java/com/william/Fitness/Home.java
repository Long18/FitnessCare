package com.william.Fitness;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.GradientDrawable;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.william.Fitness.Adapter.HomeAdapter.CategoriesAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedTutorial;
import com.william.Fitness.Adapter.HomeAdapter.MostViewedAdapter;
import com.william.Fitness.Login.Login;
import com.william.Fitness.Login.WelcomeStartUpScreen;

import java.util.ArrayList;

public class Home extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menu, btnchest, btnarms, btnback, btnlegs, btnbut;
    LinearLayout contentView;
    RelativeLayout searching;
    TextView seemore, seemoree,textPercentage, txtcountnumberwalk, txtcountnumberrun;


    //Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //Count Steps
    DecoView decoView;
    private double MagnitudePrevious = 0;
    private Integer m_stepCount = 0;
    private Integer m_runCount = 0;
    private int m_SumCount = 0;


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
        searching = (RelativeLayout) view.findViewById(R.id.searching_RL);

        seemore = (TextView) view.findViewById(R.id.see_more);
        seemoree = (TextView) view.findViewById(R.id.seee_more);


        drawerLayout = (DrawerLayout) view.findViewById(R.id.draw_layout);
        navigationView = (NavigationView) view.findViewById(R.id.ngv_view);



        textPercentage = (TextView) view.findViewById(R.id.txtCount);
        txtcountnumberwalk = (TextView) view.findViewById(R.id.txtCountNumberWalk);
        txtcountnumberrun = (TextView) view.findViewById(R.id.txtCountNumberRun);
        decoView = (DecoView) view.findViewById(R.id.countSteps);
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null){

                    //Nhận dữ liệu Gia tốc kế:
                    //
                    //Tăng tốc dọc theo trục X
                    //Tăng tốc dọc theo trục Y
                    //Tăng tốc dọc theo trục Z

                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];

                    double Magnitude = Math.sqrt(
                              x_acceleration*x_acceleration
                            + y_acceleration*y_acceleration
                            + z_acceleration*z_acceleration
                    );
                    double MagnitudeDelta = Magnitude - MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    //khác biệt về độ lớn này so với giá trị trước đó
                    //Nếu giá trị này lớn hơn một giá trị ngưỡng cụ thể thì tăng số bước.
                    //Ngưỡng đi bộ = 6
                    //Ngưỡng để chạy = 7~10

                    if (MagnitudeDelta > 6){
                        m_stepCount++;
                        txtcountnumberwalk.setText("Walk: " + m_stepCount.toString());
                    }else if (MagnitudeDelta > 7){
                        m_runCount++;
                        txtcountnumberrun.setText("Run: " + m_runCount.toString());
                    }
                    m_SumCount = m_stepCount + m_runCount;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        //==========================================================================

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
        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTurn();
            }
        });

        seemoree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTurn();
            }
        });


        //Count Steps
        //
        //
        //
        //
        //Make circle progress

       /* SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FF0000"))
                .setRange(0, 100, 20)
                .build();*/

        SeriesItem seriesItem = new SeriesItem.Builder(Color.argb(255, 64, 196, 84))
                .setRange(0, 100, m_SumCount)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_OUTER, Color.parseColor("#FF0000"), 0.2f))
                .setInset(new PointF(20f, 20f))
                .build();



        int backIndex = decoView.addSeries(seriesItem);

        decoView.addEvent(new DecoEvent.Builder(10.135791912f)
                .setIndex(backIndex)
                .setDelay(500)
                .build());

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                float percentFilled = ((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                textPercentage.setText(String.format("%.0f%%", percentFilled * 100f));


            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

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
                if (noFunction != null && noFunction.isShowing()) {
                    noFunction.dismiss();
                }
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

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_mong, "Tập Mông", "Bài tập này giúp bạn \nlàm đẹp vóc dáng"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_chay_bo, "Chạy Bộ", "Bài tập này giúp bạn \nnâng cao sức bền"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_bung_1, "Tập Bụng", "Không ai không thích \nbản thân mình đẹp"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_hit_dat_3, "Hít Đất", "Nỗ lực mọi thứ \nđể thành công"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(featuredTutorialArrayList);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_hit_dat, "Hít Đất", "Bài tập giúp lên cơ tay"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_bung_red, "Gập Bụng", "Bài tập giúp săn chắc cơ bụng"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.king_pigeon_pose, "King Pieon Pose", "Bài tập dãn cơ lưng"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_nguc_2, "Tập Ngực", "Bài tập săn chắc ngực"));

        adapter = new MostViewedAdapter(featuredTutorialArrayList);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_chay_bo, "Bài tập sức khoẻ", "Dành ra 15 phút mỗi ngày để chạy bộ để cải thiện sức khoẻ của bản thân"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_bung_red, "Bài Tập Bụng", "Chỉ cần mỗi ngày 50 cái, không cần liên tục sau 1 tháng bạn sẽ thấy sự khác biệt"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_hit_dat, "Hít đất", "Cải thiện sức bền của bản thân chỉ với 50 lần mỗi ngày"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ex_mong, "Squat", "Cải thiện vóc dáng bằng cách tập mông đều đặn"));

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
            case R.id.nav_search:
                startActivity(new Intent(getActivity().getApplicationContext(), SearchActivity.class));
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
            /*case R.id.nav_profile:


                break;*/
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
            case R.id.nav_settings:
                startActivity(new Intent(getActivity().getApplicationContext(), Settings.class));
                break;
        }

        return true;
    }

    public void searchTurn() {
        startActivity(new Intent(getActivity().getApplicationContext(), SearchActivity.class));
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", m_stepCount);
        editor.putInt("runCount", m_runCount);
        editor.apply();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", m_stepCount);
        editor.putInt("runCount", m_runCount);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        m_stepCount = sharedPreferences.getInt("stepCount", 0);
        m_runCount = sharedPreferences.getInt("runCount", 0);

        m_SumCount = m_stepCount + m_runCount;
    }
}
