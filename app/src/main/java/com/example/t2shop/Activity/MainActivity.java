package com.example.t2shop.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.t2shop.Adapter.ChatBotAdapter;
import com.example.t2shop.Adapter.MainViewPagerAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Fragment.CategoryFragment;
import com.example.t2shop.Fragment.HomeFragment;
import com.example.t2shop.Fragment.NotificationFragment;
import com.example.t2shop.Fragment.PersonalFragment;
import com.example.t2shop.Fragment.SearchFragment;
import com.example.t2shop.Model.User;
import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.facebook.FacebookSdk;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    public static TabLayout tabLayout;
    private ViewPager viewPager;
    private Handler handler = new Handler();
    private Runnable r;
    private int oldSize = 1000;
    private User user;
    private List<Voucher> arrOldVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        AnhXa();
        init();
    }
    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new HomeFragment(), "Trang chủ");
        mainViewPagerAdapter.addFragment(new CategoryFragment(), "Danh mục");
        mainViewPagerAdapter.addFragment(new SearchFragment(), "Tìm kiểm");
        mainViewPagerAdapter.addFragment(new NotificationFragment(), "Thông báo");
        mainViewPagerAdapter.addFragment(new PersonalFragment(), "Cá nhân");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(0).getIcon().setTint(Color.BLUE);
        }
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_danhmuc_all_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_search_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_notifications_24);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_baseline_account_circle_24);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tabLayout.getTabAt(tab.getPosition()).getIcon().setTint(Color.BLUE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tabLayout.getTabAt(tab.getPosition()).getIcon().setTint(Color.BLACK);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        user = T2ShopDatabase.getInstance(this).userDAO().getItems();
        r = new Runnable() {
            @Override
            public void run() {
                if (user!=null) {
                    getMessages();
                    handler.postDelayed(r, 3000);
                }
            }
        };
        handler.postDelayed(r, 3000);
    }

    private void getMessages() {
        ArrayList<String> arrMessages = new ArrayList<>();
        arrMessages.add("Xin chào bạn, bạn khỏe không?");
        Common.compositeDisposable.add(Common.it2ShopAPI.getAllVoucher(user.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseAllVoucher>() {
                    @Override
                    public void accept(ResponseAllVoucher responseAllVoucher) throws Exception {
                        List<Voucher> voucherList = responseAllVoucher.getVouchers();
                        for (int i = 0; i < responseAllVoucher.getVouchers().size(); i++){
                            arrMessages.add("Bạn được tặng " + responseAllVoucher.getVouchers().get(i).getVoucher_name() + " \nHết hạn vào ngày " +
                                    responseAllVoucher.getVouchers().get(i).getVoucher_end() + "\nHãy mua hàng ngay để không lỡ cơ hội!");
                        }
                        if(responseAllVoucher!=null){
                            if(arrMessages.size()>oldSize){
                                pushNotify(arrMessages.get(arrMessages.size()-1));
                            }
                            if(arrOldVoucher!=null){
                                for (int i = 0; i < responseAllVoucher.getVouchers().size(); i++){
                                    if(responseAllVoucher.getVouchers().get(i).getAmount_voucher() > arrOldVoucher.get(i).getAmount_voucher()){
                                        pushNotify("Bạn vừa nhận được thêm " + (responseAllVoucher.getVouchers().get(i).getAmount_voucher() - arrOldVoucher.get(i).getAmount_voucher()) +
                                                " phiếu tặng " +responseAllVoucher.getVouchers().get(i).getVoucher_name());
                                    }
                                }
                            }
                            arrOldVoucher = responseAllVoucher.getVouchers();
                        }
                        oldSize = arrMessages.size();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getContext(), "Opp, lỗi kìa!", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void pushNotify(String s) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.t2shop);
        Notification notification = new Notification.Builder(getBaseContext())
                .setContentTitle("Bạn nhận được Voucher!")
                .setContentText(s)
                .setSmallIcon(R.drawable.bell)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notification!=null){
            notificationManager.notify(getNotificationID(),notification);
        }
    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }

    private void AnhXa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}