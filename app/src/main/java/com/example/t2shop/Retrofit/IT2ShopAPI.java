package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.DataProduct;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseLogin;
import com.example.t2shop.Response.ResponseRatingAll;
import com.example.t2shop.Response.ResponseSuccess;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    @POST("auth/login")
    @FormUrlEncoded
    Observable<ResponseLogin> login(@Field("user_email") String user_email, @Field("user_password") String user_password);
    @POST("auth/register")
    @FormUrlEncoded
    Observable<ResponseSuccess> register(@Field("user_name") String user_name, @Field("user_password") String user_password, @Field("user_email") String user_email, @Field("user_phone") String user_phone);
    @POST("order/add")
    @FormUrlEncoded
    Observable<ResponseSuccess> addOrder(@Field("cart") String cart, @Field("user_id") String user_id, @Field("user_name_receive") String user_name_receive,
                         @Field("user_phone") String user_phone, @Field("user_message") String user_message, @Field("user_address") String user_address,
                         @Field("total") String total, @Field("form") String form, @Field("select_voucher") String select_voucher);
}
