package com.example.producehero.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.producehero.model.entity.OrderItem;
import com.example.producehero.model.entity.Restaurant;

import java.util.List;

/**
 * Repository class
 * Used as an abstraction to the rest of the application.
 * Decides if data should be taken from Database or remotely, however in this project only Room database is used
 */
public class ProduceHeroRepository {
    //DAOs
    private RestaurantDao restaurantDao;
    private OrderItemDao orderItemDao;

    public ProduceHeroRepository(Application application) {
        ProduceHeroDatabase database = ProduceHeroDatabase.getDatabase(application);
        restaurantDao = database.restaurantDao();
        orderItemDao = database.orderItemDao();
    }

    //Routes Activity
    private LiveData<List<String>> uniqueCityNames;
    public LiveData<List<String>> getUniqueCityNames() {
        if (uniqueCityNames == null) {
            uniqueCityNames = restaurantDao.getAllUniqueCities();
        }
        return uniqueCityNames;
    }

    //Route Plan Activity
    private LiveData<List<Restaurant>> restaurantList;
    public LiveData<List<Restaurant>> getRestaurantListForCity(String cityName) {
        if (restaurantList == null) {
            restaurantList = restaurantDao.getRestaurantListForCity(cityName);
        }
        return restaurantList;
    }

    //Order Activity
    private LiveData<List<OrderItem>> orderItemList;
    public LiveData<List<OrderItem>> getOrderItemListForRestaurant(int restaurantId) {
        if(orderItemList == null) {
            orderItemList = orderItemDao.getOrderItemsForRestaurant(restaurantId);
        }
        return orderItemList;
    }

    //Modify Item Activity
    public void updateOrderItem(OrderItem orderItem) {
        new UpdateOderItem(orderItemDao).execute(orderItem);
    }
    public void deleteOrderItem(OrderItem orderItem) {
        new DeleteOrderItem(orderItemDao).execute(orderItem);
    }

    //Database operations not returning a Live Data object must be run on a separate thread
    //Async task to update an Order Item
    private static class UpdateOderItem extends AsyncTask<OrderItem, Void, Void> {
        OrderItemDao orderItemDao;

        public UpdateOderItem(OrderItemDao orderItemDao) {
            this.orderItemDao = orderItemDao;
        }

        @Override
        protected Void doInBackground(OrderItem... orderItems) {
            orderItemDao.update(orderItems[0]);
            return null;
        }
    }

    //Database operations not returning a Live Data object must be run on a separate thread
    //Async task to delete an OrderItem
    private static class DeleteOrderItem extends AsyncTask<OrderItem, Void, Void> {
        OrderItemDao orderItemDao;

        public DeleteOrderItem(OrderItemDao orderItemDao) {
            this.orderItemDao = orderItemDao;
        }

        @Override
        protected Void doInBackground(OrderItem... orderItems) {
            orderItemDao.delete(orderItems[0]);
            return null;
        }
    }

    //Signature Activity
    public void updateRestaurant(Restaurant restaurant) {
        new UpdateRestaurantItem(restaurantDao).execute(restaurant);
    }

    private LiveData<Restaurant> restaurant;
    public LiveData<Restaurant> getRestaurant(int restaurantId) {
        if (restaurant == null) {
            restaurant = restaurantDao.getRestaurant(restaurantId);
        }
        return restaurant;
    }

    //Database operations not returning a Live Data object must be run on a separate thread
    //Async task to update a restaurant
    private static class UpdateRestaurantItem extends AsyncTask<Restaurant, Void, Void> {
        RestaurantDao restaurantDao;

        public UpdateRestaurantItem(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.update(restaurants[0]);
            return null;
        }
    }

}
