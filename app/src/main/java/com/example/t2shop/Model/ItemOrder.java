package com.example.t2shop.Model;

import com.example.t2shop.Common.Common;

public class ItemOrder {
    private int product_id;
    private String product_name;
    private String product_price;
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
    private int id;
    private int order_id;

    public ItemOrder() {
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

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_img() {
        return product_img.replace("http://localhost:8000/", Common.idServer);
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
        String dsc = product_description.replace("<p>","");
        dsc = dsc.replace("</p>", "");
        return dsc;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
