package com.example.t2shop.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int user_id;
    private String user_name;
    private String user_password;
    private String user_email;
    private String user_phone;
    private int user_level;
    private int voucher_accumulation;
    private int voucher_user_score;
    private String created_at;
    private String updated_at;

    public User(int user_id, String user_name, String user_password, String user_email, String user_phone, int user_level, int voucher_accumulation, int voucher_user_score, String created_at, String updated_at) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_level = user_level;
        this.voucher_accumulation = voucher_accumulation;
        this.voucher_user_score = voucher_user_score;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getVoucher_accumulation() {
        return voucher_accumulation;
    }

    public void setVoucher_accumulation(int voucher_accumulation) {
        this.voucher_accumulation = voucher_accumulation;
    }

    public int getVoucher_user_score() {
        return voucher_user_score;
    }

    public void setVoucher_user_score(int voucher_user_score) {
        this.voucher_user_score = voucher_user_score;
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
}
