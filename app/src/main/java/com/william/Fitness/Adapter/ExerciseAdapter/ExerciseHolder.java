package com.william.Fitness.Adapter.ExerciseAdapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Interface.ItemClickListener;
import com.william.Fitness.R;

public class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView image;
    public TextView text;

    private ItemClickListener itemClickListener;

    public ExerciseHolder(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.ex_img);
        text = itemView.findViewById(R.id.ex_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition());
    }
}
