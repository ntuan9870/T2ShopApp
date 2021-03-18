package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.DataProduct;
import com.example.t2shop.Response.ResponseRatingAll;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IT2ShopAPI {
    @GET("getNewProduct")
    Observable<DataProduct> getNewProduct();
    @GET("getFeaturedProduct")
    Observable<DataProduct> getFeaturedProduct();
    @GET("getratingall")
    Observable<ResponseRatingAll> getRatingAll();
//    @POST("login.php")
//    @FormUrlEncoded
//    Call<users> getTopRatedMovies(@Field("uemail") String uemail, @Field("upassword") String upassword);
}
