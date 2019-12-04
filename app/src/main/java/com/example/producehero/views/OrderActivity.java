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
import android.view.View;

import com.example.producehero.R;
import com.example.producehero.adapters.OrderItemAdapter;
import com.example.producehero.model.entity.OrderItem;
import com.example.producehero.model.entity.Restaurant;
import com.example.producehero.view_model.OrderViewModel;

import java.util.List;

/**
 * View activity
 * activity displaying the list of Order Items
 */
public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    public static final String EXTRA_RESTAURANT_ID = "restaurantId";

    //Setup an intent to launch this activity
    public static Intent newActivityIntent(Activity activity, Restaurant restaurant) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, restaurant.getId());
        return intent;
    }

    //Member variables
    private OrderViewModel orderViewModel;
    private int restaurantId;
    private OrderItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sign_order:
                startActivity(SignatureActivity.newActivityIntent(OrderActivity.this, restaurantId));
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    //Initialize the activity
    private void init() {
        setupViewModel();
        setupToolbar();
        setupRecyclerView();
    }

    //Setup connections to ViewModel
    private void setupViewModel() {
        restaurantId = getIntent().getIntExtra(EXTRA_RESTAURANT_ID, 0);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        orderViewModel.getOrderItemsForRestaurant(restaurantId).observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                adapter.setOrderItemList(orderItems);
            }
        });
    }

    //Setup the toolbar
    private void setupToolbar() {
        //Toolbar
        Toolbar toolbar = findViewById(R.id.order_toolbar);
        toolbar.setTitle(getString(R.string.order_activity_title));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    //Setup the recycler view and click listeners
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.order_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new OrderItemAdapter();
        adapter.setButtonClickListener(new OrderItemAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(OrderItem item) {
                startActivity(ModifyItemActivity.newActivityIntent(OrderActivity.this, item));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
