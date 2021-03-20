package com.example.t2shop.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.DAO.ProductDAO;
import com.example.t2shop.DAO.PromotionDAO;
import com.example.t2shop.Model.ItemCart;

@Database(entities = {ItemCart.class}, version = 1)
public abstract class ItemCartDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "itemcarts.db";
    private static ItemCartDatabase instance;
    public static synchronized ItemCartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ItemCartDatabase.class, DATABASE_NAME)
            .allowMainThreadQueries()
            .build();
        }
        return instance;
    }
    public abstract ItemCartDAO itemCartDAO();
}
