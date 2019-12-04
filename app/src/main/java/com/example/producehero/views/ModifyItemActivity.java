package com.example.producehero.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.producehero.R;
import com.example.producehero.model.entity.OrderItem;
import com.example.producehero.view_model.ModifyItemViewModel;

/**
 * View activity
 * activity handles the alteration of any Order item
 */
public class ModifyItemActivity extends AppCompatActivity {
    private static final String TAG = "ModifyItemActivity";
    public static final String EXTRA_ORDER_ITEM = "order_item";

    //Setup an intent to launch this activity
    public static Intent newActivityIntent(Activity activity, OrderItem item) {
        Intent intent = new Intent(activity, ModifyItemActivity.class);
        intent.putExtra(EXTRA_ORDER_ITEM, item);
        return intent;
    }

    //Member variables
    private ModifyItemViewModel modifyItemViewModel;
    private TextView numberPickerDisplayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        init();

        TextView itemNameView = findViewById(R.id.modify_item_name_view);
        numberPickerDisplayView = findViewById(R.id.picker_display);

        itemNameView.setText(modifyItemViewModel.getName() + "(" + modifyItemViewModel.getWeight()+"kg)");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.modify_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_item:
                modifyItemViewModel.updateOrderItem();
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    //Initialize the Activity
    private void init() {
        setupViewModel();
        setupToolbar();
        setupButtonListeners();
    }

    //Setup the ViewModel
    private void setupViewModel() {
        OrderItem orderItem = (OrderItem) getIntent().getSerializableExtra(EXTRA_ORDER_ITEM);
        modifyItemViewModel = ViewModelProviders.of(this).get(ModifyItemViewModel.class);
        modifyItemViewModel.setOrderItem(orderItem);

        subscribe();
    }

    //Subscribe to changes emitted by the ViewModel
    private void subscribe() {
        modifyItemViewModel.getModifiedQuantity().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                numberPickerDisplayView.setText(Integer.toString(integer));
            }
        });
    }

    //Setup the ToolBar
    private void setupToolbar() {
        //Toolbar
        Toolbar toolbar = findViewById(R.id.modify_toolbar);
        toolbar.setTitle(getString(R.string.modify_item_activity_title));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    //Setup Button Listeners for the activity
    private void setupButtonListeners() {
        Button numberPickerIncrementButton = findViewById(R.id.picker_inc);
        Button numberPickerDecrementButton = findViewById(R.id.picker_dec);
        Button deleteItemButton = findViewById(R.id.delete_item_button);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyItemViewModel.deleteItem();
                finish();
            }
        });

        numberPickerDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyItemViewModel.pickerDecrement();
            }
        });
        numberPickerIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyItemViewModel.pickerIncrement();
            }
        });
    }
}
