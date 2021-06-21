package com.example.t2shop.Response;

import com.example.t2shop.Model.Order;

import java.util.List;

public class ResponseOrder {
    private List<Order> orders;
    private List<Order> order;

    public ResponseOrder() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
