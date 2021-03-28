package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseMessage;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordFragment extends Fragment {

    public static String TAG = ForgotPasswordFragment.class.getName();
    private ImageView img_back;
    private TextInputLayout edt_email, edt_code;
    private Button btn_confirm, btn_send_code;
    private TextView txt_time_count_down;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        LinearLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_back = view.findViewById(R.id.img_back);
        edt_email = view.findViewById(R.id.edt_email);
        edt_code = view.findViewById(R.id.edt_code);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_send_code = view.findViewById(R.id.btn_send_code);
        txt_time_count_down = view.findViewById(R.id.txt_time_count_down);
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
        edt_code.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateCode();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail()){
                    SweetAlertDialog sweetAlertDialog = Common2.loadingDialog(getContext(), "Chờ xíu..");
                    sweetAlertDialog.show();
                    Common.compositeDisposable.add(Common.it2ShopAPI.sendEmail(edt_email.getEditText().getText().toString().trim())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseMessage>() {
                        @Override
                        public void accept(ResponseMessage responseMessage) throws Exception {
                            if (responseMessage.getMessage()!=null){
                                if (responseMessage.getMessage().equals("success")){
                                    Common2.showDialogAutoClose(getContext(), "Bạn có 180s để nhập mã!");
                                    new CountDownTimer(180000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                            String s = String.format("%02d:%02d:%02d",
                                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)%60,
                                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                                            txt_time_count_down.setText("" + s);
                                        }

                                        public void onFinish() {
                                            txt_time_count_down.setText("Chờ..");
                                        }
                                    }.start();
                                }else if (responseMessage.getMessage().equals("fail")){
                                    Common2.showDialogAutoClose(getContext(), "Email không tồn tại trong hệ thống!");
                                }else if (responseMessage.getMessage().equals("fail2")){
                                    Common2.showDialogAutoClose(getContext(), "Email đã được gửi mã rồi!");
                                }else if (responseMessage.getMessage().equals("fail3")){
                                    Common2.showDialogAutoClose(getContext(), "Vui lòng chờ đủ 3 phút sau khi bạn không kịp gửi mã!");
                                }
                                sweetAlertDialog.dismiss();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            sweetAlertDialog.dismiss();
                            Common2.showDialogAutoClose(getContext(), "Lỗi kết nối"+throwable.getMessage());
                        }
                    }));
                }
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCode()&&validateEmail()){
                    SweetAlertDialog sweetAlertDialog = Common2.loadingDialog(getContext(), "Chờ xíu..");
                    sweetAlertDialog.show();
                    Common.compositeDisposable.add(Common.it2ShopAPI.getCode(edt_email.getEditText().getText().toString().trim())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseMessage>() {
                                @Override
                                public void accept(ResponseMessage responseMessage) throws Exception {
                                    if (responseMessage.getMessage()!=null){
                                        if (responseMessage.getMessage().equals("success")){
                                            if (edt_code.getEditText().getText().toString().trim().equals(responseMessage.getCode())){
                                                Common2.showDialogAutoClose(getContext(), "Xác nhận thành công!");
                                                Bundle bundle = new Bundle();
                                                bundle.putString("user_email", edt_email.getEditText().getText().toString().trim());
                                                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                                                ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                                                resetPasswordFragment.setArguments(bundle);
                                                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                                                transaction.replace(R.id.main_frame, resetPasswordFragment);
                                                transaction.addToBackStack(ResetPasswordFragment.TAG);
                                                transaction.commit();
                                            }else{
                                                Common2.showDialogAutoClose(getContext(), "Mã không đúng, vui lòng thử lại!");
                                            }
                                        }else if (responseMessage.getMessage().equals("wait")){
                                            Common2.showDialogAutoClose(getContext(), "Bạn cần đợi thêm 3 phút kể từ thời điểm kết thúc thời gian nhập mã cũ!");
                                        }else if (responseMessage.getMessage().equals("error")){
                                            Common2.showDialogAutoClose(getContext(), "Có lỗi, vui lòng thử lại!");
                                        }else if (responseMessage.getMessage().equals("clear")){
                                            Common2.showDialogAutoClose(getContext(), "Bạn chưa nhấn gửi Mail");
                                        }
                                        sweetAlertDialog.dismiss();
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Common2.showDialogAutoClose(getContext(), "Lỗi kết nối");
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

    private boolean validateCode(){
        String code = edt_code.getEditText().getText().toString().trim();
        if(code.isEmpty()){
            edt_email.setError("Vui lòng nhập mã xác nhận!");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String emailInput = edt_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            edt_email.setError("Email không được trống!");
            return false;
        }
        if (!isValidEmail(emailInput)){
            edt_email.setError("Email sai định dạng!");
            return false;
        }
        edt_email.setError(null);
        return true;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}