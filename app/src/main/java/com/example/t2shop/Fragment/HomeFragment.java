package com.example.t2shop.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.t2shop.Adapter.BannerAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Model.Banner;
import com.example.t2shop.R;
import com.example.t2shop.Retrofit.IT2ShopAPI;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    IT2ShopAPI it2ShopAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    Handler handler;
    Runnable runnable;
    private int currentItem;
    RecyclerView recy_Quick_Link_1, recy_Quick_Link_2, recy_sale;
    SwipeRefreshLayout refresh_home;
    Thread t1;
    private ArrayList<Bitmap> arrBmBanners = new ArrayList<>();

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner1));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner2));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner3));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner4));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner5));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner6));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner7));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner8));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner9));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner10));
        arrBmBanners.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.banner11));

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //Init
        it2ShopAPI = Common.getAPI();
        viewPager = view.findViewById(R.id.myViewPagerBanner);
        circleIndicator = view.findViewById(R.id.indicator_banner);
        recy_Quick_Link_1 = view.findViewById(R.id.recy_quick_link_1);
        recy_Quick_Link_2 = view.findViewById(R.id.recy_quick_link_2);
        recy_sale = view.findViewById(R.id.recy_sale);
        refresh_home = view.findViewById(R.id.refresh_home);
        refresh_home.setColorSchemeResources(R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_dark);
        refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Common.isConnectedToInternet(getContext())){
                    fetchBanner();
                }else{
                    Toast.makeText(getContext(), "Cannot connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refresh_home.post(new Runnable() {
            @Override
            public void run() {
                if(Common.isConnectedToInternet(getContext())){
                    fetchBanner();
                }else{
                    Toast.makeText(getContext(), "Cannot connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void fetchBanner() {
        ArrayList<Banner> banners = new ArrayList<>();
        banners.add(new Banner(0, "Quảng cáo số 1", arrBmBanners.get(0)));
        banners.add(new Banner(1, "Quảng cáo số 2", arrBmBanners.get(1)));
        banners.add(new Banner(2, "Quảng cáo số 3", arrBmBanners.get(2)));
        banners.add(new Banner(3, "Quảng cáo số 4", arrBmBanners.get(3)));
        banners.add(new Banner(4, "Quảng cáo số 5", arrBmBanners.get(4)));
        banners.add(new Banner(5, "Quảng cáo số 6", arrBmBanners.get(5)));
        banners.add(new Banner(6, "Quảng cáo số 7", arrBmBanners.get(6)));
        banners.add(new Banner(7, "Quảng cáo số 8", arrBmBanners.get(7)));
        banners.add(new Banner(8, "Quảng cáo số 9", arrBmBanners.get(8)));
        banners.add(new Banner(9, "Quảng cáo số 10", arrBmBanners.get(9)));
        banners.add(new Banner(10, "Quảng cáo số 11", arrBmBanners.get(10)));
        BannerAdapter bannerAdapter = new BannerAdapter(banners, getContext());
        viewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(viewPager);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if(currentItem>=viewPager.getAdapter().getCount()){
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }
}