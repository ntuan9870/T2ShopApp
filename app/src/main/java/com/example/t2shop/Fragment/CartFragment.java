package com.example.t2shop.Fragment;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.CartItemAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseRatingAll;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartFragment extends Fragment {

    private RecyclerView rc_cart;
    private ImageView btn_exit_cart;
    public static String TAG = CartFragment.class.getName();
    public static TextView txt_title_cart, txt_sum_price;
    public static int sum_price = 0;
    private Button btn_buy;
    private String voucher="null";
    private int idSL = -1;
    private List<Voucher> voucherList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_cart = view.findViewById(R.id.rc_cart);
        btn_exit_cart = view.findViewById(R.id.btn_exit_cart);
        txt_title_cart = view.findViewById(R.id.txt_title_cart);
        btn_buy = view.findViewById(R.id.btn_buy);
        List<ItemCart> items = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
        CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_cart.setLayoutManager(layoutManager);
        rc_cart.setAdapter(cartItemAdapter);
        txt_sum_price = view.findViewById(R.id.txt_sum_price);
        int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
        List<ItemCart> itemCarts = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
        sum_price = 0;
        for (int i = 0; i < itemCarts.size(); i++){
            sum_price += itemCarts.get(i).getProduct_price()*itemCarts.get(i).getAmount()*(100-Integer.parseInt(itemCarts.get(i).getPromotion_infor()))/100;
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(sum_price);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        txt_sum_price.setText(price + " " +vnd);
        txt_title_cart.setText("Giỏ hàng ("+sum+")");
        btn_exit_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        User user = UserDatabase.getInstance(getContext()).userDAO().getItems();
        idSL = 0;
        if (user!=null){
            Common.compositeDisposable.add(Common.it2ShopAPI.getAllVoucher(user.getUser_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseAllVoucher>() {
                        @Override
                        public void accept(ResponseAllVoucher responseAllVoucher) throws Exception {
                            voucherList = responseAllVoucher.getVouchers();
                            ArrayList<String> arrOptions = new ArrayList<>();
                            arrOptions.add("Khong chon Voucher");
                            for (int i = 0; i < responseAllVoucher.getVouchers().size(); i++){
                                arrOptions.add(responseAllVoucher.getVouchers().get(i).getVoucher_name());
                            }
                            Spinner spinner = view.findViewById(R.id.spinner_select_voucher);
                            spinner.setSelection(0);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrOptions);
                            spinner.setAdapter(adapter);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    idSL = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idSL == 0){
                    voucher = "null";
                }else{
                    voucher = voucherList.get(idSL-1).getVoucher_id()+"";
                }
                Bundle bundle = new Bundle();
                bundle.putString("voucher_id", voucher);
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                CheckoutFragment checkoutFragment = new CheckoutFragment();
                checkoutFragment.setArguments(bundle);
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, checkoutFragment);
                transaction.addToBackStack(CheckoutFragment.TAG);
                transaction.commit();
            }
        });
        return view;
    }
}