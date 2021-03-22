package com.example.t2shop.Model;

public class City {
    private int Type;
    private String SolrID;
    private int ID;
    private String Title;
    private int STT;
    private String Created;
    private String Updated;
    private int TotalDoanhNghiep;

    public City(int type, String solrID, int ID, String title, int STT, String created, String updated, int totalDoanhNghiep) {
        Type = type;
        SolrID = solrID;
        this.ID = ID;
        Title = title;
        this.STT = STT;
        Created = created;
        Updated = updated;
        TotalDoanhNghiep = totalDoanhNghiep;
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

    public int getTotalDoanhNghiep() {
        return TotalDoanhNghiep;
    }

    public void setTotalDoanhNghiep(int totalDoanhNghiep) {
        TotalDoanhNghiep = totalDoanhNghiep;
    }
}
