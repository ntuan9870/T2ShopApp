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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.CartItemAdapter;
import com.example.t2shop.CallBack.RetrofitClient;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Common.RetrofitAPIAddress;
import com.example.t2shop.DAO.ItemCartDAO;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.District;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;
import com.example.t2shop.Response.ResponseRatingAll;
import com.example.t2shop.Retrofit.AddressAPI;
import com.example.t2shop.Retrofit.IT2ShopAPI;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartFragment extends Fragment {

    private RecyclerView rc_cart;
    private ImageView btn_exit_cart;
    public static String TAG = CartFragment.class.getName();
    public static TextView txt_title_cart, txt_sum_price, txt_nothing;
    public static int sum_price = 0;
    private Button btn_buy;
    private CompositeDisposable compositeDisposable;
    private Spinner spinner_select_store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        compositeDisposable = new CompositeDisposable();
        RelativeLayout rl_root = view.findViewById(R.id.ln_root);
        txt_nothing = view.findViewById(R.id.txt_nothing);
        spinner_select_store = view.findViewById(R.id.spinner_select_store);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rc_cart = view.findViewById(R.id.rc_cart);
        btn_exit_cart = view.findViewById(R.id.btn_exit_cart);
        txt_title_cart = view.findViewById(R.id.txt_title_cart);
        btn_buy = view.findViewById(R.id.btn_buy);
        List<ItemCart> items = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
        CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(), items, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_cart.setLayoutManager(layoutManager);
        rc_cart.setAdapter(cartItemAdapter);
        if (items.size()==0){
            txt_nothing.setVisibility(View.VISIBLE);
        }else{
            txt_nothing.setVisibility(View.INVISIBLE);
        }
        txt_sum_price = view.findViewById(R.id.txt_sum_price);
        int sum = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getAmount();
        List<ItemCart> itemCarts = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
        sum_price = 0;
        for (int i = 0; i < itemCarts.size(); i++){
            sum_price += itemCarts.get(i).getProduct_price()*itemCarts.get(i).getAmount()*(100-itemCarts.get(i).getPromotion_infor())/100;
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

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItemAdapter.getArrItems().size()!=0){
                    if (user==null){
                        Bundle bundle = new Bundle();
                        bundle.putString("checkouting", "yes");
                        FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                        LoginFragment loginFragment = new LoginFragment();
                        loginFragment.setArguments(bundle);
                        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                        transaction.replace(R.id.main_frame, loginFragment);
                        transaction.addToBackStack(LoginFragment.TAG);
                        transaction.commit();
                    }else{
                        SweetAlertDialog dialog = Common2.loadingDialog(getContext(), "Xin chờ..");
                        dialog.show();
                        FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                        CheckoutFragment checkoutFragment = new CheckoutFragment();
                        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                        transaction.replace(R.id.main_frame, checkoutFragment);
                        transaction.addToBackStack(CheckoutFragment.TAG);
                        transaction.commit();
                        dialog.dismiss();
                    }
                }else{
                    Common2.confirmDialog(getContext(), "Hãy thêm sản phẩm!", "Tất nhiên bạn không thể đặt hàng nếu không có sản phẩm! :)", "OK");
                }
            }
        });
        selectStore();
        return view;
    }

    private void selectStore() {
//        compositeDisposable.add(Common.it2ShopAPI.getAllStore()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<District>>() {
//                    @Override
//                    public void accept(List<District> responseDistrict) throws Exception {
//                        ArrayList<String> arrOptions = new ArrayList<>();
//                        for (int i = 0; i < responseDistrict.size(); i++) {
//                            arrOptions.add(responseDistrict.get(i).getTitle());
////                            arrDistrict.add(responseDistrict.get(i).getID());
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                        lv_sl_address.setAdapter(adapter);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getContext(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }));
    }
}