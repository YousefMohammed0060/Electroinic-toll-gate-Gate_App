package com.example.gateapp.Bills;

public class billModel {
    String date,status,userID,username;
    int price;

    public billModel() {
    }

    public billModel(String date, String status, String userID, String username, int price) {
        this.date = date;
        this.status = status;
        this.userID = userID;
        this.username = username;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
