package com.example.producehero.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.producehero.model.ProduceHeroRepository;
import com.example.producehero.model.entity.Restaurant;
import java.util.List;

/**
 * ViewModel class middle layer object for retrieval of the Live Data List of Restaurants in a city from the repository
 */
public class RoutePlanViewModel extends AndroidViewModel {
    private ProduceHeroRepository repository;
    public RoutePlanViewModel(@
                                      NonNull Application application) {
        super(application);
        repository = new ProduceHeroRepository(application);
    }

    //Get all restaurants in a given city
    public LiveData<List<Restaurant>> getRestaurantListForCity(String cityName) {
        return repository.getRestaurantListForCity(cityName);
    }
}
