package com.example.t2shop.Response;

import com.example.t2shop.Model.Category;

import java.util.List;

public class ResponseCategory {
    private List<Category> categories;

    public ResponseCategory() {
    }

    public ResponseCategory(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
