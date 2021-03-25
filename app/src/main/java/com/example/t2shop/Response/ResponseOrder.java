package com.example.t2shop.Response;

import com.example.t2shop.Model.Order;

import java.util.List;

public class ResponseOrder {
    private List<Order> orders;

    public ResponseOrder() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
