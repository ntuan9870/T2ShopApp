package com.example.t2shop.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseLogin;
import com.example.t2shop.Response.ResponseMessage;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    private ImageView img_close_login, img_login_fb, img_login_gg;
    public static String TAG = LoginFragment.class.getName();
    private TextView txt_register_login, txt_forgot_password;
    private Button btn_login;
    private LinearLayout rl_login;
    private RelativeLayout rl_logo;
    private NestedScrollView scrollView;
    private TextInputLayout edt_email, edt_password;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String a = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        NestedScrollView rl_root = view.findViewById(R.id.scrollView);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_close_login = view.findViewById(R.id.img_close_login);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        btn_login = view.findViewById(R.id.btn_login);
        rl_login = view.findViewById(R.id.rl_login);
        rl_logo = view.findViewById(R.id.rl_logo);
        txt_register_login = view.findViewById(R.id.txt_register_login);
        scrollView = view.findViewById(R.id.scrollView);
        txt_forgot_password = view.findViewById(R.id.txt_forgot_password);
        img_login_gg = view.findViewById(R.id.img_login_gg);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
                fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                fragmentTransaction.replace(R.id.main_frame, forgotPasswordFragment);
                fragmentTransaction.addToBackStack(ForgotPasswordFragment.TAG);
                fragmentTransaction.commit();
            }
        });
        txt_register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                fragmentTransaction.replace(R.id.main_frame, registerFragment);
                fragmentTransaction.addToBackStack(RegisterFragment.TAG);
                fragmentTransaction.commit();
            }
        });
        img_close_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKey();
                getFragmentManager().popBackStack();
            }
        });
        edt_email.getEditText().addTextChangedListener(new TextWatcher() {
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
        edt_password.getEditText().addTextChangedListener(new TextWatcher() {
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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail()&&validatePassword()){
                    String email = edt_email.getEditText().getText().toString();
                    String password = edt_password.getEditText().getText().toString();
                    Common.compositeDisposable.add(Common.getAPI().login(email, password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseLogin>() {
                                @Override
                                public void accept(ResponseLogin responseLogin) throws Exception {
                                    if (responseLogin.getResult().equals("success")){
                                        User user = responseLogin.getUser();
                                        if (user.getUser_level()==3)
                                        {
                                            loginSuccess(user);
                                        }else{
                                            loginFail();
                                        }
                                    }else {
                                        loginFail();
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
//                                    Toast.makeText(getContext(), "Lỗi kết nối!"+throwable.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    loginFail();
                                }
                            }));
                }
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKey();
            }
        });
        loginWithFB();
        loginWithGoogle();
        return view;
    }

    private void loginSuccess(User user) {
        T2ShopDatabase.getInstance(getContext()).userDAO().insert(user);
        PersonalFragment.txt_login_register.setText(user.getUser_name());
        PersonalFragment.txt_name_1.setText(user.getUser_email());
        PersonalFragment.btn_log_out.setVisibility(View.VISIBLE);
        PersonalFragment.rl_log_out.setBackgroundColor(0xFFE6E6E6);
        Bundle bundle = getArguments();
        try {
            String str= bundle.getString("checkouting");
            FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
            CheckoutFragment checkoutFragment = new CheckoutFragment();
            transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
            transaction.replace(R.id.main_frame, checkoutFragment);
            transaction.commit();
        }catch (Exception e){
            getFragmentManager().popBackStack();
        }
        SharedPreferences sharedPref = getContext().getSharedPreferences("wrongInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("times", 0);
        editor.commit();
    }


    private void loginFail() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("wrongInfo", Context.MODE_PRIVATE);
        int times = 0;
        if (sharedPref!=null){
            times = sharedPref.getInt("times", 1);
        }
        if (times < 3){
            SharedPreferences.Editor editor = sharedPref.edit();
            times+=1;
            editor.putInt("times", times);
            editor.commit();
            Common2.confirmDialog(getContext(), "Email hoặc mật khẩu chưa đúng!", "Lưu ý: Nếu nhập sai quá 3 lần bạn sẽ bị chuyển sang quên mật khẩu!","Tôi hiểu rồi");
        }else{
            FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            fragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
            fragmentTransaction.replace(R.id.main_frame, forgotPasswordFragment);
            fragmentTransaction.addToBackStack(ForgotPasswordFragment.TAG);
            fragmentTransaction.commit();
            Common2.showDialogAutoClose(getContext(), "Hệ thống sẽ giúp bạn khôi phục mật khẩu!");
        }
    }

    private boolean validatePassword() {
        if (edt_password.getEditText().getText().equals("")){
            edt_password.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        if (edt_password.getEditText().getText().length()<8){
            edt_password.setError("Vui lòng nhập mật khẩu lớn hơn 7 kí tự");
            return false;
        }
        edt_password.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmail() {
        if (edt_email.getEditText().getText().equals("")){
            edt_email.setError("Vui lòng nhập email");
            return false;
        }
        if (!isValidEmail(edt_email.getEditText().getText())){
            edt_email.setError("Vui lòng nhập email đúng định dạng");
            return false;
        }
        edt_email.setErrorEnabled(false);
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void changeKeyboardState(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            imm.showSoftInput(edt_password, InputMethodManager.SHOW_FORCED);
        }else{
            closeKey();
        }
    }
    public void closeKey(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_password.getWindowToken(),0);
    }

    private void loginWithGoogle() {
        img_login_gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loginWithFB() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setFragment(LoginFragment.this);
        loginButton.setReadPermissions(Arrays.asList("user_gender, user_friends, email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getContext(), "Error"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("AAA", object.toString());
                        User user = new User();
                        user.setUser_level(3);
                        try {
                            String user_name = object.getString("name");
                            user.setUser_name(user_name);
                            String id = object.getString("id");
                            Glide.with(getContext()).load("https://graph.facebook.com/"+id+"/picture?type=large").into(PersonalFragment.img_avatar_personal);
                            String user_email = object.getString("email");
//                            Picasso.get().load("https://graph.facebook.com/"+id+"/picture?width=150&width=150").into(PersonalFragment.img_avatar_personal);
                            user.setUser_email(user_email);
                            double tmp = Double.parseDouble(id);
                            int a = (int)tmp%1000;
                            user.setUser_id(a);
                            checkLoginSocial(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("AAA","gfdgfd"+e.getMessage().toString());
                        }
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender, name, id, first_name, last_name, email");
        request.setParameters(bundle);
        request.executeAsync();
    }

    private void checkLoginSocial(User user) {
        Common.compositeDisposable.add(Common.it2ShopAPI.checkEmailSocial(user.getUser_email(), user.getUser_name())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseMessage>() {
            @Override
            public void accept(ResponseMessage message) throws Exception {
                loginSuccess(message.getUser());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}