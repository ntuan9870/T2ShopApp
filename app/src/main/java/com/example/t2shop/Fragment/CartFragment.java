package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.t2shop.Adapter.CartItemAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Store;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseChangeStore;
import com.example.t2shop.Response.ResponseStore;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TextInputLayout spinner_select_store;
    private TextInputEditText spinner_store;
    private Store sl_store = new Store();
    private ArrayList<Store> arrStore = new ArrayList<>();
    private ArrayList<String> resCheckCart = new ArrayList<>();
    private ArrayList<Integer> resMax = new ArrayList<>();
    private CartItemAdapter cartItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        compositeDisposable = new CompositeDisposable();
        RelativeLayout rl_root = view.findViewById(R.id.ln_root);
        txt_nothing = view.findViewById(R.id.txt_nothing);
        spinner_select_store = view.findViewById(R.id.spinner_select_store);
        spinner_store = view.findViewById(R.id.spinner_store);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rc_cart = view.findViewById(R.id.rc_cart);
        btn_exit_cart = view.findViewById(R.id.btn_exit_cart);
        txt_title_cart = view.findViewById(R.id.txt_title_cart);
        btn_buy = view.findViewById(R.id.btn_buy);

        txt_sum_price = view.findViewById(R.id.txt_sum_price);
        int sum = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getAmount();
        List<ItemCart> itemCarts = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getItems();
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
        User user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItemAdapter.getArrItems().size()!=0){
                    int kl = 0;
                    List<ItemCart> itemCart = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getItems();
                    for (int i = 0; i < itemCart.size(); i++){
                        if(resMax.get(i)<itemCart.get(i).getAmount()){
                            kl++;
                        }
                    }
                    if(kl == 0){
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
                            Constants.store_id = sl_store.getStore_id();
                            dialog.dismiss();
                        }
                    }else{
                        Common2.confirmDialog(getContext(), "Hàng không đủ!", "Vui lòng thay đổi lựa chọn!", "OK");
                    }
                }else{
                    Common2.confirmDialog(getContext(), "Hãy thêm sản phẩm!", "Tất nhiên bạn không thể đặt hàng nếu không có sản phẩm! :)", "OK");
                }
            }
        });
        spinner_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStore();
            }
        });
        getALlStore();
        return view;
    }

    private void checkChangeStore() {
        List<ItemCart> itemCarts = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getItems();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < itemCarts.size(); i++){
            JSONObject myJsonObject = new JSONObject();
            try {
                myJsonObject.put("promotion", itemCarts.get(i).getPromotion_infor());
                myJsonObject.put("num", itemCarts.get(i).getAmount());
                JSONObject myJsonObject2 = new JSONObject();
                myJsonObject2.put("product_id", itemCarts.get(i).getProduct_id());
                myJsonObject2.put("product_price", itemCarts.get(i).getProduct_price());
                myJsonObject2.put("product_name", itemCarts.get(i).getProduct_name());
                myJsonObject2.put("product_warranty", "1 năm");
                myJsonObject.put("product", myJsonObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(myJsonObject);
        }
        Common.compositeDisposable.add(Common.it2ShopAPI.checkChangeStore(jsonArray.toString(), sl_store.getStore_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseChangeStore>() {
                    @Override
                    public void accept(ResponseChangeStore responseChangeStore) throws Exception {
                        if(responseChangeStore!=null){
                            List<ItemCart> items = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getItems();
                            if (items.size()==0){
                                txt_nothing.setVisibility(View.VISIBLE);
                            }else{
                                txt_nothing.setVisibility(View.INVISIBLE);
                            }
                            resCheckCart = responseChangeStore.getMessage();
                            resMax = responseChangeStore.getMax();
                            cartItemAdapter = new CartItemAdapter(getContext(), items, resCheckCart, resMax, getActivity());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rc_cart.setLayoutManager(layoutManager);
                            rc_cart.setAdapter(cartItemAdapter);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void selectStore() {
        AlertDialog.Builder mSLAddress = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_sl_address, null);
        ListView lv_sl_address = mView.findViewById(R.id.lv_sl_address);
        ArrayList<String> arrOptions = new ArrayList<>();
        for (int i = 0; i < arrStore.size(); i++){
            arrOptions.add(arrStore.get(i).getStore_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lv_sl_address.setAdapter(adapter);
//        if(arrStore!=null){
//            checkChangeStore();
//        }
        mSLAddress.setView(mView);
        AlertDialog dialog = mSLAddress.create();
        dialog.show();
        lv_sl_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_store.setText(lv_sl_address.getAdapter().getItem(position).toString());
                sl_store = arrStore.get(position);
                checkChangeStore();
                dialog.cancel();
            }
        });
    }

    private void getALlStore() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getAllStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseStore>() {
                    @Override
                    public void accept(ResponseStore responseStore) throws Exception {
                        if (responseStore!=null){
                            arrStore = responseStore.getArrStores();
                            sl_store = arrStore.get(0);
                            spinner_store.setText(sl_store.getStore_name());
                            checkChangeStore();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}