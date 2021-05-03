package com.william.Fitness.Model;

public class User {

    private String fullName;
    private String userName;
    private String email;
    private String phoneNo;
    private String password;
    private String date;
    private String gender;

    public User(String fullName, String userName, String email, String phoneNo, String password, String date, String gender) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.date = date;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
