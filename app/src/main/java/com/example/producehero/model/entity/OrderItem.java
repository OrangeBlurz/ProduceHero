package com.example.producehero.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Room Entity Object
 * POJO object representing an Order Item.
 * Room will create a SQLite table for this object with the member variables as column names (unless otherwise specified)
 */
@Entity(tableName = "order_items_table")
public class OrderItem implements Serializable {
    //Identify the primary key for the table
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "restaurant_id")
    private int restaurantId;
    private String name;
    private int quantity;
    private int weight;

    //Public constructor so Room can create objects from the data it pulls from the database
    public OrderItem(int restaurantId, String name, int quantity, int weight) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    //Getters, allow Room to pull data from the object and into the columns of the database
    public int getId() {
        return id;
    }
    public int getRestaurantId() {
        return restaurantId;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getWeight() {
        return weight;
    }

    //Setters
    //Used to allow Room to set the id, not in the constructor
    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
