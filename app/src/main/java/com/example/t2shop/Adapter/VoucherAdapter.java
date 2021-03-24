package com.example.t2shop.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Fragment.CartFragment;
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
                Bundle bundle = new Bundle();
                bundle.putInt("sl_voucher_from_notification", position+1);
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                CartFragment cartFragment = new CartFragment();
                cartFragment.setArguments(bundle);
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, cartFragment);
                transaction.addToBackStack(CartFragment.TAG);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name_voucher;
        private Button btn_use;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_voucher = itemView.findViewById(R.id.txt_name_voucher);
            btn_use = itemView.findViewById(R.id.btn_use);
        }
    }
}
