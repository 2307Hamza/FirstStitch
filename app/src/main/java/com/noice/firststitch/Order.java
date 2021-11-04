package com.noice.firststitch;

public class Order {
    private String orderID;
    //private String customerID;
    //private String tailorID;
    //private String fabricID;
    private Customer customer;
    private Tailor tailor;
    private Fabric fabric;
    private suiteDimensions dimensions;
    private String cost;
    private String status;

    public Order(String orderID, Customer customer, Tailor tailor, Fabric fabric, suiteDimensions dimensions, String cost, String status) {
        this.orderID = orderID;
        this.customer = customer;
        this.tailor = tailor;
        this.fabric = fabric;
        this.dimensions = dimensions;
        this.cost = cost;
        this.status = status;
    }

    public Order() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /*public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getTailorID() {
        return tailorID;
    }

    public void setTailorID(String tailorID) {
        this.tailorID = tailorID;
    }

    public String getFabricID() {
        return fabricID;
    }

    public void setFabricID(String fabricID) {
        this.fabricID = fabricID;
    }*/

    public suiteDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(suiteDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Tailor getTailor() {
        return tailor;
    }

    public void setTailor(Tailor tailor) {
        this.tailor = tailor;
    }

    public Fabric getFabric() {
        return fabric;
    }

    public void setFabric(Fabric fabric) {
        this.fabric = fabric;
    }
}
