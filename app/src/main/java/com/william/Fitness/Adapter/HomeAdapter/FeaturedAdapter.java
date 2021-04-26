package com.william.Fitness.Adapter.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedTutorial> featuredTutorialArrayList;

    public FeaturedAdapter(ArrayList<FeaturedTutorial> featuredTutorialArrayList) {
        this.featuredTutorialArrayList = featuredTutorialArrayList;
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, description;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            description = itemView.findViewById(R.id.feartured_desc);

        }
    }


    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card,parent,false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        FeaturedTutorial featuredTutorial = featuredTutorialArrayList.get(position);

        holder.image.setImageResource(featuredTutorial.getImage());
        holder.title.setText(featuredTutorial.getTitle());
        holder.description.setText(featuredTutorial.getDescription());

    }

    @Override
    public int getItemCount() {
        return featuredTutorialArrayList.size();
    }

}


