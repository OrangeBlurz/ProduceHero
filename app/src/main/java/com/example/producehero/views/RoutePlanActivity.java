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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.example.producehero.R;
import com.example.producehero.adapters.RestaurantAdapter;
import com.example.producehero.model.entity.Restaurant;
import com.example.producehero.view_model.RoutePlanViewModel;
import java.util.List;

/**
 * View Activity
 * Responsible for displaying restaurants in a given city
 */
public class RoutePlanActivity extends AppCompatActivity {
    private static final String TAG = "RoutePlanActivity";
    public static final String EXTRA_CITY_NAME = "city_name";

    //Setup an intent to launch this activity
    public static Intent newActivityIntent(Activity activity, String cityName) {
        Intent intent = new Intent(activity, RoutePlanActivity.class);
        intent.putExtra(EXTRA_CITY_NAME, cityName);
        return intent;
    }

    //Member variables
    private RoutePlanViewModel routeViewModel;
    private String cityName;
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        init();
    }

    //Initialize the activity
    private void init() {
        setupViewModel();
        setupToolbar();
        setupRecyclerView();
    }

    //Setup and listen to ViewModel
    private void setupViewModel() {
        cityName = getIntent().getStringExtra(EXTRA_CITY_NAME);

        routeViewModel = ViewModelProviders.of(this).get(RoutePlanViewModel.class);
        routeViewModel.getRestaurantListForCity(cityName).observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                adapter.setRestaurantList(restaurants);
            }
        });
    }

    private void setupToolbar() {
        //Toolbar
        Toolbar toolbar = findViewById(R.id.route_plan_toolbar);
        toolbar.setTitle(cityName + " Route Plan");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    //Setup recycler view and click handlers
    private void setupRecyclerView() {
        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RestaurantAdapter();
        adapter.setItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                startActivity(OrderActivity.newActivityIntent(RoutePlanActivity.this, restaurant));
            }
        });
        adapter.setMapButtonClickListener(new RestaurantAdapter.OnMapButtonClickListener() {
            @Override
            public void onMapButtonClick(Restaurant restaurant) {
                //Launch Google Maps directions.  Does not start navigation automatically similar to the video
                Uri googleMapUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + Uri.encode(restaurant.getAddress()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
