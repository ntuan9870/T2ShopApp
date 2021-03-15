package com.example.t2shop.Model;

import java.util.ArrayList;
import java.util.List;

public class DataProduct {
    public List<Product> products = new ArrayList<>();

    public DataProduct() {
    }

    public DataProduct(List<Product> products) {
        this.products = products;
    }

    public List<Product> getData() {
        return products;
    }

    public void setData(List<Product> data) {
        this.products = products;
    }
}
