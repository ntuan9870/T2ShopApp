package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.t2shop.Adapter.CategoryAdapter;
import com.example.t2shop.Adapter.ProductAdapter;
import com.example.t2shop.Adapter.RecyclerItemClickListener;
import com.example.t2shop.Common.Common;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseCategory;
import com.example.t2shop.Response.ResponseProduct;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class CategoryFragment extends Fragment {

    private RecyclerView rc_category, rc_category_detail;
    private TextView txt_category_name, txt_nothing;
    private Spinner filter_price, filter_featured;
    private int sl_pr = 0, sl_fe = 0, id_ca = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rc_category = view.findViewById(R.id.rc_category);
        rc_category_detail = view.findViewById(R.id.rc_category_detail);
        txt_category_name = view.findViewById(R.id.txt_category_name);
        filter_price = view.findViewById(R.id.filter_price);
        filter_featured = view.findViewById(R.id.filter_featured);
        txt_nothing = view.findViewById(R.id.txt_nothing);
        ArrayList<String> arrOptionPrices = new ArrayList<>();
        arrOptionPrices.add("Giá tăng dần");
        arrOptionPrices.add("Giá giảm dần");
        arrOptionPrices.add("Mới nhất");
        arrOptionPrices.add("Cũ nhất");
        ArrayList<String> arrOptionFeatures = new ArrayList<>();
        arrOptionFeatures.add("Nổi bật");
        arrOptionFeatures.add("Không nổi bật");
        ArrayAdapter<String> adapterPrices = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptionPrices);
        adapterPrices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_price.setAdapter(adapterPrices);
        filter_price.setSelection(0);
        ArrayAdapter<String> adapterFeatures = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptionFeatures);
        adapterFeatures.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_featured.setAdapter(adapterFeatures);
        filter_featured.setSelection(0);
        filter_featured.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sl_fe = position;
                fetchProductDetail(id_ca, sl_fe, sl_pr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filter_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sl_pr = position;
                fetchProductDetail(id_ca, sl_fe, sl_pr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Common.compositeDisposable.add(Common.it2ShopAPI.getAllCategory()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseCategory>() {
            @Override
            public void accept(ResponseCategory responseCategory) throws Exception {
                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), responseCategory.getCategories());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 6);
                rc_category.setLayoutManager(layoutManager);
                rc_category.setAdapter(categoryAdapter);
                rc_category.addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(), rc_category ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                id_ca = categoryAdapter.getArrCategory().get(position).getCategory_id();
                                fetchProductDetail(id_ca, sl_fe, sl_pr);
                                txt_category_name.setText("Sản phẩm của "+categoryAdapter.getArrCategory().get(position).getCategory_name());
                            }

                            @Override public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
        fetchProductDetail(id_ca, sl_fe, sl_pr);
        return view;
    }

    private void fetchProductDetail(int id, int sl_featured, int sl_price) {
        Common.compositeDisposable.add(Common.it2ShopAPI.getCategoryByID(id, sl_featured, sl_price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseProduct>() {
                    @Override
                    public void accept(ResponseProduct responseProduct) throws Exception {
                        if (responseProduct.getProducts().size()==0){
                            txt_nothing.setVisibility(View.VISIBLE);
                        }else{
                            txt_nothing.setVisibility(View.INVISIBLE);
                        }
                        ProductAdapter productAdapter = new ProductAdapter(getContext(), responseProduct.getProducts(), responseProduct.getPromotions(), responseProduct.getRatings());
                        rc_category_detail.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        rc_category_detail.setAdapter(productAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}