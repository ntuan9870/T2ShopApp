package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Adapter.OrderAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseOrder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderManagerFragment extends Fragment {

    private ImageView img_back;
    private RecyclerView rc_order_manager;
    private TextView txt_nothing;
    public static String TAG = OrderManagerFragment.class.getName();
    private LinearLayout ln_note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manager, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_back = view.findViewById(R.id.img_back);
        rc_order_manager = view.findViewById(R.id.rc_order_manager);
        txt_nothing = view.findViewById(R.id.txt_nothing);
        ln_note = view.findViewById(R.id.ln_note);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        User user = UserDatabase.getInstance(getContext()).userDAO().getItems();
        Common.compositeDisposable.add(Common.it2ShopAPI.getAllOrderByUserId(user.getUser_id())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseOrder>() {
            @Override
            public void accept(ResponseOrder responseOrder) throws Exception {
                if (responseOrder.getOrders().size()==0){
                    txt_nothing.setVisibility(View.VISIBLE);
                    ln_note.setVisibility(View.INVISIBLE);
                }else{
                    ln_note.setVisibility(View.VISIBLE);
                    txt_nothing.setVisibility(View.INVISIBLE);
                }
                OrderAdapter orderAdapter = new OrderAdapter(getContext(), responseOrder.getOrders());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rc_order_manager.setLayoutManager(layoutManager);
                rc_order_manager.setAdapter(orderAdapter);
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