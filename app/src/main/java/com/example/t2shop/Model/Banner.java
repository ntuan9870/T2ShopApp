package com.example.t2shop.Model;

import android.graphics.Bitmap;

public class Banner {
    public int id;
    public String title;
    private Bitmap bitmap;
    public Banner() {
    }

    public Banner(int id, String title, Bitmap bitmap) {
        this.id = id;
        this.title = title;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(String url) {
        this.bitmap = bitmap;
    }
}
