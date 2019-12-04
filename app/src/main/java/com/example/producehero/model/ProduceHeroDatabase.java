package com.example.producehero.model;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.producehero.model.entity.OrderItem;
import com.example.producehero.model.entity.Restaurant;

import java.util.Random;

/**
 * Room Database class
 * Abstract class that extends RoomDatabase.
 * Annotated so database knows which entities/tables are in it
 */
@Database(entities = {Restaurant.class, OrderItem.class}, version = 1)
public abstract class ProduceHeroDatabase extends RoomDatabase {
    private static final String DATABASE_NAME= "produce_hero_database";

    //Abstract method
    //Retrieval of the DAOs, Room auto generates these based on return types
    public abstract RestaurantDao restaurantDao();
    public abstract OrderItemDao orderItemDao();

    //Database singleton, as we should only ever have 1 database
    private static volatile ProduceHeroDatabase produceHeroDatabase;

    public static synchronized ProduceHeroDatabase getDatabase(final Context context) {
        if (produceHeroDatabase == null) {
            produceHeroDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ProduceHeroDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration() //If there is an upgrade in the database, delete and recreate
                    .addCallback(roomCallback) //Callback once the database is done creation, for populating initial data
                    .build();
        }
        return produceHeroDatabase;
    }

    //Room callback to populate initial data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //Non Live Data operations need to be run in their own thread as Room doesnt allow DB operations on main thread
            new PopulateDbAsyncTask(produceHeroDatabase).execute();
        }
    };

    //Async taks for populating database
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private RestaurantDao restaurantDao;
        private OrderItemDao orderItemDao;

        private PopulateDbAsyncTask(ProduceHeroDatabase database) {
            this.restaurantDao = database.restaurantDao();
            this.orderItemDao = database.orderItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Populate Database
            int pili_pili_id = 1;
            int pizza_hut_id = 2;
            int the_works_ottawa_id = 3;
            int the_works_barrie_id = 4;
            int casa_mia_id = 5;
            int atomica_kitchen_id = 6;

            restaurantDao.insert(new Restaurant(pili_pili_id,"Pili Pili", "205 Dalhousie St, Ottawa", "Ottawa", ""));
            restaurantDao.insert(new Restaurant(pizza_hut_id,"Pizza Hut", "1518 Greenbank Rd, Ottawa", "Ottawa", ""));
            restaurantDao.insert(new Restaurant(the_works_ottawa_id,"The Works", "3500 Fallowfield Rd, Ottawa", "Ottawa", ""));
            restaurantDao.insert(new Restaurant(the_works_barrie_id,"The Works", "137 Dunlop St E, Barrie", "Barrie", ""));
            restaurantDao.insert(new Restaurant(casa_mia_id,"Casa Mia", "88 Dunlop St W, Barrie", "Barrie", ""));
            restaurantDao.insert(new Restaurant(atomica_kitchen_id,"Atomica Kitchen", "71 Brock St, Kingston", "Kingston", ""));

            for (int i = 1; i < 15 ; i++) {
                orderItemDao.insert(new OrderItem(pili_pili_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }
            for (int i = 1; i < 10 ; i++) {
                orderItemDao.insert(new OrderItem(pizza_hut_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }
            for (int i = 1; i < 5 ; i++) {
                orderItemDao.insert(new OrderItem(the_works_ottawa_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }
            for (int i = 1; i < 24 ; i++) {
                orderItemDao.insert(new OrderItem(the_works_barrie_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }
            for (int i = 1; i < 11 ; i++) {
                orderItemDao.insert(new OrderItem(casa_mia_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }
            for (int i = 1; i < 7 ; i++) {
                orderItemDao.insert(new OrderItem(atomica_kitchen_id, "Order Item " + i,new Random().nextInt(22) + 2, 1 ));
            }

            return null;
        }
    }
}
