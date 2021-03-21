package com.example.t2shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseLogin;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    private ImageView img_close_login;
    public static String TAG = LoginFragment.class.getName();
    private TextView edt_email, edt_password;
    private Button btn_login;
    private LinearLayout rl_login;
    private RelativeLayout rl_logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        img_close_login = view.findViewById(R.id.img_close_login);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        btn_login = view.findViewById(R.id.btn_login);
        rl_login = view.findViewById(R.id.rl_login);
        rl_logo = view.findViewById(R.id.rl_logo);
        img_close_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKey();
                getFragmentManager().popBackStack();
            }
        });
        if (edt_email.getText().equals("")){
            edt_email.setError("Vui lòng nhập email");
        }
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isValidEmail(s)){
                    edt_email.setError("Vui lòng nhập email đúng định dạng");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (edt_password.getText().equals("")){
            edt_password.setError("Vui lòng nhập mật khẩu");
        }
        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeKeyboardState();
            }
        });
        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeKeyboardState();
            }
        });
        edt_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKey();
                }
                return false;
            }
        });
        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKey();
                }
                return false;
            }
        });
        edt_email.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_password.getText().length()<8){
                    edt_password.setError("Vui lòng nhập mật khẩu lớn hơn 7 kí tự");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                Common.compositeDisposable.add(Common.getAPI().login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseLogin>() {
                    @Override
                    public void accept(ResponseLogin responseLogin) throws Exception {
                        if (responseLogin.getResult().equals("success")){
                            User user = responseLogin.getUser();
                            UserDatabase.getInstance(getContext()).userDAO().insert(user);
                            PersonalFragment.txt_login_register.setText(user.getUser_name());
                            PersonalFragment.txt_name_1.setText(user.getUser_email());
                            PersonalFragment.btn_log_out.setVisibility(View.VISIBLE);
                            getFragmentManager().popBackStack();
                            Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Email hoặc mật khẩu chưa đúng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKey();
            }
        });
        return view;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void changeKeyboardState(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            imm.showSoftInput(edt_password, InputMethodManager.SHOW_FORCED);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    rl_logo.getLayoutParams();
            params.weight = 1.0f;
            rl_logo.setLayoutParams(params);
        }else{
            closeKey();
        }
    }
    public void closeKey(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_password.getWindowToken(),0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                rl_logo.getLayoutParams();
        params.weight = 4.0f;
        rl_logo.setLayoutParams(params);
    }
}