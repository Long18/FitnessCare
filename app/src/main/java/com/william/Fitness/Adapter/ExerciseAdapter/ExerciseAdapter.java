package com.william.Fitness.Adapter.ExerciseAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.william.Fitness.Interface.ItemClickListener;
import com.william.Fitness.Model.Exercise;
import com.william.Fitness.Model.ExerciseSearch;
import com.william.Fitness.R;
import com.william.Fitness.ViewExercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder> implements Filterable {

    private List<ExerciseSearch> exerciseList;
    private  List<ExerciseSearch> exerciseListSearch;


    public ExerciseAdapter(List<ExerciseSearch> exerciseList) {
        this.exerciseList = exerciseList;
        this.exerciseListSearch = exerciseList;
    }


    @Override
    public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise_view, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseHolder holder, int position) {

        ExerciseSearch ex = exerciseList.get(position);
        if (ex == null) {
            return;
        }

        holder.image.setImageResource(exerciseList.get(position).getImage());
        holder.text.setText(exerciseList.get(position).getName());
        holder.des.setText(exerciseList.get(position).getDesc());
        holder.time.setText(exerciseList.get(position).getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Call to new Activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    exerciseList = exerciseListSearch;
                } else {
                    List<ExerciseSearch> list = new ArrayList<>();
                    for (ExerciseSearch ex : exerciseListSearch) {
                        if (ex.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(ex);
                        }
                    }
                    exerciseList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = exerciseList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                exerciseList = (List<ExerciseSearch>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}