package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.t2shop.Adapter.HomeProductAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Model.DataProduct;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseRatingAll;
import com.example.t2shop.Retrofit.IT2ShopAPI;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailProductFragment extends Fragment {
    private AppBarLayout appBarLayoutDetailProduct;
    private ImageView img_menu_detail_product, img_shopping_cart_detail_product, img_home_detail_product, img_search_detail_product,
            img_back_detail_product, img_detail_product;
    private TextView txt_name_detail_product, txt_price_detail_product, txt_price_original_detail_product, txt_promotion_detail_product,
            txt_number_rating_detail_product, txt_category_detail_product, txt_warranty_detail_product, txt_accessories_detail_product;
    private TextView txt_conditional_detail_product, txt_description_detail_product, txt_rating_detail_product, txt_number_rating_detail_product2;
    private RatingBar rating_detail_product, rating_detail_product2;
    private ProgressBar rating_5_star, rating_4_star, rating_3_star, rating_2_star, rating_1_star;
    public static String TAG = DetailProductFragment.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        appBarLayoutDetailProduct = view.findViewById(R.id.appBarLayoutDetailProduct);
        img_menu_detail_product = view.findViewById(R.id.img_menu_detail_product);
        img_shopping_cart_detail_product = view.findViewById(R.id.img_shopping_cart_detail_product);
        img_home_detail_product = view.findViewById(R.id.img_home_detail_product);
        img_search_detail_product = view.findViewById(R.id.img_search_detail_product);
        img_back_detail_product = view.findViewById(R.id.img_back_detail_product);
        img_detail_product = view.findViewById(R.id.img_detail_product);
        txt_name_detail_product = view.findViewById(R.id.txt_name_detail_product);
        txt_price_detail_product = view.findViewById(R.id.txt_price_detail_product);
        txt_price_original_detail_product = view.findViewById(R.id.txt_price_original_detail_product);
        txt_promotion_detail_product = view.findViewById(R.id.txt_promotion_detail_product);
        txt_number_rating_detail_product = view.findViewById(R.id.txt_number_rating_detail_product);
        rating_detail_product = view.findViewById(R.id.rating_detail_product);
        txt_category_detail_product = view.findViewById(R.id.txt_category_detail_product);
        txt_warranty_detail_product = view.findViewById(R.id.txt_warranty_detail_product);
        txt_accessories_detail_product = view.findViewById(R.id.txt_accessories_detail_product);
        txt_conditional_detail_product = view.findViewById(R.id.txt_conditional_detail_product);
        txt_description_detail_product = view.findViewById(R.id.txt_description_detail_product);
        txt_rating_detail_product = view.findViewById(R.id.txt_rating_detail_product);
        txt_number_rating_detail_product2 = view.findViewById(R.id.txt_number_rating_detail_product2);
        rating_detail_product2 = view.findViewById(R.id.rating_detail_product2);
        rating_5_star = view.findViewById(R.id.rating_5_star);
        rating_4_star = view.findViewById(R.id.rating_4_star);
        rating_3_star = view.findViewById(R.id.rating_3_star);
        rating_2_star = view.findViewById(R.id.rating_2_star);
        rating_1_star = view.findViewById(R.id.rating_1_star);
        appBarLayoutDetailProduct.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                int o = scrollRange + verticalOffset;
                if (o>=0&&o<100){
                    img_menu_detail_product.setBackgroundColor(0x00000000);
                    img_menu_detail_product.setColorFilter(0xFF0021C8);
                    img_shopping_cart_detail_product.setBackgroundColor(0x00000000);
                    img_shopping_cart_detail_product.setColorFilter(0xFF0021C8);
                    img_home_detail_product.setBackgroundColor(0x00000000);
                    img_home_detail_product.setColorFilter(0xFF0021C8);
                    img_search_detail_product.setBackgroundColor(0x00000000);
                    img_search_detail_product.setColorFilter(0xFF0021C8);
                    img_back_detail_product.setBackgroundColor(0x00000000);
                    img_back_detail_product.setColorFilter(0xFF0021C8);
                }else{
                    img_menu_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_menu_detail_product.setColorFilter(0xFFFFFFFF);
                    img_shopping_cart_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_shopping_cart_detail_product.setColorFilter(0xFFFFFFFF);
                    img_home_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_home_detail_product.setColorFilter(0xFFFFFFFF);
                    img_search_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_search_detail_product.setColorFilter(0xFFFFFFFF);
                    img_back_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_back_detail_product.setColorFilter(0xFFFFFFFF);
                }
            }
        });
        img_back_detail_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Bundle extras = getArguments();
        Product product = (Product) extras.getSerializable("product");
        Promotion promotion = (Promotion) extras.getSerializable("promotion");
        String rating = extras.getString("rating");
        Glide.with(getContext()).load(product.getProduct_img()).into(img_detail_product);
        String dsc = product.getProduct_description().replace("<p>", "");
        dsc = dsc.replace("</p>", "");
        txt_name_detail_product.setText(product.getProduct_name() + " là " + dsc);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getProduct_price()-(product.getProduct_price()*Integer.parseInt(promotion.getPromotion_infor()))/100);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        txt_price_detail_product.setText(price+" "+vnd);
        txt_price_original_detail_product.setText(Html.fromHtml("<strike>"+formatter.format(product.getProduct_price())+vnd+"</strike>"));
        txt_promotion_detail_product.setText("- " + promotion.getPromotion_infor()+"%");
        rating_detail_product.setRating(Float.parseFloat(rating));
        rating_detail_product2.setRating(Float.parseFloat(rating));
        Common.compositeDisposable.add(Common.it2ShopAPI.getRatingAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseRatingAll>() {
                    @Override
                    public void accept(ResponseRatingAll responseRatingAll) throws Exception {
                        if (responseRatingAll.getArrcount().get(0)==0) {
                            txt_number_rating_detail_product.setText("(Chưa có đánh giá nào cả)");
                            txt_number_rating_detail_product2.setText("(Chưa có đánh giá nào cả)");
                        }
                        else {
                            txt_number_rating_detail_product.setText("("+responseRatingAll.getArrcount().get(0)+" đánh giá)");
                            txt_number_rating_detail_product2.setText("("+responseRatingAll.getArrcount().get(0)+" đánh giá)");
                            txt_rating_detail_product.setText(responseRatingAll.getArrcount().get(0));
                            rating_5_star.setProgress(responseRatingAll.getArrcount().get(1));
                            rating_5_star.setProgress(responseRatingAll.getArrcount().get(2));
                            rating_5_star.setProgress(responseRatingAll.getArrcount().get(3));
                            rating_5_star.setProgress(responseRatingAll.getArrcount().get(4));
                            rating_5_star.setProgress(responseRatingAll.getArrcount().get(5));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối INTERNET", Toast.LENGTH_SHORT).show();
                    }
                }));
        txt_category_detail_product.setText(product.getProduct_cate()+"");
        txt_warranty_detail_product.setText(product.getProduct_warranty());
        txt_accessories_detail_product.setText(product.getProduct_accessories());
        txt_conditional_detail_product.setText(product.getProduct_condition());
        txt_description_detail_product.setText(dsc);

        return view;
    }
}