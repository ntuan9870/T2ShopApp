package com.example.t2shop.Response;

public class ResponseSuccess {
    private String success;
    private String error;

    public ResponseSuccess() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
