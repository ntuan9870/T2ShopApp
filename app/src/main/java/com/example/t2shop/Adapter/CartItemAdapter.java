package com.example.t2shop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Fragment.CartFragment;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseChangeStore;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context context;
    private List<ItemCart> arrItems;
    private List<String> resCheckCart;
    private Activity activity;
    private List<Integer> resMax;

    public CartItemAdapter(Context context, List<ItemCart> arrItems, List<String> resCheckCart, List<Integer> resMax, Activity activity) {
        this.context = context;
        this.arrItems = arrItems;
        this.resCheckCart = resCheckCart;
        this.activity = activity;
        this.resMax = resMax;
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
        String dsc = itemCart.getProduct_description().replace( "<p>", "");
        dsc = dsc.replace("</p>", "");
        holder.txt_name_product_cart.setText(itemCart.getProduct_name() + " là " + dsc);
        if(resCheckCart.get(position).equals("true")){
            holder.txt_status.setText("Còn hàng");
            holder.txt_status.setTextColor(0xFF26FF00);
        }else{
            holder.txt_status.setText("Hết hàng");
            holder.txt_status.setTextColor(0xFFFF0000);
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*itemCart.getPromotion_infor())/100);
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
                    double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*itemCart.getPromotion_infor())/100);
                    String price = formatter.format(pr);
                    holder.txt_price_product_cart.setText(price+" " + vnd);
                    CartFragment.sum_price-=itemCart.getProduct_price()*(100-itemCart.getPromotion_infor())/100;
                    CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
                    int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
                    CartFragment.txt_title_cart.setText("Giỏ hàng ("+sum+")");
                    if(itemCart.getAmount()<=resMax.get(position)){
                        holder.txt_status.setText("Còn hàng");
                        holder.txt_status.setTextColor(0xFF26FF00);
                    }else{
                        holder.txt_status.setText("Hết hàng");
                        holder.txt_status.setTextColor(0xFFFF0000);
                    }
                }
            }
        });
        holder.btn_increase_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+itemCart.getProduct_amount(), Toast.LENGTH_SHORT).show();
//                if (itemCart.getAmount()<itemCart.getProduct_amount()){
                    itemCart.setAmount(itemCart.getAmount()+1);
                    holder.txt_amount_product.setText(itemCart.getAmount()+"");
                    ItemCartDatabase.getInstance(getContext()).itemCartDAO().update(itemCart);
                    if (itemCart.getAmount()>1){
                        holder.btn_decrease_number.setEnabled(true);
                    }
                    double pr = itemCart.getAmount()*(itemCart.getProduct_price()-(itemCart.getProduct_price()*itemCart.getPromotion_infor())/100);
                    String price = formatter.format(pr);
                    holder.txt_price_product_cart.setText(price+" " + vnd);
                    CartFragment.sum_price+=itemCart.getProduct_price()*(100-itemCart.getPromotion_infor())/100;
                    CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
                    int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
                    CartFragment.txt_title_cart.setText("Giỏ hàng ("+sum+")");
                    if(itemCart.getAmount()<=resMax.get(position)){
                        holder.txt_status.setText("Còn hàng");
                        holder.txt_status.setTextColor(0xFF26FF00);
                    }else{
                        holder.txt_status.setText("Hết hàng");
                        holder.txt_status.setTextColor(0xFFFF0000);
                    }
//                }
            }
        });
        holder.img_cancel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.sum_price-=itemCart.getAmount()*(itemCart.getProduct_price()*(100-itemCart.getPromotion_infor())/100);
                CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
                ItemCartDatabase.getInstance(context).itemCartDAO().delete(itemCart);
                notifyItemRemoved(position);
                getArrItems().remove(position);
                getResCheckCart().remove(position);
                getResMax().remove(position);
                notifyItemRangeChanged(position, getItemCount());
                animateHeight((View) holder.itemView.getParent(), holder.itemView.getMeasuredHeight());
                if (getArrItems().size()==0){
                    CartFragment.txt_nothing.setVisibility(View.VISIBLE);
                }else{
                    CartFragment.txt_nothing.setVisibility(View.INVISIBLE);
                }
                int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
                CartFragment.txt_title_cart.setText("Giỏ hàng ("+sum+")");
            }
        });
        holder.txt_amount_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mChangeNumber = new AlertDialog.Builder(activity);
                View mView = LayoutInflater.from(context).inflate(R.layout.dialog_change_number, null);
                Button btn_submit = mView.findViewById(R.id.btn_submit);
                TextInputLayout edt_number = mView.findViewById(R.id.edt_number);
                mChangeNumber.setView(mView);
                AlertDialog dialog = mChangeNumber.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                edt_number.getEditText().setText(itemCart.getAmount()+"");
                edt_number.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        validateNumber(edt_number);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validateNumber(edt_number)){
                            itemCart.setAmount(Integer.parseInt(edt_number.getEditText().getText().toString().trim()));
                            CartFragment.sum_price = (int)(itemCart.getAmount()*(itemCart.getProduct_price()*(100-itemCart.getPromotion_infor())/100));
                            CartFragment.txt_sum_price.setText(formatter.format(CartFragment.sum_price)+" "+vnd);
                            holder.txt_amount_product.setText(edt_number.getEditText().getText().toString().trim());
                            int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
                            CartFragment.txt_title_cart.setText("Giỏ hàng ("+sum+")");
                            ItemCartDatabase.getInstance(getContext()).itemCartDAO().update(itemCart);
                            dialog.dismiss();
                            if(itemCart.getAmount()<=resMax.get(position)){
                                holder.txt_status.setText("Còn hàng");
                                holder.txt_status.setTextColor(0xFF26FF00);
                            }else{
                                holder.txt_status.setText("Hết hàng");
                                holder.txt_status.setTextColor(0xFFFF0000);
                            }
                        }
                    }
                });
            }

            private boolean validateNumber(TextInputLayout edt_number) {
                if (edt_number.getEditText().getText().length()==0){
                    edt_number.setError("Vui lòng nhập số lượng!");
                    return false;
                }
//                else if (Integer.parseInt(edt_number.getEditText().getText().toString().trim())>itemCart.getProduct_amount()){
//                    edt_number.getEditText().setText(itemCart.getProduct_amount()+"");
//                }
                edt_number.setError(null);

                return true;
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
        TextView txt_name_product_cart, txt_price_product_cart, txt_amount_product, txt_status;
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
            txt_status = itemView.findViewById(R.id.txt_status);
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

    public List<String> getResCheckCart() {
        return resCheckCart;
    }

    public void setResCheckCart(List<String> resCheckCart) {
        this.resCheckCart = resCheckCart;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Integer> getResMax() {
        return resMax;
    }

    public void setResMax(List<Integer> resMax) {
        this.resMax = resMax;
    }
}
