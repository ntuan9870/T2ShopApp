package com.example.t2shop.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.t2shop.CallBack.RetrofitClientAddress;
import com.example.t2shop.Retrofit.AddressAPI;

import io.reactivex.disposables.CompositeDisposable;

public class RetrofitAPIAddress {
    public static AddressAPI addressAPI;
    public static CompositeDisposable compositeDisposable;
    public static AddressAPI getAPI(){
        return RetrofitClientAddress.getInstance().create(AddressAPI.class);
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
