package com.example.t2shop.Response;

import com.example.t2shop.Model.Favorite;

import java.util.List;

public class ResponseFavorite {
    private List<Favorite> favoriteProduct;

    public ResponseFavorite() {
    }

    public List<Favorite> getFavorites() {
        return favoriteProduct;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favoriteProduct = favorites;
    }
}
