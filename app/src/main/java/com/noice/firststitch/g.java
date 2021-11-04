package com.noice.firststitch;

import android.app.Application;

public class g extends Application {

    private static String userID;
    private static String userType;
    private static String customerID;
    private static String tailorID;
    private static String adminID;
    private static String fabricID;
    private static Admin admin;
    private static Tailor tailor;
    private static Customer customer;
    private static Fabric fabric;
    private static int rvSize;
    private static int rvCurrentIndex;
    private static boolean isEditing;
    private static boolean isComingFromAllFabric;
    private static String category;
    private static suiteDimensions suiteDimensions;
    private static Order order;
    private static String orderFragmentType;
    private static boolean isGoingToCompleted;
    private static boolean isGoingToPending;
    private static int themeCode;

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        g.userID = userID;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        g.userType = userType;
    }

    public static String getCustomerID() {
        return customerID;
    }

    public static void setCustomerID(String customerID) {
        g.customerID = customerID;
    }

    public static String getTailorID() {
        return tailorID;
    }

    public static void setTailorID(String tailorID) {
        g.tailorID = tailorID;
    }

    public static String getAdminID() {
        return adminID;
    }

    public static void setAdminID(String adminID) {
        g.adminID = adminID;
    }

    public static String getFabricID() {
        return fabricID;
    }

    public static void setFabricID(String fabricID) {
        g.fabricID = fabricID;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        g.admin = admin;
    }

    public static Tailor getTailor() {
        return tailor;
    }

    public static void setTailor(Tailor tailor) {
        g.tailor = tailor;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        g.customer = customer;
    }

    public static Fabric getFabric() {
        return fabric;
    }

    public static void setFabric(Fabric fabric) {
        g.fabric = fabric;
    }

    public static boolean isIsEditing() {
        return isEditing;
    }

    public static void setIsEditing(boolean isEditing) {
        g.isEditing = isEditing;
    }

    public static int getRvSize() {
        return rvSize;
    }

    public static void setRvSize(int rvSize) {
        g.rvSize = rvSize;
    }

    public static int getRvCurrentIndex() {
        return rvCurrentIndex;
    }

    public static void setRvCurrentIndex(int rvCurrentIndex) {
        g.rvCurrentIndex = rvCurrentIndex;
    }

    public static boolean isIsComingFromAllFabric() {
        return isComingFromAllFabric;
    }

    public static void setIsComingFromAllFabric(boolean isComingFromAllFabric) {
        g.isComingFromAllFabric = isComingFromAllFabric;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        g.category = category;
    }

    public static com.noice.firststitch.suiteDimensions getSuiteDimensions() {
        return suiteDimensions;
    }

    public static void setSuiteDimensions(com.noice.firststitch.suiteDimensions suiteDimensions) {
        g.suiteDimensions = suiteDimensions;
    }

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        g.order = order;
    }

    public static String getOrderFragmentType() {
        return orderFragmentType;
    }

    public static void setOrderFragmentType(String orderFragmentType) {
        g.orderFragmentType = orderFragmentType;
    }

    public static boolean isIsGoingToCompleted() {
        return isGoingToCompleted;
    }

    public static void setIsGoingToCompleted(boolean isGoingToCompleted) {
        g.isGoingToCompleted = isGoingToCompleted;
    }

    public static boolean isIsGoingToPending() {
        return isGoingToPending;
    }

    public static void setIsGoingToPending(boolean isGoingToPending) {
        g.isGoingToPending = isGoingToPending;
    }

    public static int getThemeCode() {
        return themeCode;
    }

    public static void setThemeCode(int themeCode) {
        g.themeCode = themeCode;
    }
}
