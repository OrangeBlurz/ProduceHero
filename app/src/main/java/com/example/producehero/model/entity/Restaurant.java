package com.example.producehero.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room Entity Object
 * POJO object representing an Order Item.
 * Room will create a SQLite table for this object with the member variables as column names (unless otherwise specified)
 */
@Entity(tableName = "restaurants_table")
public class Restaurant{
    //This is not being autogenerated to make the insertion of fake data easier
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    @ColumnInfo(name = "city_name")
    private String city;
    @ColumnInfo(name = "signature_file")
    private String signatureFile;

    //Public constructor so Room can create objects from the data it pulls from the database
    //Passing the Id as a value in the constructor for easier populating of the database
    public Restaurant(int id, String name, String address, String city, String signatureFile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.signatureFile = signatureFile;
    }

    //Getters, allow Room to pull data from the object and into the columns of the database
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getSignatureFile() {
        return signatureFile;
    }

    //Setters
    public void setSignatureFile(String signatureFile) {
        this.signatureFile = signatureFile;
    }
    public void setId(int id) {
        this.id = id;
    }
}