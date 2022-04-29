package com.example.gateapp.CarDetails;
// hello
public class CarsModel {
    String CarModel, UserID ,CarLetters,CarNumbers;
    public CarsModel() {
    }

    public CarsModel(String carModel, String userID, String carLetters, String carNumbers) {
        this.CarModel = carModel;
        this.UserID = userID;
        CarLetters = carLetters;
        CarNumbers = carNumbers;
    }

    public String getCarLetters() {
        return CarLetters;
    }

    public void setCarLetters(String carLetters) {
        CarLetters = carLetters;
    }

    public String getCarNumbers() {
        return CarNumbers;
    }

    public void setCarNumbers(String carNumbers) {
        CarNumbers = carNumbers;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        this.CarModel = carModel;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }
}
