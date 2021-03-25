package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.DeadObjectException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Adapter.OrderDetailAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Model.Order;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseOrderItem;

import java.text.DecimalFormat;
import java.util.Currency;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailOrderFragment extends Fragment {

    private ImageView img_back;
    private RecyclerView rc_detail_order;
    public static String TAG = DeadObjectException.class.getName();
    private TextView txt_total_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_order, container, false);
        img_back = view.findViewById(R.id.img_back);
        rc_detail_order = view.findViewById(R.id.rc_detail_order);
        txt_total_price = view.findViewById(R.id.txt_total_price);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Bundle bundle = getArguments();
        Order order = (Order) bundle.getSerializable("order");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(Integer.parseInt(order.getTotal()));
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        txt_total_price.setText(price + " " +vnd);
        Common.compositeDisposable.add(Common.it2ShopAPI.getDetailOrder(132)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseOrderItem>() {
            @Override
            public void accept(ResponseOrderItem responseOrderItem) throws Exception {
                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(getContext(), responseOrderItem.getOrderitems());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rc_detail_order.setAdapter(orderDetailAdapter);
                rc_detail_order.setLayoutManager(layoutManager);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        }));
        return view;
    }
}