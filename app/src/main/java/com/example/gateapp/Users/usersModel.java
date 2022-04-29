package com.example.gateapp.Users;

public class usersModel {
    String city, nationalID, phone, profileImage, username, userId, email,password;

    public usersModel() {
    }

    public usersModel(String city, String nationalID, String phone, String profileImage, String username, String userId, String email, String password) {
        this.city = city;
        this.nationalID = nationalID;
        this.phone = phone;
        this.profileImage = profileImage;
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}