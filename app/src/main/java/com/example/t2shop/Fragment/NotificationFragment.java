package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.ProductAdapter;
import com.example.t2shop.Adapter.VoucherAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseProduct;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotificationFragment extends Fragment {
    private RecyclerView rc_vouchers;
    private TextView check_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rc_vouchers = view.findViewById(R.id.rc_vouchers);
        check_login = view.findViewById(R.id.check_login);
        User user = UserDatabase.getInstance(getContext()).userDAO().getItems();
        if (user!=null){
            check_login.setVisibility(View.INVISIBLE);
            rc_vouchers.setVisibility(View.VISIBLE);
            Common.compositeDisposable.add(Common.it2ShopAPI.getAllVoucher(user.getUser_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseAllVoucher>() {
                        @Override
                        public void accept(ResponseAllVoucher responseAllVoucher) throws Exception {
                            VoucherAdapter voucherAdapter = new VoucherAdapter(getContext(), responseAllVoucher.getVouchers());
                            rc_vouchers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            rc_vouchers.setAdapter(voucherAdapter);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                        }
                    }));
        }else{
            check_login.setVisibility(View.VISIBLE);
            rc_vouchers.setVisibility(View.INVISIBLE);
            check_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(4);
                    tab.select();
                }
            });
        }
        return view;
    }
}