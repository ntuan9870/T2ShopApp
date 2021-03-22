package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.District;
import com.example.t2shop.Model.Ward;
import com.example.t2shop.Response.ResponseCity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AddressAPI {
    @GET("city")
    Observable<ResponseCity> getAllCity();
    @GET("city/{id}/district")
    Observable<List<District>> getAllDistrictInCity(@Path("id") int id);
    @GET("district/{id}/ward")
    Observable<List<Ward>> getAllWardInDistrict(@Path("id") int id);
}
