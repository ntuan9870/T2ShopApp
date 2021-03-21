package com.example.t2shop.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Voucher {
    private int voucher_id;
    private int user_id;
    private int amount_voucher;
    private int voucher_used;
    private String created_at;
    private String updated_at;
    private String voucher_name;
    private int voucher_score;
    private int voucher_value;
    private int voucher_discount;
    private int voucher_price;
    private int voucher_total;
    private String voucher_start;
    private String voucher_end;
    private String voucher_apply;
    private int user_voucher_id;

    public Voucher(int voucher_id, int user_id, int amount_voucher, int voucher_used, String created_at, String updated_at, String voucher_name, int voucher_score, int voucher_value, int voucher_discount, int voucher_price, int voucher_total, String voucher_start, String voucher_end, String voucher_apply, int user_voucher_id) {
        this.voucher_id = voucher_id;
        this.user_id = user_id;
        this.amount_voucher = amount_voucher;
        this.voucher_used = voucher_used;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.voucher_name = voucher_name;
        this.voucher_score = voucher_score;
        this.voucher_value = voucher_value;
        this.voucher_discount = voucher_discount;
        this.voucher_price = voucher_price;
        this.voucher_total = voucher_total;
        this.voucher_start = voucher_start;
        this.voucher_end = voucher_end;
        this.voucher_apply = voucher_apply;
        this.user_voucher_id = user_voucher_id;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAmount_voucher() {
        return amount_voucher;
    }

    public void setAmount_voucher(int amount_voucher) {
        this.amount_voucher = amount_voucher;
    }

    public int getVoucher_used() {
        return voucher_used;
    }

    public void setVoucher_used(int voucher_used) {
        this.voucher_used = voucher_used;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getVoucher_name() {
        return voucher_name;
    }

    public void setVoucher_name(String voucher_name) {
        this.voucher_name = voucher_name;
    }

    public int getVoucher_score() {
        return voucher_score;
    }

    public void setVoucher_score(int voucher_score) {
        this.voucher_score = voucher_score;
    }

    public int getVoucher_value() {
        return voucher_value;
    }

    public void setVoucher_value(int voucher_value) {
        this.voucher_value = voucher_value;
    }

    public int getVoucher_discount() {
        return voucher_discount;
    }

    public void setVoucher_discount(int voucher_discount) {
        this.voucher_discount = voucher_discount;
    }

    public int getVoucher_price() {
        return voucher_price;
    }

    public void setVoucher_price(int voucher_price) {
        this.voucher_price = voucher_price;
    }

    public int getVoucher_total() {
        return voucher_total;
    }

    public void setVoucher_total(int voucher_total) {
        this.voucher_total = voucher_total;
    }

    public String getVoucher_start() {
        return voucher_start;
    }

    public void setVoucher_start(String voucher_start) {
        this.voucher_start = voucher_start;
    }

    public String getVoucher_end() {
        return voucher_end;
    }

    public void setVoucher_end(String voucher_end) {
        this.voucher_end = voucher_end;
    }

    public String getVoucher_apply() {
        return voucher_apply;
    }

    public void setVoucher_apply(String voucher_apply) {
        this.voucher_apply = voucher_apply;
    }

    public int getUser_voucher_id() {
        return user_voucher_id;
    }

    public void setUser_voucher_id(int user_voucher_id) {
        this.user_voucher_id = user_voucher_id;
    }
}
