package com.example.shoptop;

public class VegModel {
    String vname;
    String vaddress;
    int vprice;
    String vquantity;
    String vpurl;
    String userid;

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setVaddress(String vaddress) {
        this.vaddress = vaddress;
    }

    public void setVprice(int vprice) {
        this.vprice = vprice;
    }

    public void setVquantity(String vquantity) {
        this.vquantity = vquantity;
    }

    public void setVpurl(String vpurl) {
        this.vpurl = vpurl;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVname() {
        return vname;
    }

    public String getVaddress() {
        return vaddress;
    }

    public int getVprice() {
        return vprice;
    }

    public String getVquantity() {
        return vquantity;
    }

    public String getVpurl() {
        return vpurl;
    }

    public String getUserid() {
        return userid;
    }
}
