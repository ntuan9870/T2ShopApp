package com.example.t2shop.Model;

public class Store {
    private int store_id;
    private String store_name;
    private String store_address;
    private String store_ward;
    private String store_district;
    private String store_status;
    private String admin_id;
    private String created_at;
    private String updated_at;

    public Store() {
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_ward() {
        return store_ward;
    }

    public void setStore_ward(String store_ward) {
        this.store_ward = store_ward;
    }

    public String getStore_district() {
        return store_district;
    }

    public void setStore_district(String store_district) {
        this.store_district = store_district;
    }

    public String getStore_status() {
        return store_status;
    }

    public void setStore_status(String store_status) {
        this.store_status = store_status;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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
}
