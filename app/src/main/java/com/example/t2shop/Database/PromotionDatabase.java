package com.example.t2shop.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.DAO.ProductDAO;
import com.example.t2shop.DAO.PromotionDAO;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;

@Database(entities = {Promotion.class}, version = 1)
public abstract class PromotionDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "promotions.db";
    private static PromotionDatabase instance;
    public static synchronized PromotionDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PromotionDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract PromotionDAO promotionDAO();
}
