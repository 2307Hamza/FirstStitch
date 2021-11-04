package com.noice.firststitch;

public class Customer {

    private String customerID;
    private String username;
    private String phone;
    private String address;

    public Customer(String customerID, String username, String phone, String address) {
        this.customerID = customerID;
        this.username = username;
        this.phone = phone;
        this.address = address;
    }

    public Customer() {
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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
