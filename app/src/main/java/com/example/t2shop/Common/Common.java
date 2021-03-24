package com.example.t2shop.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.t2shop.CallBack.RetrofitClient;
import com.example.t2shop.Retrofit.IT2ShopAPI;

import io.reactivex.disposables.CompositeDisposable;

public class Common {
    public static IT2ShopAPI it2ShopAPI;
    public static CompositeDisposable compositeDisposable;

    public static IT2ShopAPI getAPI(){
        return RetrofitClient.getInstance().create(IT2ShopAPI.class);
    }
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info!=null){
                for(int i = 0; i < info.length; i++){
                    if(info[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
