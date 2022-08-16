package com.example.shoptop;

public class RegisterUser {

    public String FName, LName, Address, Email, ContactNumber;

    public RegisterUser()
    {

    }

    public RegisterUser(String FName, String LName, String address, String email, String contactNumber) {
        this.FName = FName;
        this.LName = LName;
        Address = address;
        Email = email;
        ContactNumber = contactNumber;
    }
}
