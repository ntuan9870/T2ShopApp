package com.example.t2shop.Model;

public class Ward {
    private int Type;
    private String SolrID;
    private int ID;
    private String Title;
    private int STT;
    private int TinhThanhID;
    private String TinhThanhTitle;
    private String TinhThanhTitleAscii;
    private int QuanHuyenID;
    private String QuanHuyenTitle;
    private String QuanHuyenTitleAscii;
    private String Created;
    private String Updated;

    public Ward() {
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getSolrID() {
        return SolrID;
    }

    public void setSolrID(String solrID) {
        SolrID = solrID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public int getTinhThanhID() {
        return TinhThanhID;
    }

    public void setTinhThanhID(int tinhThanhID) {
        TinhThanhID = tinhThanhID;
    }

    public String getTinhThanhTitle() {
        return TinhThanhTitle;
    }

    public void setTinhThanhTitle(String tinhThanhTitle) {
        TinhThanhTitle = tinhThanhTitle;
    }

    public String getTinhThanhTitleAscii() {
        return TinhThanhTitleAscii;
    }

    public void setTinhThanhTitleAscii(String tinhThanhTitleAscii) {
        TinhThanhTitleAscii = tinhThanhTitleAscii;
    }

    public int getQuanHuyenID() {
        return QuanHuyenID;
    }

    public void setQuanHuyenID(int quanHuyenID) {
        QuanHuyenID = quanHuyenID;
    }

    public String getQuanHuyenTitle() {
        return QuanHuyenTitle;
    }

    public void setQuanHuyenTitle(String quanHuyenTitle) {
        QuanHuyenTitle = quanHuyenTitle;
    }

    public String getQuanHuyenTitleAscii() {
        return QuanHuyenTitleAscii;
    }

    public void setQuanHuyenTitleAscii(String quanHuyenTitleAscii) {
        QuanHuyenTitleAscii = quanHuyenTitleAscii;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getUpdated() {
        return Updated;
    }

    public void setUpdated(String updated) {
        Updated = updated;
    }
}
