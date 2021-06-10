package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.t2shop.Adapter.ProductAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseProduct;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteFragment extends Fragment {

    public static final String TAG = Fragment.class.getName();
    private RelativeLayout rl_favorite, rl_nothing;
    private RecyclerView rv_favorite;
    private ImageView btn_exit_favorite;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rl_favorite = view.findViewById(R.id.rl_favorite);
        rl_nothing = view.findViewById(R.id.rl_nothing);
        rv_favorite = view.findViewById(R.id.rv_favorite);
        btn_exit_favorite = view.findViewById(R.id.btn_exit_favorite);
        rl_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        if (user!=null){
            fetchFavoriteProduct();
        }
        btn_exit_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
    private void fetchFavoriteProduct() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getFavoriteProduct(user.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseProduct>() {
                    @Override
                    public void accept(ResponseProduct responseProduct) throws Exception {
                        if (responseProduct.getProducts().size()!=0){
                            ProductAdapter productAdapter = new ProductAdapter(getContext(), responseProduct.getProducts(), responseProduct.getPromotions(), responseProduct.getRatings());
                            rv_favorite.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            rv_favorite.setAdapter(productAdapter);
                            rl_nothing.setVisibility(View.INVISIBLE);
                        }else{
                            rl_nothing.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}