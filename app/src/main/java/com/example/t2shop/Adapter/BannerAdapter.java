package com.example.t2shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.t2shop.Model.Banner;
import com.example.t2shop.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private List<Banner> banners;
    private Context context;

    public BannerAdapter(List<Banner> banners, Context context) {
        this.banners = banners;
        this.context = context;
    }


    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_item, null);
        ImageView img_banner = view.findViewById(R.id.img_item_banner);
        img_banner.setImageBitmap(banners.get(position).getBitmap());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
