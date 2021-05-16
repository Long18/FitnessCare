package com.william.Fitness.Adapter.ExerciseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Interface.ItemClickListener;
import com.william.Fitness.Model.Exercise;
import com.william.Fitness.R;
import com.william.Fitness.ViewExercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder>{

    private final List<Exercise> exerciseList;
    private final Context context;

    public ExerciseAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @Override
    public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise,parent,false);

        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseHolder holder, int position) {
        holder.image.setImageResource(exerciseList.get(position).getImage_id());
        holder.text.setText(exerciseList.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Call to new Activity
                Intent intent = new Intent(context, ViewExercise.class);
                intent.putExtra("image_id",exerciseList.get(position).getImage_id());
                intent.putExtra("name",exerciseList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
