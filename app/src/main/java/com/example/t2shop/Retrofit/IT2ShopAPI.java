package com.example.t2shop.Retrofit;

import com.example.t2shop.Model.Category;
import com.example.t2shop.Model.Comment;
import com.example.t2shop.Response.ResponseChangeStore;
import com.example.t2shop.Response.ResponseComment;
import com.example.t2shop.Response.ResponseFavorite;
import com.example.t2shop.Response.ResponseOrder;
import com.example.t2shop.Response.ResponseOrderItem;
import com.example.t2shop.Response.ResponseProduct;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseCategory;
import com.example.t2shop.Response.ResponseLogin;
import com.example.t2shop.Response.ResponseRatingAll;
import com.example.t2shop.Response.ResponseMessage;
import com.example.t2shop.Response.ResponseStore;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IT2ShopAPI {
    @GET("getNewProduct")
    Observable<ResponseProduct> getNewProduct();
    @GET("getFeaturedProduct")
    Observable<ResponseProduct> getFeaturedProduct();
    @POST("getratingall")
    @FormUrlEncoded
    Observable<ResponseRatingAll> getRatingAll(@Field("product_id")int product_id);
    @POST("danhgia")
    @FormUrlEncoded
    Observable<ResponseMessage> addRating(@Field("user_id")int user_id, @Field("product_id")int product_id, @Field("rating")int rating);
    @POST("getratingelement")
    @FormUrlEncoded
    Observable<ResponseMessage> getRating(@Field("user_id")int user_id, @Field("product_id")int product_id);
    @POST("addComment")
    @FormUrlEncoded
    Observable<ResponseMessage> addComment(@Field("user_id")int user_id, @Field("product_id")int product_id, @Field("comment_content")String comment_content);
    @POST("getComment")
    @FormUrlEncoded
    Observable<ResponseComment> getComment(@Field("product_id")int product_id);
    @POST("removeComment")
    @FormUrlEncoded
    Observable<ResponseMessage> removeComment(@Field("comment_id")int comment_id);
    @POST("voucher/getallvoucherforuser")
    @FormUrlEncoded
    Observable<ResponseAllVoucher> getAllVoucher(@Field("user_id") int user_id);
    @POST("auth/login")
    @FormUrlEncoded
    Observable<ResponseLogin> login(@Field("user_email") String user_email, @Field("user_password") String user_password);
    @POST("auth/checkemailSocial")
    @FormUrlEncoded
    Observable<ResponseMessage> checkEmailSocial(@Field("email") String email, @Field("name") String name);
    @POST("auth/register")
    @FormUrlEncoded
    Observable<ResponseMessage> register(@Field("user_name") String user_name, @Field("user_password") String user_password, @Field("user_email") String user_email, @Field("user_phone") String user_phone);
    @POST("order/add")
    @FormUrlEncoded
    Observable<ResponseMessage> addOrder(@Field("cart") String cart, @Field("user_id") String user_id, @Field("user_name_receive") String user_name_receive,
                                         @Field("user_phone") String user_phone, @Field("user_message") String user_message, @Field("user_address") String user_address,
                                         @Field("total") String total, @Field("form") String form, @Field("select_voucher") String select_voucher,
                                         @Field("store_id") String store_id);
    @GET("category/show")
    Observable<ResponseCategory> getAllCategory();
    @POST("category/getEdit")
    @FormUrlEncoded
    Observable<Category> getEditCategory(@Field("id") int id);
    @POST("show")
    @FormUrlEncoded
    Observable<ResponseProduct> getCategoryByID(@Field("id") int id, @Field("sl_featured") int sl_featured, @Field("sl_filter") int sl_filter);
    @POST("show")
    @FormUrlEncoded
    Observable<ResponseProduct> search(@Field("key")String key);
    @POST("order/show")
    @FormUrlEncoded
    Observable<ResponseOrder>  getAllOrderByUserId(@Field("user_id") int user_id);
    @POST("order/show/detail")
    @FormUrlEncoded
    Observable<ResponseOrderItem>  getDetailOrder(@Field("order_id") int order_id);
    @POST("users/postEdit")
    @FormUrlEncoded
    Observable<ResponseMessage> changeInformation(@Field("user_id") int user_id, @Field("user_name")String user_name, @Field("user_email") String user_email, @Field("user_phone") String user_phone);

    @POST("auth/checksameemail")
    @FormUrlEncoded
    Observable<ResponseMessage> checkSameEmail(@Field("useremail")String useremail);
    @POST("auth/sendnewpass")
    @FormUrlEncoded
    Observable<ResponseMessage> changePassword(@Field("user_password")String user_password,@Field("email")String email);
    @POST("auth/sendemail")
    @FormUrlEncoded
    Observable<ResponseMessage> sendEmail(@Field("email")String email);
    @POST("auth/getcode")
    @FormUrlEncoded
    Observable<ResponseMessage> getCode(@Field("email")String email);
    @POST("getFavoriteProduct")
    @FormUrlEncoded
    Observable<ResponseProduct> getFavoriteProduct(@Field("user_id") int user_id);
    @POST("recommened/getrecommened")
    @FormUrlEncoded
    Observable<ResponseProduct> getRecommendProduct(@Field("user_id") int user_id);
    @POST("pushFavoriteProduct")
    @FormUrlEncoded
    Observable<ResponseMessage> addFavorite(@Field("user_id") int user_id, @Field("product_id") int product_id);
    @POST("getFavorite")
    @FormUrlEncoded
    Observable<ResponseFavorite> getFavorite(@Field("user_id") int user_id, @Field("product_id") int product_id);
    @POST("removeFavoriteProduct")
    @FormUrlEncoded
    Observable<ResponseMessage> removeFavorite(@Field("FP_id") int FP_id);
    @POST("recommened/add")
    @FormUrlEncoded
    Observable<ResponseMessage> addRecommend(@Field("user_id") int user_id, @Field("product_id") String product_id);
    @POST("checkAcceptComment")
    @FormUrlEncoded
    Observable<ResponseOrder> checkAllowRating(@Field("user_id") int user_id, @Field("product_id") int product_id);
    @GET("store/showStore")
    Observable<ResponseStore> getAllStore();
    @POST("cart/checkChangeStore")
    @FormUrlEncoded
    Observable<ResponseChangeStore> checkChangeStore(@Field("cart") String json_cart, @Field("store_id") int store_id);
}
