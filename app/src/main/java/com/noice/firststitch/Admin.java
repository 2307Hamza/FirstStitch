package com.noice.firststitch;

public class Admin {

    private String adminID;
    private String username;
    private String phone;
    private String address;

    public Admin(String adminID, String username, String phone, String address) {
        this.adminID = adminID;
        this.username = username;
        this.phone = phone;
        this.address = address;
    }

    public Admin() {
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
