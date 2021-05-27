package com.example.t2shop.Model;

import java.util.ArrayList;

public class City {
    private String id;
    private String code;
    private String name;
    private ArrayList<District> arrDistrict;

    public City() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<District> getArrDistrict() {
        return arrDistrict;
    }

    public void setArrDistrict(ArrayList<District> arrDistrict) {
        this.arrDistrict = arrDistrict;
    }
}
