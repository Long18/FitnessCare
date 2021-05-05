package com.william.Fitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        ListView listView = findViewById(R.id.list);
        List<String> mylist = new ArrayList<>();
        mylist.add("Chest"); mylist.add("Arms");
        mylist.add("Legs"); mylist.add("Back");
        mylist.add("Legs"); mylist.add("But");

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}