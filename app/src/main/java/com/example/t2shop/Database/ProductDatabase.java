package com.example.t2shop.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.DAO.ProductDAO;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "products.db";
    private static ProductDatabase instance;
    public static synchronized ProductDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ProductDAO productDAO();
}