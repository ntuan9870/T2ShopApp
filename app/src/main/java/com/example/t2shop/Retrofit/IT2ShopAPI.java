package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.DataProduct;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IT2ShopAPI {
    @GET("getNewProduct")
    Observable<DataProduct> getNewProduct();
    @GET("getFeaturedProduct")
    Observable<DataProduct> getFeaturedProduct();
}
