package com.example.t2shop.Response;

import com.example.t2shop.Model.Store;

import java.util.ArrayList;

public class ResponseStore {
    private ArrayList<Store> stores = new ArrayList<>();

    public ResponseStore() {
    }

    public ArrayList<Store> getArrStores() {
        return stores;
    }

    public void setArrStores(ArrayList<Store> arrStores) {
        this.stores = arrStores;
    }
}
