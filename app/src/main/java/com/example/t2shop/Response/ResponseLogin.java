package com.example.t2shop.Response;

import com.example.t2shop.Model.User;

public class ResponseLogin {
    private User user;
    private String result;

    public ResponseLogin(User user, String result) {
        this.user = user;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
