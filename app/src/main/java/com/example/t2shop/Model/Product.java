package com.example.t2shop.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int product_id;
    private String product_name;
    private double product_price;
    private String product_img;
    private String product_warranty;
    private String product_accessories;
    private String product_condition;
    private int product_promotion;
    private String product_description;
    private String product_featured;
    private int product_amount;
    private int product_cate;
    private String created_at;
    private String updated_at;

    public Product() {
    }

    public Product(int product_id, String product_name, double product_price, String product_img, String product_warranty, String product_accessories, String product_condition, int product_promotion, String product_description, String product_featured, int product_amount, int product_cate, String created_at, String updated_at) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_img = product_img;
        this.product_warranty = product_warranty;
        this.product_accessories = product_accessories;
        this.product_condition = product_condition;
        this.product_promotion = product_promotion;
        this.product_description = product_description;
        this.product_featured = product_featured;
        this.product_amount = product_amount;
        this.product_cate = product_cate;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_img() {
        return product_img.replace("localhost:8000", "192.168.43.126");
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_warranty() {
        return product_warranty;
    }

    public void setProduct_warranty(String product_warranty) {
        this.product_warranty = product_warranty;
    }

    public String getProduct_accessories() {
        return product_accessories;
    }

    public void setProduct_accessories(String product_accessories) {
        this.product_accessories = product_accessories;
    }

    public String getProduct_condition() {
        return product_condition;
    }

    public void setProduct_condition(String product_condition) {
        this.product_condition = product_condition;
    }

    public int getProduct_promotion() {
        return product_promotion;
    }

    public void setProduct_promotion(int product_promotion) {
        this.product_promotion = product_promotion;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_featured() {
        return product_featured;
    }

    public void setProduct_featured(String product_featured) {
        this.product_featured = product_featured;
    }

    public int getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(int product_amount) {
        this.product_amount = product_amount;
    }

    public int getProduct_cate() {
        return product_cate;
    }

    public void setProduct_cate(int product_cate) {
        this.product_cate = product_cate;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
