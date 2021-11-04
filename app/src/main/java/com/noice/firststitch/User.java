package com.noice.firststitch;

public class User {
    private String userID;
    private String username;
    private String phone;
    private String type;

    public User(String userID, String username, String phone, String type) {
        this.userID = userID;
        this.username = username;
        this.phone = phone;
        this.type = type;
    }

    public User() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
