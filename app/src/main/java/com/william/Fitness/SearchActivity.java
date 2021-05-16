package com.william.Fitness;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Adapter.ExerciseAdapter.ExerciseAdapter;
import com.william.Fitness.Model.ExerciseSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnUpload;

    private final List<ExerciseSearch> exerciseList = new ArrayList<ExerciseSearch>();
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
        adapter = new ExerciseAdapter(exerciseList, getApplicationContext());
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
        exerciseList.add(new ExerciseSearch(R.drawable.ex_hit_dat,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_hit_dat_1,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_hit_dat_3,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung_1,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung_2,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung_3,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung_red,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_bung_white,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_chay,  "Chạy","Bài tập khoẻ", "5:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_chay_bo,  "Chạy","Bài tập khoẻ", "5:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_mong,  "Squat","Bài tập săn chắc mông", "1:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.ex_nguc_2,  "Tập Ngực","Bài tập săn chắc ngực", "1:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.king_pigeon_pose, "King pigeon Pose","ABC", "1:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.facing_dog_pose, "Facing Dog Pose","ABC", "1:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.cobra_pose, "Cobra Pose","ABC", "1:00"));
        exerciseList.add(new ExerciseSearch(R.drawable.camel_pose, "Camel Pose","ABC", "1:00"));


    }
}

