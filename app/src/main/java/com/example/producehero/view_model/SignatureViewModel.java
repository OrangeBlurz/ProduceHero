package com.example.producehero.view_model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.producehero.model.ProduceHeroRepository;
import com.example.producehero.model.entity.Restaurant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ViewModel class middle layer object for retrieval of the Live Data of a single Restaurant from the repository
 * as well as updating Restaurants when they get a signature
 */
public class SignatureViewModel extends AndroidViewModel {
    private ProduceHeroRepository repository;
    private LiveData<Restaurant> restaurant;

    public SignatureViewModel(@NonNull Application application) {
        super(application);
        repository = new ProduceHeroRepository(application);
    }

    //Update a Restaurant
    private void updateRestaurant(Restaurant restaurant) {
        repository.updateRestaurant(restaurant);
    }

    //Get a single restaurant for a given Id
    public LiveData<Restaurant> getRestaurant(int restaurantId) {
        if (restaurant == null) {
            restaurant = repository.getRestaurant(restaurantId);
        }
        return restaurant;
    }

    private void updateRestaurant() {
        updateRestaurant(restaurant.getValue());
    }

    //Save signature bitmap
    public void saveSignature(Bitmap signatureBitmap, Context context) {
        File directory = context.getDir("signatures", Context.MODE_PRIVATE);
        String fileName = restaurant.getValue().getName().replaceAll(" ","_") + "_signature.jpg";
        File signatureFile = new File(directory, fileName);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(signatureFile);
            signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            restaurant.getValue().setSignatureFile(fileName);
            updateRestaurant();
        }
    }

    //Load signature Bitmap
    public Bitmap loadSignature(Context context) {
        Bitmap signature = null;
        try {
            File directory = context.getDir("signatures", Context.MODE_PRIVATE);
            File signatureFile = new File(directory, restaurant.getValue().getSignatureFile());
            signature = BitmapFactory.decodeStream(new FileInputStream(signatureFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return signature;
    }

}
