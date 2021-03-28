package com.example.t2shop.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.t2shop.Model.ItemCart;

import java.util.List;

@Dao
public interface ItemCartDAO {
    @Insert
    void insert(ItemCart... items);
    @Update
    void update(ItemCart... items);
    @Delete
    void delete(ItemCart item);
    @Query("SELECT * FROM itemcarts")
    List<ItemCart> getItems();
    @Query("SELECT * FROM itemcarts WHERE id = :id")
    ItemCart getItemById(Long id);
    @Query("SELECT SUM(amount) FROM itemcarts")
    int getAmount();
    @Query("DELETE FROM itemcarts")
    void deleteAll();
}
