package com.androexp.ada247;

public class UserModel {

    private String userName, userGender, userEmail, userStatus;

    public UserModel(String userName, String userGender, String userEmail, String userStatus) {
        this.userName = userName;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
