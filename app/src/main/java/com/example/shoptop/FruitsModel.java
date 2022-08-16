package com.example.shoptop;

public class FruitsModel {
    String name;
    String address;
    int Price;
    String quantity;
    String purl;
    String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return Price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPurl() {
        return purl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
