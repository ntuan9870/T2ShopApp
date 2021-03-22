package com.example.t2shop.Response;

import com.example.t2shop.Model.City;

import java.util.List;

public class ResponseCity {
    private List<City> LtsItem;

    public ResponseCity(List<City> ltsItem) {
        LtsItem = ltsItem;
    }

    public List<City> getLtsItem() {
        return LtsItem;
    }

    public void setLtsItem(List<City> ltsItem) {
        LtsItem = ltsItem;
    }
}
