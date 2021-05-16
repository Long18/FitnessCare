package com.william.Fitness;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Adapter.ExerciseAdapter.ExerciseAdapter;
import com.william.Fitness.Adapter.RecyclerViewAdapter;
import com.william.Fitness.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnUpload;

    private final List<Exercise> exerciseList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        initData();
        //Hook
        btnUpload = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.list_view);
        adapter = new ExerciseAdapter(exerciseList,getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updateImageExcercise.class);
                startActivity(intent);
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