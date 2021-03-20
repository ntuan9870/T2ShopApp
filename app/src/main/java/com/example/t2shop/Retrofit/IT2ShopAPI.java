package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.DataProduct;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseRatingAll;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IT2ShopAPI {
    @GET("getNewProduct")
    Observable<DataProduct> getNewProduct();
    @GET("getFeaturedProduct")
    Observable<DataProduct> getFeaturedProduct();
    @GET("getratingall")
    Observable<ResponseRatingAll> getRatingAll();
    @POST("voucher/getallvoucherforuser")
    @FormUrlEncoded
    Observable<ResponseAllVoucher> getAllVoucher(@Field("user_id") int user_id);
//    @POST("login.php")
//    @FormUrlEncoded
//    Call<users> getTopRatedMovies(@Field("uemail") String uemail, @Field("upassword") String upassword);
}
