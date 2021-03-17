package com.example.t2shop.Model;

public class Promotion {
    private int promotion_id;
    private String promotion_name;
    private String start_date;
    private String end_date;
    private String promotion_infor;
    private String promotion_status;
    private String created_at;
    private String updated_at;

    public Promotion() {
    }

    public Promotion(int promotion_id, String promotion_name, String start_date, String end_date, String promotion_infor, String promotion_status, String created_at, String updated_at) {
        this.promotion_id = promotion_id;
        this.promotion_name = promotion_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.promotion_infor = promotion_infor;
        this.promotion_status = promotion_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getPromotion_name() {
        return promotion_name;
    }

    public void setPromotion_name(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPromotion_infor() {
        return promotion_infor;
    }

    public void setPromotion_infor(String promotion_infor) {
        this.promotion_infor = promotion_infor;
    }

    public String getPromotion_status() {
        return promotion_status;
    }

    public void setPromotion_status(String promotion_status) {
        this.promotion_status = promotion_status;
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
