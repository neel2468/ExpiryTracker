package com.example.expirytracker;

public class Products {
    private  String name="";
    private  String expiry_date="";

    public Products(String name,String expiry_date) {
        this.name = name;
      this.expiry_date = expiry_date;
    }

    public String getDate() {
        return expiry_date;
    }

    public String getName() {
        return name;
    }
}
