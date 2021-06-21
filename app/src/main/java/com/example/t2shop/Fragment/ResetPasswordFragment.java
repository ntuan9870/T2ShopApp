package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordFragment extends Fragment {

    private TextInputLayout edt_old_password, edt_re_new_password, edt_new_password;
    private Button btn_confirm;
    public static String TAG = RegisterFragment.class.getName();
    private ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        edt_old_password = view.findViewById(R.id.edt_old_password);
        edt_re_new_password = view.findViewById(R.id.edt_re_new_password);
        edt_new_password = view.findViewById(R.id.edt_new_password);
        img_back = view.findViewById(R.id.img_back);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        edt_new_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateNewPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_re_new_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateReNewPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNewPassword()&&validateReNewPassword()){
                    Bundle bundle = getArguments();
                    String user_email = bundle.getString("user_email");
                    if (user_email==null){
                        User user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
                        user_email = user.getUser_email();
                    }
                    Common.compositeDisposable.add(Common.it2ShopAPI.changePassword(edt_new_password.getEditText().getText().toString().trim(), user_email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseMessage>() {
                        @Override
                        public void accept(ResponseMessage responseMessage) throws Exception {
                            if (responseMessage.getMessage()!=null){
                                if (responseMessage.getMessage().equals("success")){
                                    Common2.showDialogAutoClose(getContext(), "Đổi mật khẩu thành công!");
                                    if(bundle.getString("user_email")!=null){
                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                            fm.popBackStack();
                                        }
                                        FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                                        LoginFragment loginFragment = new LoginFragment();
                                        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                                        transaction.replace(R.id.main_frame, loginFragment);
                                        transaction.commit();
                                    }
                                }else{
                                    Common2.errorDialog(getContext(), "Thất bại!");
                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Common2.errorDialog(getContext(), "Lỗi kết nối!"+throwable.getMessage());
                        }
                    }));
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private boolean validateReNewPassword() {
        String new_pass = edt_new_password.getEditText().getText().toString().trim();
        String re_new_pass = edt_re_new_password.getEditText().getText().toString().trim();
        Log.d("AAA", new_pass);
        Log.d("BBB", re_new_pass);
        if (edt_re_new_password.getEditText().length()==0){
            edt_re_new_password.setError("Vui lòng nhập lại mật khẩu mới!");
            return false;
        }
        if (!re_new_pass.equals(new_pass)){
            edt_re_new_password.setError("Nhập lại mật khẩu mới không trùng khớp!");
            return false;
        }
        edt_re_new_password.setError(null);
        return true;
    }
    private boolean validateNewPassword() {
        if (edt_new_password.getEditText().length()==0){
            edt_new_password.setError("Vui lòng nhập mật khẩu mới!");
            return false;
        }else{
            edt_new_password.setError(null);
        }
        return true;
    }
}