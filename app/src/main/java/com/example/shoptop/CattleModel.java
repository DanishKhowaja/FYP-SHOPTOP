package com.example.shoptop;

public class CattleModel {
    String Name;
    int Price;
    String Detail;
    String Address;
    String cpurl;
    String userid;

    public CattleModel() {

    }

    public CattleModel(String name, int price, String detail, String address, String cpurl, String userid) {
        Name = name;
        Price = price;
        Detail = detail;
        Address = address;
        this.cpurl = cpurl;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return Name;
    }

    public int getPrice() {
        return Price;
    }

    public String getDetail() {
        return Detail;
    }

    public String getAddress() {
        return Address;
    }

    public String getCpurl() {
        return cpurl;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCpurl(String cpurl) {
        this.cpurl = cpurl;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
