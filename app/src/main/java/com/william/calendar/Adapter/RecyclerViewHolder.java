package com.william.calendar.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.calendar.Interface.ItemClickListener;
import com.william.calendar.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView image;
    public TextView text;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView){
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.ex_img);
        text = (TextView)itemView.findViewById(R.id.ex_name);

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