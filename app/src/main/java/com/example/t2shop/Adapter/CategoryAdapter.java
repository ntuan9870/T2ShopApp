package com.example.t2shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Model.Category;
import com.example.t2shop.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> arrCategory;

    public CategoryAdapter(Context context, List<Category> arrCategory) {
        this.context = context;
        this.arrCategory = arrCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrCategory.get(position).getCategory_image()).into(holder.img_category);
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_category = itemView.findViewById(R.id.img_category);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Category> getArrCategory() {
        return arrCategory;
    }

    public void setArrCategory(List<Category> arrCategory) {
        this.arrCategory = arrCategory;
    }

}
