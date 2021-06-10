package com.example.t2shop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Fragment.CartFragment;
import com.example.t2shop.Fragment.CheckoutFragment;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;

import java.util.ArrayList;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {
    private Context context;
    private List<Voucher> vouchers;

    public VoucherAdapter(Context context, List<Voucher> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_voucher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_name_voucher.setText(vouchers.get(position).getVoucher_name());
        holder.btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ItemCart> itemCarts = T2ShopDatabase.getInstance(context).itemCartDAO().getItems();
                if(itemCarts.size() == 0){
                    Common2.showDialogAutoClose(context,"Giỏ hàng rỗng");
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("sl_voucher_from_notification", position+1);
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    CheckoutFragment checkoutFragment = new CheckoutFragment();
                    checkoutFragment.setArguments(bundle);
                    transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    transaction.replace(R.id.main_frame, checkoutFragment);
                    transaction.addToBackStack(CheckoutFragment.TAG);
                    transaction.commit();
                }
            }
        });
        holder.btn_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mSuccess = new AlertDialog.Builder(context);
                View mView = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_detail_voucher, null);
                Button btn_ok = mView.findViewById(R.id.btn_ok);
                TextView txt_detail_voucher = mView.findViewById(R.id.txt_detail_voucher);
                mSuccess.setView(mView);
                AlertDialog dialog = mSuccess.create();
                dialog.show();
                txt_detail_voucher.setText("Tên Voucher: " + vouchers.get(position).getVoucher_name()+"\n"
                +"Ngày hết hạn: "+vouchers.get(position).getVoucher_end() + "\nGiá trị: "+vouchers.get(position).getVoucher_value()
                +"VND\nChiết khấu: " + vouchers.get(position).getVoucher_discount() + "%\nSố lượng: " +  vouchers.get(position).getAmount_voucher());
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name_voucher;
        private Button btn_use, btn_view_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_voucher = itemView.findViewById(R.id.txt_name_voucher);
            btn_use = itemView.findViewById(R.id.btn_use);
            btn_view_detail = itemView.findViewById(R.id.btn_view_detail);
        }
    }
}
