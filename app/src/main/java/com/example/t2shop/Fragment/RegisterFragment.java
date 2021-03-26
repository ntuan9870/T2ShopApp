package com.example.t2shop.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterFragment extends Fragment {

    private TextInputLayout txt_email_register, txt_password_register, txt_re_password_register, txt_phone_register, txt_user_register;
    private Button btn_register;
    public static String TAG = RegisterFragment.class.getName();
    private RelativeLayout ln_t2shop;
    private LinearLayout ln_register;
    private int scroll_distance = 1000;
    private View view;
    private NestedScrollView scroll_register;
    private ImageView img_close_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        txt_email_register = view.findViewById(R.id.txt_email_register);
        txt_password_register = view.findViewById(R.id.txt_password_register);
        txt_re_password_register = view.findViewById(R.id.txt_re_password_register);
        txt_phone_register = view.findViewById(R.id.txt_phone_register);
        btn_register = view.findViewById(R.id.btn_register);
        txt_user_register = view.findViewById(R.id.txt_user_register);
        ln_t2shop = view.findViewById(R.id.ln_t2shop);
        ln_register = view.findViewById(R.id.ln_register);
        scroll_register = view.findViewById(R.id.scroll_register);
        img_close_register = view.findViewById(R.id.img_close_register);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        ln_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKey();
            }
        });
        txt_email_register.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scroll_distance = 0;
                openKey();
            }
        });
        txt_password_register.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scroll_distance = 500;
                openKey();
            }
        });
        txt_re_password_register.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scroll_distance = 1000;
                openKey();
            }
        });
        txt_user_register.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scroll_distance = 1000;
                openKey();
            }
        });
        txt_phone_register.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scroll_distance = 2000;
                openKey();
            }
        });
        txt_email_register.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_password_register.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_user_register.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUserName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_re_password_register.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateRePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmInput()){
                    Common.compositeDisposable.add(Common.it2ShopAPI.register(
                            txt_user_register.getEditText().getText().toString().trim(),txt_password_register.getEditText().getText().toString().trim()
                            ,txt_email_register.getEditText().getText().toString().trim(),txt_phone_register.getEditText().getText().toString().trim()
                    )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseMessage>() {
                        @Override
                        public void accept(ResponseMessage responseMessage) throws Exception {
                            if (responseMessage.getSuccess().equals("Đăng ký thành công!")){
                                Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                closeKey();
                                getFragmentManager().popBackStack();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối INTERNET!"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        });
        img_close_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private boolean validateUserName() {
        String userNameInput = txt_user_register.getEditText().getText().toString().trim();
        if (userNameInput.length()>15){
            txt_user_register.setError("Tên hiển thị quá dài!");
            return false;
        }
        return true;
    }

    private boolean validateRePassword() {
        String rePasswordInput = txt_re_password_register.getEditText().getText().toString().trim();
        String passwordInput = txt_password_register.getEditText().getText().toString().trim();
        if (rePasswordInput.isEmpty()){
            txt_re_password_register.setError("Nhập lại mật khẩu không được trống!");
            return false;
        }
        if (!rePasswordInput.equals(passwordInput)){
            txt_re_password_register.setError("Nhập lại mật khẩu không trùng khớp!");
            return false;
        }
        txt_re_password_register.setError(null);
        return true;
    }

    private boolean confirmInput() {
        if (!validateEmail()||!validatePassword()||!validateRePassword()||!validateUserName()){
            return false;
        }

       return true;
    }

    private boolean validatePassword() {
        String passwordInput = txt_password_register.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()){
            txt_password_register.setError("Mật khẩu không được trống!");
            return false;
        }
        if (passwordInput.length()<8){
            txt_password_register.setError("Mật khẩu phải có ít nhất 8 ký tự!");
            return false;
        }
        txt_password_register.setError(null);
        return true;
    }

    private boolean validateEmail() {
        String emailInput = txt_email_register.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            txt_email_register.setError("Email không được trống!");
            return false;
        }
        if (!isValidEmail(emailInput)){
            txt_email_register.setError("Email sai định dạng!");
            return false;
        }
        txt_email_register.setError(null);
        return true;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void openKey(){
        ln_register.setPadding(ln_register.getPaddingLeft(),ln_register.getPaddingTop(), ln_register.getPaddingRight(), scroll_distance* Constants.SCREEN_HEIGHT/1920);
        scroll();
    }

    private void scroll() {

        View lastChild = scroll_register.getChildAt(scroll_register.getChildCount() - 1);
        int bottom = lastChild.getBottom() + scroll_register.getPaddingBottom();
        int sy = scroll_register.getScrollY();
        int sh = scroll_register.getHeight();
        int delta = bottom - (sy + sh);

        scroll_register.smoothScrollBy(0, delta);
    }

    public void closeKey(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt_email_register.getEditText().getWindowToken(),0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                ln_t2shop.getLayoutParams();
        params.weight = 4.0f;
        ln_t2shop.setLayoutParams(params);
    }
}