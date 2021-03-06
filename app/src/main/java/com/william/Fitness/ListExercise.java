package com.william.Fitness;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Adapter.RecyclerViewAdapter;
import com.william.Fitness.Model.Exercise;
import com.william.Fitness.Model.ExerciseSearch;

import java.util.ArrayList;
import java.util.List;

public class ListExercise extends Fragment {

    private final List<Exercise> exerciseList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_exercises,container,false);
        initData();
        recyclerView = view.findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList,getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initData() {

        exerciseList.add(new Exercise(R.drawable.ex_hit_dat,  "Hít đất"));
        exerciseList.add(new Exercise(R.drawable.ex_hit_dat_1,  "Hít đất"));
        exerciseList.add(new Exercise(R.drawable.ex_hit_dat_3,  "Hít đất"));
        exerciseList.add(new Exercise(R.drawable.ex_bung,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_bung_1,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_bung_2,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_bung_3,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_bung_red,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_bung_white,  "Gập bụng"));
        exerciseList.add(new Exercise(R.drawable.ex_chay,  "Chạy"));
        exerciseList.add(new Exercise(R.drawable.ex_chay_bo,  "Chạy"));
        exerciseList.add(new Exercise(R.drawable.ex_mong,  "Squat"));
        exerciseList.add(new Exercise(R.drawable.ex_nguc_2,  "Tập Ngực"));
        exerciseList.add(new Exercise(R.drawable.low_lunge_pose, "Low Lunge Pose"));
        exerciseList.add(new Exercise(R.drawable.king_pigeon_pose, "King pigeon Pose"));
        exerciseList.add(new Exercise(R.drawable.facing_dog_pose, "Facing Dog Pose"));
        exerciseList.add(new Exercise(R.drawable.cobra_pose, "Cobra Pose"));
        exerciseList.add(new Exercise(R.drawable.camel_pose, "Camel Pose"));


    }
}