package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.DataProduct;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IT2ShopAPI {
    @GET("getNewProduct")
    Observable<DataProduct> getDataProduct();
//    @GET("shopping/v2/widgets/quick_link")
//    Observable<DataQuickLink> getDataQuickLink();
//    @GET("v2/widget/deals/hot")
//    Observable<Sale> getSale();
}
