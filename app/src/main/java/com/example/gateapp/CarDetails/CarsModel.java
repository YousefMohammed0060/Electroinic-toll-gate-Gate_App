package com.example.gateapp.CarDetails;
// hello
public class CarsModel {
    String CarModel, UserID ,CarLetters,CarNumbers,FullPlateNO;

    public CarsModel() {
    }

    public CarsModel(String carModel, String userID, String carLetters, String carNumbers, String fullPlateNO) {
        CarModel = carModel;
        UserID = userID;
        CarLetters = carLetters;
        CarNumbers = carNumbers;
        FullPlateNO = fullPlateNO;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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

    public String getFullPlateNO() {
        return FullPlateNO;
    }

    public void setFullPlateNO(String fullPlateNO) {
        FullPlateNO = fullPlateNO;
    }
}
