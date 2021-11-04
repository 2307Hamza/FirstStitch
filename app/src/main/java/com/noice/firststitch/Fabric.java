package com.noice.firststitch;

public class Fabric {
    private String fabricID;
    private String imageURL;
    private String name;
    private String type;
    private String color;
    private String cost;
    private String description;
    private boolean isSeasonal;
    private boolean isLatest;

    public Fabric(String fabricID, String imageURL, String name, String type, String color, String cost, String description, boolean isSeasonal, boolean isLatest) {
        this.fabricID = fabricID;
        this.imageURL = imageURL;
        this.name = name;
        this.type = type;
        this.color = color;
        this.cost = cost;
        this.description = description;
        this.isSeasonal = isSeasonal;
        this.isLatest = isLatest;
    }

    public Fabric() {
    }

    public String getFabricID() {
        return fabricID;
    }

    public void setFabricID(String fabricID) {
        this.fabricID = fabricID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isSeasonal() {
        return isSeasonal;
    }

    public void setSeasonal(boolean seasonal) {
        isSeasonal = seasonal;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setLatest(boolean latest) {
        isLatest = latest;
    }
}
