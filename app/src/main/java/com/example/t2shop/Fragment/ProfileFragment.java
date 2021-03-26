package com.example.t2shop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    public static String TAG = ProfileFragment.class.getName();
    private TextInputLayout edt_username, edt_email, edt_phone_number;
    private Button btn_confirm;
    private User user;
    private ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = UserDatabase.getInstance(getContext()).userDAO().getItems();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edt_username = view.findViewById(R.id.edt_username);
        edt_email = view.findViewById(R.id.edt_email);
        img_back = view.findViewById(R.id.img_back);
        edt_phone_number = view.findViewById(R.id.edt_phone_number);
        edt_username.getEditText().setText(user.getUser_name());
        edt_phone_number.getEditText().setText(user.getUser_phone());
        edt_email.getEditText().setText(user.getUser_email());
        btn_confirm = view.findViewById(R.id.btn_confirm);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        edt_username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUserName(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasChange()){
                    Toast.makeText(getContext(), "Bạn không thay đổi gì cả!", Toast.LENGTH_SHORT).show();
                }else {
                    if (validateEmail(edt_email.getEditText().getText())&&validateUserName(edt_username.getEditText().getText())){
                        ProgressDialog loading = new ProgressDialog(getContext());
                        loading.setTitle("Vui lòng chờ..");
                        loading.setMessage("Hoạt động sẽ tốn vài giây..");
                        loading.setCanceledOnTouchOutside(false);
                        loading.show();
                        Common.compositeDisposable.add(Common.it2ShopAPI.changeInformation(user.getUser_id(), edt_username.getEditText().getText().toString().trim(),
                                edt_email.getEditText().getText().toString().trim(), edt_phone_number.getEditText().getText().toString().trim())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<ResponseMessage>() {
                                    @Override
                                    public void accept(ResponseMessage responseChangeInformation) throws Exception {
                                        if (responseChangeInformation.getError()!=null){
                                            Common2.showDialogAutoClose(getContext(), "Không thành công!");
                                        } else if (responseChangeInformation.getSuccess().equals("Sửa đổi thông tin thành công!")){
                                            user.setUser_email(edt_email.getEditText().getText().toString().trim());
                                            user.setUser_name(edt_username.getEditText().getText().toString().trim());
                                            user.setUser_phone(edt_phone_number.getEditText().getText().toString().trim());
                                            UserDatabase.getInstance(getContext()).userDAO().update(user);
                                            Common2.showDialogAutoClose(getContext(), "Sửa đổi thông tin thành công!");
                                        }else{
                                            Common2.showDialogAutoClose(getContext(), "Không thành công!");
                                        }
                                        loading.dismiss();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Common2.showDialogAutoClose(getContext(), "Lỗi kết nối!");
                                        loading.dismiss();
                                    }
                                }));
                    }
                }
            }
        });
        return view;
    }

    private boolean hasChange() {
        if (!user.getUser_name().equals(edt_username.getEditText().getText().toString().trim())
        ||!user.getUser_email().equals(edt_email.getEditText().getText().toString().trim())
        ||!user.getUser_phone().equals(edt_phone_number.getEditText().getText().toString().trim())){
            return true;
        }
        return false;
    }

    private boolean validateEmail(CharSequence s) {
        if (s.length()==0){
            edt_email.setError("Email không được trống!");
            return false;
        }
        if (!isValidEmail(s)){
            edt_email.setError("Email sai định dạng!");
            return false;
        }
        edt_email.setError(null);
        if (!edt_email.getEditText().getText().toString().trim().equals(user.getUser_email())){
            checkSameEmail();
        }
        return true;
    }

    public void checkSameEmail() {
        Common.compositeDisposable.add(Common.it2ShopAPI.checkSameEmail(edt_email.getEditText().getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage responseMessage) throws Exception {
                        if (responseMessage.getError()!=null) {
                            edt_email.setError("Email đã tồn tại!");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private boolean validateUserName(CharSequence s) {
        if (s.length()==0){
            edt_username.setError("Tên hiển thị không được để trống!");
            return false;
        }else if (s.length()>15){
            edt_username.setError("Tên hiển thị quá dài!");
            return false;
        }
        edt_username.setError(null);
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}