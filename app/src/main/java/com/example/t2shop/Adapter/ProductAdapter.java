package com.example.t2shop.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Fragment.DetailProductFragment;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.R;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> arrProducts;
    private List<Promotion> arrPromotions;
    private List<String> arrRatings;

    public ProductAdapter(Context context, List<Product> arrProducts, List<Promotion> arrPromotions, List<String> arrRatings) {
        this.context = context;
        this.arrProducts = arrProducts;
        this.arrPromotions = arrPromotions;
        this.arrRatings = arrRatings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product p = arrProducts.get(position);
        final Promotion promotion = arrPromotions.get(position);
        Glide.with(context).load(p.getProduct_img()).into(holder.img_home_product);
        holder.txt_name_home_product.setText(p.getProduct_name() + " là " + p.getProduct_description());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int promo = 0;
        if(promotion != null){
            promo= promotion.getPromotion_infor();
        }
        String price = formatter.format(p.getProduct_price()-(p.getProduct_price()*promo)/100);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        holder.txt_price_home_product.setText(price+" " + vnd);
        String dsc = p.getProduct_description().replace("<p>", "");
        dsc = dsc.replace("</p>", "");
        holder.txt_name_home_product.setText(p.getProduct_name() + " là " + dsc);
        holder.txt_promotion_home_product.setText("- " + promo +"%");
        holder.rating_home_product.setRating(Float.parseFloat(arrRatings.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", p);
                bundle.putSerializable("promotion", promotion);
                bundle.putString("rating", arrRatings.get(position));
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                DetailProductFragment detailProductFragment = new DetailProductFragment();
                detailProductFragment.setArguments(bundle);
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, detailProductFragment);
                transaction.addToBackStack(DetailProductFragment.TAG);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_home_product;
        private TextView txt_name_home_product, txt_price_home_product, txt_promotion_home_product;
        private RatingBar rating_home_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_home_product = itemView.findViewById(R.id.img_home_product);
            txt_name_home_product = itemView.findViewById(R.id.txt_name_home_product);
            txt_price_home_product = itemView.findViewById(R.id.txt_price_home_product);
            txt_promotion_home_product = itemView.findViewById(R.id.txt_promotion_home_product);
            rating_home_product = itemView.findViewById(R.id.rating_home_product);
        }
    }
}
