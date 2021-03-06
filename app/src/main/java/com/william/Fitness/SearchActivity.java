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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updateImageExcercise.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


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
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
        super.onBackPressed();
    }



    private List<ExerciseSearch> data() {
        List<ExerciseSearch> list = new ArrayList<>();

        list.add(new ExerciseSearch(R.drawable.ex_hit_dat, "H??t ?????t", "B??i t???p gi??p l??n c?? tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_hit_dat_1, "H??t ?????t", "B??i t???p gi??p l??n c?? tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_hit_dat_3, "H??t ?????t", "B??i t???p gi??p l??n c?? tay", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_1, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_2, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_3, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_red, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_bung_white, "G???p b???ng", "B??i t???p gi??p s??n ch???c c?? b???ng", "0:30"));
        list.add(new ExerciseSearch(R.drawable.ex_chay, "Ch???y", "B??i t???p kho???", "5:00"));
        list.add(new ExerciseSearch(R.drawable.ex_chay_bo, "Ch???y", "B??i t???p kho???", "5:00"));
        list.add(new ExerciseSearch(R.drawable.ex_mong, "Squat", "B??i t???p s??n ch???c m??ng", "1:00"));
        list.add(new ExerciseSearch(R.drawable.ex_nguc_2, "T???p Ng???c", "B??i t???p s??n ch???c ng???c", "1:00"));
        list.add(new ExerciseSearch(R.drawable.king_pigeon_pose, "King pigeon Pose", "B??i t???p u???n d???o", "1:00"));
        list.add(new ExerciseSearch(R.drawable.facing_dog_pose, "Facing Dog Pose", "B??i t???p d??n c?? l??ng", "1:00"));
        list.add(new ExerciseSearch(R.drawable.cobra_pose, "Cobra Pose", "B??i t???p cho c?? l??ng", "1:00"));
        list.add(new ExerciseSearch(R.drawable.camel_pose, "Camel Pose", "B??i t???p v??? c?? l??ng", "1:00"));

        return list;
    }
}

