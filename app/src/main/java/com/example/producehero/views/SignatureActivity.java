package com.example.producehero.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.producehero.R;
import com.example.producehero.model.entity.Restaurant;
import com.example.producehero.view_model.SignatureViewModel;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * View Activity
 * Responsible for showing and captuing signatures for orders
 */
public class SignatureActivity extends AppCompatActivity {
    private static final String TAG = "SignatureActivity";
    public static final String EXTRA_RESTAUARNT_ID = "restaurantId";

    //Setup an intent to launch this activity
    public static Intent newActivityIntent(Activity activity, int restaurant_id) {
        Intent intent = new Intent(activity, SignatureActivity.class);
        intent.putExtra(EXTRA_RESTAUARNT_ID, restaurant_id);
        return intent;
    }

    //Member variables
    private SignaturePad signaturePad;
    private int restaurantId;
    private SignatureViewModel signatureViewModel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        init();

        signaturePad = findViewById(R.id.signature_pad);
    }

    private void init() {
        context = this;
        setupViewModel();
        setupClickListeners();
    }

    private void setupViewModel() {
        restaurantId = getIntent().getIntExtra(EXTRA_RESTAUARNT_ID, 0);

        signatureViewModel = ViewModelProviders.of(this).get(SignatureViewModel.class);
        signatureViewModel.getRestaurant(restaurantId).observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                if(!restaurant.getSignatureFile().isEmpty()) {
                    signaturePad.setSignatureBitmap(signatureViewModel.loadSignature(context));
                }
            }
        });
    }

    private void setupClickListeners() {
        Button cancelButton = findViewById(R.id.cancel_button);
        Button doneButton = findViewById(R.id.done_button);
        TextView clearView = findViewById(R.id.clear_button);

        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureViewModel.saveSignature(signaturePad.getSignatureBitmap(), context);
                finish();
            }
        });
    }
}
