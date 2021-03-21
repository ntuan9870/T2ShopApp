package com.example.t2shop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        img_close_login = view.findViewById(R.id.img_close_login);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        btn_login = view.findViewById(R.id.btn_login);
        img_close_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
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
        return view;
    }
}