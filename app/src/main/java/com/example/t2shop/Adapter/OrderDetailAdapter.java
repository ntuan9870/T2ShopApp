package com.example.t2shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Model.ItemOrder;
import com.example.t2shop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private Context context;
    private List<ItemOrder> arrItemOrders;

    public OrderDetailAdapter(Context context, List<ItemOrder> arrItemOrders) {
        this.context = context;
        this.arrItemOrders = arrItemOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_detail_order_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_number.setText("#"+(position+1));
        ItemOrder itemOrder = arrItemOrders.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        String priceOriginal = formatter.format(Integer.parseInt(itemOrder.getProduct_price()));
        holder.txt_original_price.setText("Giá gốc: " + priceOriginal + " " +vnd);
        int pr = Integer.parseInt(itemOrder.getProduct_price())*(100-itemOrder.getProduct_promotion())/100;
        String price = formatter.format(pr);
        holder.txt_price.setText("Đơn giá: "+price + " " +vnd);
        holder.txt_name_product.setText(itemOrder.getProduct_name());
        holder.txt_amount.setText("Số lượng: "+ itemOrder.getProduct_amount()+"");
        holder.txt_promotion.setText("Khuyến mãi: " + itemOrder.getProduct_promotion()+"%");
        int sum_price = pr*itemOrder.getProduct_amount();
        String sum_pr = formatter.format(sum_price);
        holder.txt_sum_price.setText("Thành tiền: " + sum_pr +" " + vnd);
        Glide.with(context).load(itemOrder.getProduct_img()).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return arrItemOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_number, txt_name_product, txt_original_price, txt_price, txt_amount, txt_promotion, txt_sum_price;
        private ImageView img_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_number = itemView.findViewById(R.id.txt_number);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_original_price = itemView.findViewById(R.id.txt_original_price);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_promotion = itemView.findViewById(R.id.txt_promotion);
            txt_sum_price = itemView.findViewById(R.id.txt_sum_price);
            img_product = itemView.findViewById(R.id.img_product);
        }
    }
}
