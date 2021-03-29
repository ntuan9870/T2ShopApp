package com.example.t2shop.Adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Fragment.CartFragment;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context context;
    private List<ItemCart> arrItems;

    public CartItemAdapter(Context context, List<ItemCart> arrItems) {
        this.context = context;
        this.arrItems = arrItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ItemCart itemCart = arrItems.get(position);
        Glide.with(context).load(itemCart.getProduct_img()).into(holder.img_product_cart);
        String dsc = itemCart.getProduct_description().replace("<p>", "");
        dsc = dsc.replace("</p>", "");
        holder.txt_name_product_cart.setText(itemCart.getProduct_name() + " là " + dsc);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*Integer.parseInt(itemCart.getPromotion_infor()))/100);
        String price = formatter.format(pr);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        holder.txt_price_product_cart.setText(price+" " + vnd);
        holder.txt_amount_product.setText(""+arrItems.get(position).getAmount());
        if (holder.txt_amount_product.getText().equals("1")){
            holder.btn_decrease_number.setEnabled(false);
        }else{
            holder.btn_decrease_number.setEnabled(true);
        }
        holder.btn_decrease_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txt_amount_product.getText()!="1"){
                    itemCart.setAmount(itemCart.getAmount()-1);
                    holder.txt_amount_product.setText(itemCart.getAmount()+"");
                    ItemCartDatabase.getInstance(getContext()).itemCartDAO().update(itemCart);
                    if (itemCart.getAmount()==1){
                        holder.btn_decrease_number.setEnabled(false);
                    }
                    double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*Integer.parseInt(itemCart.getPromotion_infor()))/100);
                    String price = formatter.format(pr);
                    holder.txt_price_product_cart.setText(price+" " + vnd);
                    CartFragment.sum_price-=itemCart.getProduct_price()*(100-Integer.parseInt(itemCart.getPromotion_infor()))/100;
                    CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
                }
            }
        });
        holder.btn_increase_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCart.setAmount(itemCart.getAmount()+1);
                holder.txt_amount_product.setText(itemCart.getAmount()+"");
                ItemCartDatabase.getInstance(getContext()).itemCartDAO().update(itemCart);
                if (itemCart.getAmount()>1){
                    holder.btn_decrease_number.setEnabled(true);
                }
                double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*Integer.parseInt(itemCart.getPromotion_infor()))/100);
                String price = formatter.format(pr);
                holder.txt_price_product_cart.setText(price+" " + vnd);
                CartFragment.sum_price+=itemCart.getProduct_price()*(100-Integer.parseInt(itemCart.getPromotion_infor()))/100;
                CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
            }
        });
        holder.img_cancel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemCartDatabase.getInstance(context).itemCartDAO().delete(itemCart);
                notifyItemRemoved(position);
                getArrItems().remove(position);
                notifyItemRangeChanged(position, getItemCount());
                animateHeight((View) holder.itemView.getParent(), holder.itemView.getMeasuredHeight());
                if (getArrItems().size()==0){
                    CartFragment.txt_nothing.setVisibility(View.VISIBLE);
                }else{
                    CartFragment.txt_nothing.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void animateHeight(final View v, final int height) {
        final int initialHeight = v.getMeasuredHeight();
        int duration = 360* Constants.SCREEN_HEIGHT/1920;
        Interpolator interpolator = new AccelerateInterpolator(2);
        v.getLayoutParams().height = initialHeight;
        v.requestLayout();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = initialHeight - (int) (height * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);
        a.setInterpolator(interpolator);
        v.startAnimation(a);
    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product_cart, img_cancel_item;
        TextView txt_name_product_cart, txt_price_product_cart, txt_amount_product;
        Button btn_increase_number, btn_decrease_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product_cart = itemView.findViewById(R.id.img_product_cart);
            img_cancel_item = itemView.findViewById(R.id.img_cancel_item);
            txt_name_product_cart = itemView.findViewById(R.id.txt_name_product_cart);
            txt_price_product_cart = itemView.findViewById(R.id.txt_price_product_cart);
            txt_amount_product = itemView.findViewById(R.id.txt_amount_product);
            btn_increase_number = itemView.findViewById(R.id.btn_increase_amount);
            btn_decrease_number = itemView.findViewById(R.id.btn_decrease_amount);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ItemCart> getArrItems() {
        return arrItems;
    }

    public void setArrItems(List<ItemCart> arrItems) {
        this.arrItems = arrItems;
    }
}
