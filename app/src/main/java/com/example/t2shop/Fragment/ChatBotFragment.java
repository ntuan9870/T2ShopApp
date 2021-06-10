package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.t2shop.Adapter.ChatBotAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.User;
import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseAllVoucher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChatBotFragment extends Fragment {
    public static final String TAG = ChatBotFragment.class.getName();
    private RecyclerView rv_chat_bot;
    private User user;
    private List<Voucher> voucherList = new ArrayList<>();
    private ArrayList<String> arrMessages = new ArrayList<>();
    private ImageView btn_exit_chat_bot;
    private RelativeLayout rl_chat_bot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_bot, container, false);
        rv_chat_bot = view.findViewById(R.id.rv_chat_bot);
        btn_exit_chat_bot = view.findViewById(R.id.btn_exit_chat_bot);
        rl_chat_bot = view.findViewById(R.id.rl_chat_bot);
        rl_chat_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        arrMessages.add("Xin chào bạn, bạn khỏe không?");
        if (user!=null){
            getMessages();
        }
        btn_exit_chat_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void getMessages() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getAllVoucher(user.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseAllVoucher>() {
                    @Override
                    public void accept(ResponseAllVoucher responseAllVoucher) throws Exception {
                        voucherList = responseAllVoucher.getVouchers();
                        for (int i = 0; i < responseAllVoucher.getVouchers().size(); i++){
                            arrMessages.add("Bạn được tặng " + responseAllVoucher.getVouchers().get(i).getVoucher_name() + " \nHết hạn vào ngày " +
                                    responseAllVoucher.getVouchers().get(i).getVoucher_end() + "\nHãy mua hàng ngay để không lỡ cơ hội!");
                        }
                        ChatBotAdapter adapter = new ChatBotAdapter(getContext(), arrMessages);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_chat_bot.setLayoutManager(linearLayoutManager);
                        rv_chat_bot.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Opp, lỗi kìa!", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}