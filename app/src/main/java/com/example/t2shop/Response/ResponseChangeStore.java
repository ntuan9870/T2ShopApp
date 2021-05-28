package com.example.t2shop.Response;

import java.util.ArrayList;

public class ResponseChangeStore {
    private ArrayList<String> message;
    private ArrayList<Integer> max;

    public ResponseChangeStore() {
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public ArrayList<Integer> getMax() {
        return max;
    }

    public void setMax(ArrayList<Integer> max) {
        this.max = max;
    }
}
