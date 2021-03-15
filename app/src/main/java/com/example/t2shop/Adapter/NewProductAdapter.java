package com.example.t2shop.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Model.Product;
import com.example.t2shop.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> arrProducts;

    public NewProductAdapter(Context context, List<Product> arrProducts) {
        this.context = context;
        this.arrProducts = arrProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_new_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = arrProducts.get(position);
        Glide.with(context).load(p.getProduct_img()).into(holder.img_new_product);
        holder.txt_name_new_product.setText(p.getProduct_name());
    }

    @Override
    public int getItemCount() {
        return arrProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_new_product;
        private TextView txt_name_new_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_new_product = itemView.findViewById(R.id.img_new_product);
            txt_name_new_product = itemView.findViewById(R.id.txt_name_new_product);
        }
    }
}
