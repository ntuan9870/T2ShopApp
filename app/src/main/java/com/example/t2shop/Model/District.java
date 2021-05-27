package com.example.t2shop.Model;

import java.util.ArrayList;

public class District {
    private String id;
    private String name;
    private ArrayList<Ward> arrWards;

    public District() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ward> getArrWards() {
        return arrWards;
    }

    public void setArrWards(ArrayList<Ward> arrWards) {
        this.arrWards = arrWards;
    }
}
