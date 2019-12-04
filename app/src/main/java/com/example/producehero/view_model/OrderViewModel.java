package com.example.producehero.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.producehero.model.ProduceHeroRepository;
import com.example.producehero.model.entity.OrderItem;
import java.util.List;

/**
 * ViewModel class middle layer object for retrieval of the Live Data List of Order Items from the repository
 */
public class OrderViewModel extends AndroidViewModel {
    private ProduceHeroRepository repository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new ProduceHeroRepository (application);
    }

    //Get all OrderItems from a particular Restaurant Id
    public LiveData<List<OrderItem>> getOrderItemsForRestaurant (int restaurantId) {
        return repository.getOrderItemListForRestaurant(restaurantId);
    }
}
