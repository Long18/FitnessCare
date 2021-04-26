package com.william.Fitness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Adapter.HomeAdapter.FeaturedAdapter;
import com.william.Fitness.Adapter.HomeAdapter.FeaturedTutorial;

import java.util.ArrayList;

public class Home extends Fragment {
   RecyclerView featuredRecycler;
   RecyclerView.Adapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        featuredRecycler = (RecyclerView) view.findViewById(R.id.featured_recycler);
        featuredRecycler();
        
        return view;
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext() ,LinearLayoutManager.HORIZONTAL, false ));

        ArrayList<FeaturedTutorial> featuredTutorialArrayList = new ArrayList<>();

        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_login_hero,"Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_address,"Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_chest,"Alo alo", "Viet vo day"));
        featuredTutorialArrayList.add(new FeaturedTutorial(R.drawable.ic_profile,"Alo alo", "Viet vo day"));

        adapter = new FeaturedAdapter(featuredTutorialArrayList);
        featuredRecycler.setAdapter(adapter);
    }
}
