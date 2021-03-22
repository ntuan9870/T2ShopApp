package com.example.t2shop.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.t2shop.Common.RetrofitAPIAddress;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseCity;
import com.example.t2shop.Retrofit.AddressAPI;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckoutFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private AddressAPI addressAPI;
    public static String TAG = CheckoutFragment.class.getName();
    private TextInputLayout text_input_user_name, text_input_phone_number, text_input_user_address;
    private Spinner spinner_city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        compositeDisposable = new CompositeDisposable();
        addressAPI = RetrofitAPIAddress.getAPI();
        View view = inflater.inflate(R.layout.fragment_check_out, container, false);
        text_input_user_name = view.findViewById(R.id.text_input_user_name);
        text_input_phone_number = view.findViewById(R.id.text_input_phone_number);
        text_input_user_address = view.findViewById(R.id.text_input_user_address);
        spinner_city = view.findViewById(R.id.spinner_city);
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
        compositeDisposable.add(addressAPI.getAllCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseCity>() {
                    @Override
                    public void accept(ResponseCity responseCity) throws Exception {
                        ArrayList<String> arrOptions = new ArrayList<>();
                        for (int i = 0; i < responseCity.getLtsItem().size(); i++){
                            arrOptions.add(responseCity.getLtsItem().get(i).getTitle());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrOptions);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_city.setSelection(3);
                        spinner_city.setAdapter(adapter);
                    }
                }));
        return view;
    }
}