package com.example.t2shop.Response;

import com.example.t2shop.Model.ItemOrder;

import java.util.List;

public class ResponseOrderItem {
    private List<ItemOrder> orderitems;

    public ResponseOrderItem() {
    }

    public List<ItemOrder> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<ItemOrder> orderitems) {
        this.orderitems = orderitems;
    }
}
