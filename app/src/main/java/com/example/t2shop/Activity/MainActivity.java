package com.example.t2shop.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.t2shop.Adapter.MainViewPagerAdapter;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Fragment.CategoryFragment;
import com.example.t2shop.Fragment.HomeFragment;
import com.example.t2shop.Fragment.NotificationFragment;
import com.example.t2shop.Fragment.PersonalFragment;
import com.example.t2shop.Fragment.SearchFragment;
import com.example.t2shop.R;
import com.facebook.FacebookSdk;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{

    public static TabLayout tabLayout;
    private ViewPager viewPager;

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
    }

    private void AnhXa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}