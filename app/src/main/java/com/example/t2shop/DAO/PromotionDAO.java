package com.example.t2shop.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;

import java.util.List;

@Dao
public interface PromotionDAO {
    @Insert
    public void insert(Promotion... items);
    @Update
    public void update(Promotion... items);
    @Delete
    public void delete(Promotion item);
    @Query("SELECT * FROM promotions")
    public List<Promotion> getItems();
    @Query("SELECT * FROM promotions WHERE promotion_id = :promotion_id")
    public Promotion getItemById(int promotion_id);
}