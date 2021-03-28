package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.ItemCartDatabase;
import com.example.t2shop.Database.UserDatabase;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;

import java.util.List;

public class PersonalFragment extends Fragment {

    public static TextView txt_login_register, txt_name_1;
    public static Button btn_log_out;
    private LinearLayout ln_order_profile, ln_change_password, ln_profile;
    public static String TAG = PersonalFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        RelativeLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_login_register = view.findViewById(R.id.txt_login_register);
        txt_name_1 = view.findViewById(R.id.txt_name_1);
        btn_log_out = view.findViewById(R.id.btn_log_out);
        ln_order_profile = view.findViewById(R.id.ln_order_profile);
        ln_change_password = view.findViewById(R.id.ln_change_password);
        ln_profile = view.findViewById(R.id.ln_profile);
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
        ln_order_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null) {
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(OrderManagerFragment.TAG);
                    OrderManagerFragment orderManagerFragment = new OrderManagerFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    fragmentTransaction.replace(R.id.main_frame, orderManagerFragment);
                    fragmentTransaction.commit();
                }else{
                    Common2.errorDialog(getContext(),"Bạn chưa đăng nhập!");
                }
            }
        });
        ln_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null) {
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(ProfileFragment.TAG);
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    fragmentTransaction.replace(R.id.main_frame, profileFragment);
                    fragmentTransaction.commit();
                }else{
                    Common2.errorDialog(getContext(),"Bạn chưa đăng nhập!");
                }
            }
        });
        ln_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null) {
                    FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(ResetPasswordFragment.TAG);
                    ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                    fragmentTransaction.replace(R.id.main_frame, resetPasswordFragment);
                    fragmentTransaction.commit();
                }else{
                    Common2.errorDialog(getContext(),"Bạn chưa đăng nhập!");
                }
            }
        });
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