package com.example.producehero.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.producehero.model.ProduceHeroRepository;
import java.util.List;

/**
 * ViewModel class middle layer object for retrieval of the Live Data List of unique cities from the repository
 */
public class RoutesViewModel extends AndroidViewModel {
    private ProduceHeroRepository repository;
    private LiveData<List<String>> uniqueCities;

    public RoutesViewModel(@NonNull Application application) {
        super(application);
        repository = new ProduceHeroRepository(application);
        //This value is something we will want to know off the start and has no parameters so it is fetched immediately
        uniqueCities = repository.getUniqueCityNames();
    }

    //Get a list of unique cities
    public LiveData<List<String>> getUniqueCities() {
        return uniqueCities;
    }
}
