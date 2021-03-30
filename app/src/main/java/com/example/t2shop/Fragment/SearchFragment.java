package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.t2shop.Adapter.ProductAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseProduct;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment {

    private RecyclerView rc_search;
    private EditText edt_search;
    private TextView txt_nothing, txt_search;
    private ImageView img_cart_notification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rc_search = view.findViewById(R.id.rc_search);
        edt_search = view.findViewById(R.id.edt_search);
        txt_nothing = view.findViewById(R.id.txt_nothing);
        txt_search = view.findViewById(R.id.txt_search);
        img_cart_notification = view.findViewById(R.id.img_cart_notification);
        img_cart_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                CartFragment cartFragment = new CartFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, cartFragment);
                transaction.addToBackStack(CartFragment.TAG);
                transaction.commit();
            }
        });
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Common.compositeDisposable.add(Common.it2ShopAPI.search(edt_search.getText().toString().trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseProduct>() {
                            @Override
                            public void accept(ResponseProduct responseProduct) throws Exception {
                                if (responseProduct.getProducts().size()==0){
                                    txt_nothing.setVisibility(View.VISIBLE);
                                    txt_nothing.setText("Không có sản phẩm nào phù hợp với từ khóa \"" + edt_search.getText()+"\"");
                                    txt_search.setVisibility(View.INVISIBLE);
                                }else{
                                    txt_nothing.setVisibility(View.INVISIBLE);
                                    txt_search.setVisibility(View.VISIBLE);
                                }
                                ProductAdapter productAdapter = new ProductAdapter(getContext(), responseProduct.getProducts(), responseProduct.getPromotions(), responseProduct.getRatings());
                                rc_search.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                rc_search.setAdapter(productAdapter);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}