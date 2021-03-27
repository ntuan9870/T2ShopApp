package com.example.t2shop.Model;

import com.example.t2shop.Common.Common;

public class Category {
    private int category_id;
    private String category_name;
    private String category_image;
    private String created_at;
    private String updated_at;

    public Category() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image.replace("http://localhost:8000/", Common.idServer);
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
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
