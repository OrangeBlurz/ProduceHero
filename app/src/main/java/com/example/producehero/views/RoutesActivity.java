package com.example.producehero.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.producehero.R;
import com.example.producehero.adapters.CityNameAdapter;
import com.example.producehero.view_model.RoutesViewModel;

import java.util.List;

/**
 * View Activity
 * Responsible for displaying unique city names
 */
public class RoutesActivity extends AppCompatActivity {
    private static final String TAG = "RoutesActivity";

    //Setup an intent to launch this activity
    public static Intent newActivityIntent(Activity activity) {
        Intent intent = new Intent(activity, RoutesActivity.class);
        return intent;
    }

    //Member variables
    private RoutesViewModel routesViewModel;
    private CityNameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.routes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sign_out:
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        setupViewModel();
        setupToolbar();
        setupRecyclerView();
    }

    //Setup and listen to ViewModel
    private void setupViewModel() {
        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel.class);
        routesViewModel.getUniqueCities().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> cityNames) {
                //update RecyclerView
                adapter.setCityNameList(cityNames);
            }
        });
    }

    //Setup Toolbar
    private void setupToolbar() {
        //Toolbar Setup
        Toolbar toolbar = findViewById(R.id.routes_toolbar);
        toolbar.setTitle(getString(R.string.routes_activity_title));
        setSupportActionBar(toolbar);
    }

    //Setup recycler view and click listeners
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //May not be true for this view
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new CityNameAdapter();
        adapter.setItemClickListener(new CityNameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String cityName) {
                startActivity(RoutePlanActivity.newActivityIntent(RoutesActivity.this, cityName));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
