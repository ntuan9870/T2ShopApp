package com.example.t2shop.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itemcarts")
public class ItemCart {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private int product_id;
    private String product_name;
    private double product_price;
    private int product_amount;
    private String product_img;
    private String product_description;
    private String promotion_infor;
    private int amount;

    public ItemCart() {
    }

    public ItemCart(@NonNull Long id, int product_id, String product_name, double product_price, String product_img, String product_description, String promotion_infor, int amount) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_img = product_img;
        this.product_description = product_description;
        this.promotion_infor = promotion_infor;
        this.amount = amount;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getPromotion_infor() {
        return promotion_infor;
    }

    public void setPromotion_infor(String promotion_infor) {
        this.promotion_infor = promotion_infor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(int product_amount) {
        this.product_amount = product_amount;
    }
}
