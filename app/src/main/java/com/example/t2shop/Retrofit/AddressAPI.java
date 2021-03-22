package com.example.t2shop.Retrofit;

import com.example.t2shop.Response.ResponseCity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AddressAPI {
    @GET("city")
    Observable<ResponseCity> getAllCity();
//    @GET("city/")
//    Observable<ResponseCity> getAllCity();
//    @GET("city")
//    Observable<ResponseCity> getAllCity();
}
