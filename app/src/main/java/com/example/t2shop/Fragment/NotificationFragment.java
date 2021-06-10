package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.VoucherAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotificationFragment extends Fragment {
    private RecyclerView rc_vouchers;
    private TextView check_notify;
    private ImageView img_cart_notification;
    private SwipeRefreshLayout refresh_notify;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        img_cart_notification = view.findViewById(R.id.img_cart_notification);
        refresh_notify = view.findViewById(R.id.refresh_notify);
        refresh_notify.setColorSchemeResources(R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_dark);
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
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rc_vouchers = view.findViewById(R.id.rc_vouchers);
        check_notify = view.findViewById(R.id.check_notify);
        fetchNotify();
        refresh_notify.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Common.isConnectedToInternet(getContext())){
                    fetchNotify();
                    refresh_notify.setRefreshing(false);
                }else{
                    Toast.makeText(getContext(), "Vui lòng kết nối INTERNET và thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void fetchNotify() {
        User user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        if (user!=null){
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
                            check_notify.setVisibility(View.INVISIBLE);
                            if (responseAllVoucher.getVouchers().size() == 0){
                                check_notify.setText("Bạn không có ưu đãi nào cả!");
                                check_notify.setTextColor(0xFFFF0000);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getContext(), "Có lỗi!"+ throwable.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }));
        }else{
            check_notify.setVisibility(View.VISIBLE);
            rc_vouchers.setVisibility(View.INVISIBLE);
            check_notify.setText("Vui lòng đăng nhập!");
            check_notify.setTextColor(0xFF001CBA);
            check_notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(4);
                    tab.select();
                }
            });
        }
    }
}