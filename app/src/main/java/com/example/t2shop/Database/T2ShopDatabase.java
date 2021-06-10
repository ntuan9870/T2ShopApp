package com.example.t2shop.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.DAO.ProductDAO;
import com.example.t2shop.DAO.PromotionDAO;
import com.example.t2shop.DAO.UserDAO;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.Model.User;

@Database(entities = {ItemCart.class, Product.class, Promotion.class, User.class}, version = 1)
public abstract class T2ShopDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "t2shop.db";
    private static T2ShopDatabase instance;
    public static synchronized T2ShopDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), T2ShopDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ItemCartDAO itemCartDAO();
    public abstract ProductDAO productDAO();
    public abstract UserDAO userDAO();
    public abstract PromotionDAO promotionDAO();
}
