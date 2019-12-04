package com.example.producehero.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.producehero.model.entity.OrderItem;
import java.util.List;

/**
 * Data Access Object
 * Used by the DataBase class to access Entity tables
 * Annotated interface, Room will provide the implementation
 */
@Dao
public interface OrderItemDao {

    //Room knows how to automatically insert an entity
    //Building Database
    @Insert
    void insert(OrderItem item);

    //Query to get Live Data List of Order Items
    @Query("SELECT * FROM order_items_table WHERE restaurant_id = :restaurantId")
    LiveData<List<OrderItem>> getOrderItemsForRestaurant(int restaurantId);

    //Room knows how to automatically update an entity
    @Update
    void update(OrderItem OrderItem);

    //Room knows how to automatically delete an entity
    @Delete
    void delete(OrderItem OrderItem);



    @Query("SELECT * FROM order_items_table WHERE id = :orderItemId LIMIT 1")
    LiveData<OrderItem> getOrderItem(int orderItemId);

    @Query("SELECT * FROM order_items_table WHERE id = :orderItemId LIMIT 1")
    OrderItem getOrderItem2(int orderItemId);
}
