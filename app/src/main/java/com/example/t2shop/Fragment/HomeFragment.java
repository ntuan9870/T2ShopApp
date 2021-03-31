package com.example.t2shop.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.BannerAdapter;
import com.example.t2shop.Adapter.ProductAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Model.Banner;
import com.example.t2shop.Response.ResponseProduct;
import com.example.t2shop.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    Handler handler;
    Runnable runnable;
    private int currentItem;
    SwipeRefreshLayout refresh_home;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    Thread t1;
    private ArrayList<Bitmap> arrBmBanners = new ArrayList<>();
    private RecyclerView rv_new_product, rv_featured_product;
    private EditText edt_search;
    private LinearLayout ln_search;
    private ImageView img_shopping_cart;
    @Override
    public void onStop() {
        Common.compositeDisposable.clear();
        super.onStop();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Common.compositeDisposable = new CompositeDisposable();

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
        RelativeLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //Init
        Common.it2ShopAPI = Common.getAPI();
        viewPager = view.findViewById(R.id.myViewPagerBanner);
        circleIndicator = view.findViewById(R.id.indicator_banner);
        rv_new_product = view.findViewById(R.id.rv_new_product);
        rv_featured_product = view.findViewById(R.id.rv_featured_product);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        edt_search = view.findViewById(R.id.edt_search);
        ln_search = view.findViewById(R.id.ln_search);
        img_shopping_cart = view.findViewById(R.id.img_shopping_cart);
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(2);
                tab.select();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                int o = scrollRange + verticalOffset;
                ViewGroup.LayoutParams params = ln_search.getLayoutParams();
                if (o>=0&&o<100){
                    params.width = 700*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }else if (o>=100&&o<110){
                    params.width = 750*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }else if (o>=110&&o<120){
                    params.width = 800*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }
                else if (o>=120&&o<130){
                    params.width = 850*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }
                else if (o>=130&&o<140){
                    params.width = 900*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }
                else if (o>=140&&o<=150){
                    params.width = 950*Constants.SCREEN_WIDTH/1080;
                    ln_search.setLayoutParams(params);
                }
            }
        });
        refresh_home = view.findViewById(R.id.refresh_home);
        refresh_home.setColorSchemeResources(R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_dark);
        fetchBanner();
        fetchNewProduct();
        fetchFeatured();
        refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Common.isConnectedToInternet(getContext())){
                    fetchNewProduct();
                    fetchFeatured();
                    refresh_home.setRefreshing(false);
                }else{
                    Toast.makeText(getContext(), "Vui lòng kết nối INTERNET và thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_shopping_cart.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }

    private void fetchFeatured() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getFeaturedProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseProduct>() {
                    @Override
                    public void accept(ResponseProduct responseProduct) throws Exception {
                        ProductAdapter productAdapter = new ProductAdapter(getContext(), responseProduct.getProducts(), responseProduct.getPromotions(), responseProduct.getRatings());
                        rv_featured_product.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        rv_featured_product.setAdapter(productAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void fetchNewProduct() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getNewProduct()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseProduct>() {
            @Override
            public void accept(ResponseProduct responseProduct) throws Exception {
                ProductAdapter productAdapter = new ProductAdapter(getContext(), responseProduct.getProducts(), responseProduct.getPromotions(), responseProduct.getRatings());
                rv_new_product.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                rv_new_product.setAdapter(productAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        }));
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
                handler.postDelayed(runnable, 3500);
            }
        };
        handler.postDelayed(runnable, 3500);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.serch_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tim kiem o day");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}