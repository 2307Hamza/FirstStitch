package com.noice.firststitch;

public class Tailor {

    //members
    private String tailorID;
    private String username;
    private String phone;
    private String address;
    private int speciality;  //0 none, 1 ladies, 2 gents, 3 kids, 4 ladies gents, 5 ladies kids, 6 gents kids, 7 ladies gents kids

    //constructors
    public Tailor(String tailorID, String username, String phone, String address, int speciality) {
        this.tailorID = tailorID;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.speciality = speciality;
    }

    public Tailor() {
    }

    //getters and setters
    public String getTailorID() {
        return tailorID;
    }

    public void setTailorID(String tailorID) {
        this.tailorID = tailorID;
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

    public int getSpeciality() {
        return speciality;
    }

    public void setSpeciality(int speciality) {
        this.speciality = speciality;
    }
}
