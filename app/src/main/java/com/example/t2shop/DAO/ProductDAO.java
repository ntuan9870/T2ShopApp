package com.example.t2shop.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    public void insert(Product... items);
    @Update
    public void update(Product... items);
    @Delete
    public void delete(Product item);
    @Query("SELECT * FROM products")
    public List<Product> getItems();
    @Query("SELECT * FROM products WHERE product_id = :product_id")
    public Product getItemById(int product_id);
}
