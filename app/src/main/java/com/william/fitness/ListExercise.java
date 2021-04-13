package com.william.fitness;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.william.fitness.Adapter.RecyclerViewAdapter;
import com.william.fitness.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ListExercise extends AppCompatActivity {

    private final List<Exercise> exerciseList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        initData();

        recyclerView = findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnvgbar);

        // Select menu on navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(ListExercise.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_Calendar:
                        Intent calendar = new Intent(ListExercise.this, Calendar.class);
                        startActivity(calendar);
                        break;
                    case R.id.ic_add:
                        Intent add = new Intent(ListExercise.this, Daily_Training.class);
                        startActivity(add);
                        break;
                    case R.id.ic_recent:
                        Intent recent = new Intent(ListExercise.this, ListExercise.class);
                        startActivity(recent);
                        break;
                    case R.id.ic_User:
                        Intent user = new Intent(ListExercise.this, Settings.class);
                        startActivity(user);
                        break;
                }
                return false;
            }
        });

    }

    private void initData() {
        exerciseList.add(new Exercise(R.drawable.low_lunge_pose, "Low Lunge Pose"));
        exerciseList.add(new Exercise(R.drawable.king_pigeon_pose, "King pigeon Pose"));
        exerciseList.add(new Exercise(R.drawable.facing_dog_pose, "Facing Dog Pose"));
        exerciseList.add(new Exercise(R.drawable.cobra_pose, "Cobra Pose"));
        exerciseList.add(new Exercise(R.drawable.camel_pose, "Camel Pose"));


    }
}