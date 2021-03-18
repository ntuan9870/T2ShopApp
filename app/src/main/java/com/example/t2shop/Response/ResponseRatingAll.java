package com.example.t2shop.Response;

import java.util.List;

public class ResponseRatingAll {
    private List<Integer> arrcount;

    public ResponseRatingAll(List<Integer> arrcount) {
        this.arrcount = arrcount;
    }

    public List<Integer> getArrcount() {
        return arrcount;
    }

    public void setArrcount(List<Integer> arrcount) {
        this.arrcount = arrcount;
    }
}
