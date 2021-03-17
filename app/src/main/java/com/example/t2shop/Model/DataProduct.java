package com.example.t2shop.Model;

import java.util.ArrayList;
import java.util.List;

public class DataProduct {
    public List<Product> products = new ArrayList<>();
    public List<Promotion> promotions = new ArrayList<>();
    public List<String> ratings = new ArrayList<>();

    public DataProduct() {
    }

    public DataProduct(List<Product> products, List<Promotion> promotions, List<String> ratings) {
        this.products = products;
        this.promotions = promotions;
        this.ratings = ratings;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> data) {
        this.products = products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }
}
