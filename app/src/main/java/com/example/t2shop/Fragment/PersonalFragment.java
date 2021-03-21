package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;

import java.util.List;

public class PersonalFragment extends Fragment {

    public static TextView txt_login_register, txt_name_1;
    public static Button btn_log_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        txt_login_register = view.findViewById(R.id.txt_login_register);
        txt_name_1 = view.findViewById(R.id.txt_name_1);
        btn_log_out = view.findViewById(R.id.btn_log_out);
        User user = UserDatabase.getInstance(getContext()).userDAO().getItems();
        if (user==null){
            login();
        }else{
            btn_log_out.setVisibility(View.VISIBLE);
            txt_login_register.setText(user.getUser_name());
            txt_name_1.setText(user.getUser_email());
            btn_log_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDatabase.getInstance(getContext()).userDAO().delete(user);
                    txt_login_register.setText("Đăng nhập/Đăng ký");
                    txt_name_1.setText("Chào mừng bạn đến với T2Shop");
                    Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
                    login();
                }
            });
        }
        return view;
    }

    private void login() {
        btn_log_out.setVisibility(View.GONE);
        txt_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, loginFragment);
                transaction.addToBackStack(LoginFragment.TAG);
                transaction.commit();
            }
        });
    }
}