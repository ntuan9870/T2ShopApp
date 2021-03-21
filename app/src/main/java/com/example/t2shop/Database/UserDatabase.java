package com.example.t2shop.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t2shop.DAO.ProductDAO;
import com.example.t2shop.DAO.UserDAO;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "users.db";
    private static UserDatabase instance;
    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();
}
