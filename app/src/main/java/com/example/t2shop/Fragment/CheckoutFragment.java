package com.example.t2shop.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.RetrofitAPIAddress;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.District;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.Model.Ward;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseCity;
import com.example.t2shop.Response.ResponseSuccess;
import com.example.t2shop.Retrofit.AddressAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckoutFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private AddressAPI addressAPI;
    public static String TAG = CheckoutFragment.class.getName();
    private TextInputLayout text_input_user_name, text_input_phone_number, text_input_user_address, text_input_user_address_city, text_input_user_address_district,
            text_input_user_address_ward;
    private TextInputEditText spinner_city, spinner_district, spinner_ward;
    private ArrayList<Integer> arrWard = new ArrayList<>();
    private ArrayList<Integer> arrDistrict = new ArrayList<>();
    private ArrayList<Integer> arrCity = new ArrayList<>();
    private int idCity, idDistrict;
    private TextView txt_sum_price_checkout;
    private ImageView img_back_check_out;
    private Button btn_buy;
    private int sum_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        compositeDisposable = new CompositeDisposable();
        addressAPI = RetrofitAPIAddress.getAPI();
        View view = inflater.inflate(R.layout.fragment_check_out, container, false);
        text_input_user_name = view.findViewById(R.id.text_input_user_name);
        text_input_phone_number = view.findViewById(R.id.text_input_phone_number);
        text_input_user_address = view.findViewById(R.id.text_input_user_address);
        text_input_user_address_city = view.findViewById(R.id.text_input_user_address_city);
        text_input_user_address_district = view.findViewById(R.id.text_input_user_address_district);
        text_input_user_address_ward = view.findViewById(R.id.text_input_user_address_ward);
        txt_sum_price_checkout = view.findViewById(R.id.txt_sum_price_checkout);
        spinner_city = view.findViewById(R.id.spinner_city);
        spinner_district = view.findViewById(R.id.spinner_district);
        spinner_ward = view.findViewById(R.id.spinner_ward);
        img_back_check_out = view.findViewById(R.id.img_back_check_out);
        btn_buy = view.findViewById(R.id.btn_buy);
        List<ItemCart> itemCarts = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
        sum_price = 0;
        for (int i = 0; i < itemCarts.size(); i++){
            sum_price += itemCarts.get(i).getProduct_price()*itemCarts.get(i).getAmount()*(100-Integer.parseInt(itemCarts.get(i).getPromotion_infor()))/100;
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(sum_price);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        txt_sum_price_checkout.setText(price + " " +vnd);
        text_input_user_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>15){
                    text_input_user_name.setError("Tên người nhận quá dài!");
                }else if (s.length()==0) {
                    text_input_user_name.setError("Tên người nhận là bắt buộc!");
                }else{
                    text_input_user_name.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        text_input_phone_number.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>11){
                    text_input_phone_number.setError("Số điện thoại quá dài!");
                }else if(s.length()==0){
                    text_input_phone_number.setError("Số điện thoại là bắt buộc!");
                }else{
                    text_input_phone_number.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<8) {
                    text_input_phone_number.setError("Số quá ngắn");
                }
            }
        });
        text_input_user_address.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    text_input_user_address.setError("Số điện thoại là bắt buộc!");
                }else{
                    text_input_user_address.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        spinner_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddressCity();
            }
        });
        spinner_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spinner_city.getText().toString().equals("")){
                    selectAddressDistrict();
                    text_input_user_address_district.setErrorEnabled(false);
                }else{
                    text_input_user_address_district.setError("Vui lòng chọn tỉnh/thành phố trước!");
                }
            }
        });
        spinner_ward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spinner_district.getText().toString().equals("")){
                    selectAddressWard();
                    text_input_user_address_ward.setErrorEnabled(false);
                }else{
                    text_input_user_address_ward.setError("Vui lòng chọn quận/huyện trước!");
                }
            }
        });
        img_back_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ItemCart> itemCarts = ItemCartDatabase.getInstance(getContext()).itemCartDAO().getItems();
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
                Bundle mBundle = new Bundle();
                mBundle = getArguments();
                String voucher_id = mBundle.getString("voucher_id");
                User user = UserDatabase.getInstance(getContext()).userDAO().getItems();
                compositeDisposable.add(Common.it2ShopAPI.addOrder(jsonArray.toString(), user.getUser_id()+"", text_input_user_name.getEditText().getText().toString(),
                        text_input_phone_number.getEditText().getText().toString(), "", text_input_user_address_city.getEditText().getText().toString(), sum_price+"", "Trực tiếp",
                        voucher_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseSuccess>() {
                            @Override
                            public void accept(ResponseSuccess responseSuccess) throws Exception {
                                AlertDialog.Builder mSuccess = new AlertDialog.Builder(getActivity());
                                View mView = getLayoutInflater().inflate(R.layout.dialog_complete_checkout, null);
                                Button btn_success = mView.findViewById(R.id.btn_success);
                                mSuccess.setView(mView);
                                AlertDialog dialog = mSuccess.create();
                                dialog.show();
                                btn_success.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                            fm.popBackStack();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("AAA", throwable.getMessage().toString());
                            }
                        }));
            }
        });
        return view;
    }

    private void selectAddressCity() {
        AlertDialog.Builder mSLAddress = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_sl_address, null);
        ListView lv_sl_address = mView.findViewById(R.id.lv_sl_address);
        compositeDisposable.add(addressAPI.getAllCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseCity>() {
                    @Override
                    public void accept(ResponseCity responseCity) throws Exception {
                        ArrayList<String> arrOptions = new ArrayList<>();
                        for (int i = 0; i < responseCity.getLtsItem().size(); i++){
                            arrOptions.add(responseCity.getLtsItem().get(i).getTitle());
                            arrCity.add(responseCity.getLtsItem().get(i).getID());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lv_sl_address.setAdapter(adapter);
                    }
                }));
        mSLAddress.setView(mView);
        AlertDialog dialog = mSLAddress.create();
        dialog.show();
        lv_sl_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_city.setText(lv_sl_address.getAdapter().getItem(position).toString());
                idCity = arrCity.get(position);
                dialog.cancel();
            }
        });
    }
    private void selectAddressDistrict() {
        AlertDialog.Builder mSLAddress = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_sl_address, null);
        ListView lv_sl_address = mView.findViewById(R.id.lv_sl_address);
        compositeDisposable.add(addressAPI.getAllDistrictInCity(idCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<District>>() {
                    @Override
                    public void accept(List<District> responseDistrict) throws Exception {
                        ArrayList<String> arrOptions = new ArrayList<>();
                        for (int i = 0; i < responseDistrict.size(); i++) {
                            arrOptions.add(responseDistrict.get(i).getTitle());
                            arrDistrict.add(responseDistrict.get(i).getID());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lv_sl_address.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
        mSLAddress.setView(mView);
        AlertDialog dialog = mSLAddress.create();
        dialog.show();
        lv_sl_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_district.setText(lv_sl_address.getAdapter().getItem(position).toString());
                idDistrict = arrDistrict.get(position);
                dialog.cancel();
            }
        });
    }
    private void selectAddressWard() {
        AlertDialog.Builder mSLAddress = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_sl_address, null);
        ListView lv_sl_address = mView.findViewById(R.id.lv_sl_address);
        compositeDisposable.add(addressAPI.getAllWardInDistrict(idDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Ward>>() {
                    @Override
                    public void accept(List<Ward> responseWard) throws Exception {
                        ArrayList<String> arrOptions = new ArrayList<>();
                        for (int i = 0; i < responseWard.size(); i++) {
                            arrOptions.add(responseWard.get(i).getTitle());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lv_sl_address.setAdapter(adapter);
                    }
                }));
        mSLAddress.setView(mView);
        AlertDialog dialog = mSLAddress.create();
        dialog.show();
        lv_sl_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_ward.setText(lv_sl_address.getAdapter().getItem(position).toString());
                dialog.cancel();
            }
        });
    }
}