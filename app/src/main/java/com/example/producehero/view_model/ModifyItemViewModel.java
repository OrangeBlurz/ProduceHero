package com.example.producehero.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.producehero.model.ProduceHeroRepository;
import com.example.producehero.model.entity.OrderItem;

import java.util.List;

/**
 * ViewModel class middle layer object for updating and deleting OrderItems from the repository
 * Creation happens on the initialization of the database
 */
public class ModifyItemViewModel extends AndroidViewModel {
    private static final String TAG = "ModifyItemViewModel";
    private ProduceHeroRepository repository;
    private MutableLiveData<Integer> modifiedQuantity = new MutableLiveData<>();
    private OrderItem orderItem;

    public ModifyItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ProduceHeroRepository(application);
    }

    public void updateOrderItem() {
        orderItem.setQuantity(modifiedQuantity.getValue());
        updateOrderItem(orderItem);
    }

    //Update an Order Item
    private void updateOrderItem(OrderItem orderItem) {
        repository.updateOrderItem(orderItem);
    }

    public void deleteItem() {
        deleteItem(orderItem);
    }

    //Delete an Order Item
    private void deleteItem(OrderItem orderItem) {
        repository.deleteOrderItem(orderItem);
    }

    //Dirty, Set the data to be held in the ViewModel
    public void setOrderItem (OrderItem orderItem) {
        if (this.orderItem == null) {
            this.orderItem = orderItem;
            modifiedQuantity.postValue(orderItem.getQuantity());
        }
    }

    //Get Mutable Live Data to be observed by the View
    public LiveData<Integer> getModifiedQuantity() {
        return modifiedQuantity;
    }

    //Utility functions for the view
    public void pickerDecrement() {
        int value;
        value = modifiedQuantity.getValue();
        if (value - 1 >= 0) {
            modifiedQuantity.postValue(value - 1);
        }
    }

    public void pickerIncrement() {
        int value = modifiedQuantity.getValue();
        modifiedQuantity.postValue(value + 1);
    }

    public String getName() {
        return orderItem.getName();
    }

    public int getWeight() {
        return orderItem.getWeight();
    }
}
