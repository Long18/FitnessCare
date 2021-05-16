package com.william.Fitness;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.william.Fitness.Adapter.ExerciseAdapter.ExerciseAdapter;
import com.william.Fitness.Model.ExerciseSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnUpload;
    SearchView searchView;
    Toolbar toolbar;
    private List<ExerciseSearch> exerciseList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_view);
        //initData();

        //Hook
        btnUpload = findViewById(R.id.btnAdd);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.list_view);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new ExerciseAdapter(data());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration  = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updateImageExcercise.class);
                startActivity(intent);
            }
        });

    }

    private List<ExerciseSearch> data(){
        List<ExerciseSearch> list = new ArrayList<>();

        list.add(new ExerciseSearch(R.drawable.ex_hit_dat,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_hit_dat_1,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_hit_dat_3,  "Hít đất","Bài tập giúp lên cơ tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_1,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_2,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_3,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_red,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_white,  "Gập bụng","Bài tập giúp săn chắc cơ bụng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_chay,  "Chạy","Bài tập khoẻ", "5:00"));
        list.add(new ExerciseSearch(R.drawable.ex_chay_bo,  "Chạy","Bài tập khoẻ", "5:00"));
        list.add(new ExerciseSearch(R.drawable.ex_mong,  "Squat","Bài tập săn chắc mông", "1:00"));
        list.add(new ExerciseSearch(R.drawable.ex_nguc_2,  "Tập Ngực","Bài tập săn chắc ngực", "1:00"));
        list.add(new ExerciseSearch(R.drawable.king_pigeon_pose, "King pigeon Pose","ABC", "1:00"));
        list.add(new ExerciseSearch(R.drawable.facing_dog_pose, "Facing Dog Pose","ABC", "1:00"));
        list.add(new ExerciseSearch(R.drawable.cobra_pose, "Cobra Pose","ABC", "1:00"));
        list.add(new ExerciseSearch(R.drawable.camel_pose, "Camel Pose","ABC", "1:00"));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        //SearchView searchView = (SearchView) searchItem.getActionView();


        //searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();

       // searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
        }
        super.onBackPressed();
    }
    /*@Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }*/

    /*private void  initData() {
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


    }*/
}

