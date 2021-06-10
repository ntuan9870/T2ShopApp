package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;

public class PersonalFragment extends Fragment {

    public static TextView txt_login_register, txt_name_1;
    public static Button btn_log_out;
    private LinearLayout ln_order_profile, ln_change_password, ln_profile, ln_message_profile,ln_favorite_profile, ln_voucher_profile;
    public static String TAG = PersonalFragment.class.getName();
    public static RelativeLayout rl_log_out;
    private User user;
    public static ImageView img_avatar_personal, img_cart_personal;

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
        img_avatar_personal = view.findViewById(R.id.img_avatar_personal);
        txt_name_1 = view.findViewById(R.id.txt_name_1);
        btn_log_out = view.findViewById(R.id.btn_log_out);
        ln_order_profile = view.findViewById(R.id.ln_order_profile);
        ln_change_password = view.findViewById(R.id.ln_change_password);
        ln_profile = view.findViewById(R.id.ln_profile);
        ln_message_profile = view.findViewById(R.id.ln_message_profile);
        ln_favorite_profile = view.findViewById(R.id.ln_favorite_profile);
        ln_voucher_profile = view.findViewById(R.id.ln_voucher_profile);
        rl_log_out = view.findViewById(R.id.rl_log_out);
        img_cart_personal = view.findViewById(R.id.img_cart_personal);
        img_cart_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                CartFragment cartFragment = new CartFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, cartFragment);
                transaction.addToBackStack(CartFragment.TAG);
                transaction.commit();
            }
        });
        ln_message_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                ChatBotFragment chatBotFragment = new ChatBotFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, chatBotFragment);
                transaction.addToBackStack(ChatBotFragment.TAG);
                transaction.commit();
            }
        });
        ln_voucher_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(3);
                tab.select();
            }
        });
        ln_favorite_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, favoriteFragment);
                transaction.addToBackStack(FavoriteFragment.TAG);
                transaction.commit();
            }
        });
        user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        if (user==null){
            login();
        }else{
            btn_log_out.setVisibility(View.VISIBLE);
            txt_login_register.setText(user.getUser_name());
            txt_name_1.setText(user.getUser_email());
        }
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null){
                    user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
                }
                T2ShopDatabase.getInstance(getContext()).userDAO().delete(user);
                txt_login_register.setText("Đăng nhập/Đăng ký");
                txt_name_1.setText("Chào mừng bạn đến với T2Shop");
                login();
                LoginManager.getInstance().logOut();
                Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            }
        });
        ln_order_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
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
                user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
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
                user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
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
        rl_log_out.setBackgroundColor(0xFFFFFFFF);
        btn_log_out.setVisibility(View.INVISIBLE);
    }
}