package com.example.t2shop.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public void insert(User... items);
    @Update
    public void update(User... items);
    @Delete
    public void delete(User item);
    @Query("SELECT * FROM users LIMIT 1")
    public User getItems();
    @Query("SELECT * FROM users WHERE user_id = :user_id")
    public User getItemById(int user_id);
}
