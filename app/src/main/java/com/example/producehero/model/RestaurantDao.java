package com.example.producehero.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.producehero.model.entity.Restaurant;
import java.util.List;

/**
 * Data Access Object
 * Used by the DataBase class to access Entity tables
 * Annotated interface, Room will provide the implementation
 */
@Dao
public interface RestaurantDao {

    //Room knows how to automatically insert an entity
    @Insert
    void insert(Restaurant restaurant);

    //Route
    //Get a Live Data List of Strings of all unique city names
    @Query("SELECT DISTINCT city_name FROM restaurants_table")
    LiveData<List<String>> getAllUniqueCities();

    //RoutePlan
    //Get a Live Data List of Restaurants in a particular city
    @Query("SELECT * FROM restaurants_table WHERE city_name = :cityName")
    LiveData<List<Restaurant>> getRestaurantListForCity(String cityName);

    //Signature
    //Room knows how to automatically update an entity
    @Update
    void update(Restaurant restaurant);

    //Get a Live Data Restaurant for an id
    @Query("SELECT * FROM restaurants_table WHERE id = :restaurantId LIMIT 1")
    LiveData<Restaurant> getRestaurant(int restaurantId);

    //May not be used in this example
    @Delete
    void delete(Restaurant restaurant);
}
