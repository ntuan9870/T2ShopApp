package com.example.t2shop.Model;

public class Favorite {
    private int FP_id;
    private int user_id;
    private int product_id;
    private int FD_status;
    private String created_at;
    private String updated_at;

    public Favorite() {
    }

    public Favorite(int FP_id, int user_id, int product_id, int FD_status, String created_at, String updated_at) {
        this.FP_id = FP_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.FD_status = FD_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFP_id() {
        return FP_id;
    }

    public void setFP_id(int FP_id) {
        this.FP_id = FP_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getFD_status() {
        return FD_status;
    }

    public void setFD_status(int FD_status) {
        this.FD_status = FD_status;
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
