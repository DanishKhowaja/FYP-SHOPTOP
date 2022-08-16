package com.example.shoptop;

public class CartModel {

    int Price;
    String name;
    int Count;
    String purl, userid;

    public CartModel() {
    }

    public CartModel(int price,  String name, int count, String purl, String userid) {
        Price = price;
        this.name = name;
        this.Count=count;
        this.purl =purl;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
