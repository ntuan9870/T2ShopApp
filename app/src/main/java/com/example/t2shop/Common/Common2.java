package com.example.t2shop.Common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Common2 {
    public static void showDialogAutoClose(Context context, String message){
        SweetAlertDialog dialog = new SweetAlertDialog(context);
        dialog.setTitleText(message);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    public static void confirmDialog(Context context, String messageConfirm, String messageContent, String messageCText){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(messageConfirm)
                .setContentText(messageContent)
                .setConfirmText(messageCText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public static void errorDialog(Context context, String message){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Lỗi rồi..")
                .setContentText(message)
                .show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    public static SweetAlertDialog loadingDialog(Context context, String message){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(message + " ...");
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);
        return pDialog;
    }

    public static void confirmDialog(Context context, String message){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Bạn chắc chắn chứ")
                .setContentText("Bạn sẽ không thể khôi phục dữ liệu đâu đấy!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Đã xác nhận!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }
}
