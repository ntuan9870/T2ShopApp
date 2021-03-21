package com.example.t2shop.Response;

import com.example.t2shop.Model.Voucher;

import java.util.List;

public class ResponseAllVoucher {
    private List<Voucher> vouchers;

    public ResponseAllVoucher(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
