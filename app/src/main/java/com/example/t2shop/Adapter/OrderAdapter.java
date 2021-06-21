package com.example.t2shop.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Fragment.DetailOrderFragment;
import com.example.t2shop.Fragment.DetailProductFragment;
import com.example.t2shop.Model.Order;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;
import com.example.t2shop.Response.ResponseProduct;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<Order> arrOrders;

    public OrderAdapter(Context context, List<Order> arrOrders) {
        this.context = context;
        this.arrOrders = arrOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order o = arrOrders.get(position);
        holder.txt_id_order.setText("#" + o.getOrder_id()+"");
        holder.txt_date_order.setText(o.getCreated_at());
        holder.txt_name_order.setText("Đơn hàng được đặt vào ngày " + o.getCreated_at() + "..");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(Integer.parseInt(o.getTotal()));
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        holder.txt_amount_order.setText(price + " " +vnd);
        if (!o.getReady().equals("0")){
            holder.img_ready.setImageResource(R.drawable.ic_baseline_check_circle_24);
            holder.img_ready.setColorFilter(0xFF26B100);
        }else{
            holder.img_ready.setImageResource(R.drawable.ic_baseline_cancel_24);
            holder.img_ready.setColorFilter(0xFFFF0000);
        }
        if (!o.getStatus().equals("0")){
            holder.img_status.setImageResource(R.drawable.ic_baseline_flash_on_24);
        }else{
            holder.img_status.setImageResource(R.drawable.ic_baseline_flash_off_24);
        }
        if (o.getForm().equals("Trực tiếp")){
            holder.img_form.setImageResource(R.drawable.ic_baseline_store_24);
            holder.img_form.setColorFilter(0xFF00189F);
        }else{
            holder.img_form.setImageResource(R.drawable.ic_baseline_credit_card_24);
            holder.img_form.setColorFilter(0xFFFF0000);
        }
        if(o.getStatus().equals("2")){
            holder.txt_canceled.setVisibility(View.VISIBLE);
            holder.btn_cancel.setVisibility(View.INVISIBLE);
        }else{
            holder.txt_canceled.setVisibility(View.INVISIBLE);
            holder.btn_cancel.setVisibility(View.VISIBLE);
        }
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", o);
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)(context)).getSupportFragmentManager().beginTransaction();
                DetailOrderFragment detailOrderFragment = new DetailOrderFragment();
                detailOrderFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(DetailOrderFragment.TAG);
                fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                fragmentTransaction.replace(R.id.main_frame, detailOrderFragment);
                fragmentTransaction.commit();
            }
        });
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(o.getStatus().equals("1")){
                    Common2.showDialogAutoClose(context, "Bạn không thể hủy đơn hàng đang được giao!");
                }else{
                    Common.compositeDisposable.add(Common.it2ShopAPI.removeOrder(o.getOrder_id())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseMessage>() {
                                @Override
                                public void accept(ResponseMessage responseMessage) throws Exception {
                                    if (responseMessage!=null){
                                        if(responseMessage.getMessage().equals("success")){
                                            notifyDataSetChanged();
                                            Common2.showDialogAutoClose(context, "Hủy đơn hàng thành công!");
                                        }else{
                                            Common2.showDialogAutoClose(context, "Đã xảy ra lỗi!");
                                        }
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                }
                            }));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_id_order, txt_date_order, txt_name_order, txt_amount_order, txt_canceled;
        private ImageView img_ready, img_status, img_form;
        private Button btn_detail, btn_cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_date_order = itemView.findViewById(R.id.txt_date_order);
            txt_name_order = itemView.findViewById(R.id.txt_name_order);
            txt_amount_order = itemView.findViewById(R.id.txt_amount_order);
            img_ready = itemView.findViewById(R.id.img_ready);
            img_status = itemView.findViewById(R.id.img_status);
            img_form = itemView.findViewById(R.id.img_form);
            btn_detail = itemView.findViewById(R.id.btn_detail);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            txt_canceled = itemView.findViewById(R.id.txt_canceled);
        }
    }
}
