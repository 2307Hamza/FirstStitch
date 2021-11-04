package com.noice.firststitch;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class suiteDimensions {
    private String arm;
    private String shoulder;
    private String chest;
    private String shirt;
    private String waist;
    private String leg;
    private String instructions;
    private String type;
    private boolean pocket;
    private boolean vneck;

    public suiteDimensions(String arm, String shoulder, String chest, String shirt, String waist, String leg, String instructions, String type, boolean pocket, boolean vneck) {
        this.arm = arm;
        this.shoulder = shoulder;
        this.chest = chest;
        this.shirt = shirt;
        this.waist = waist;
        this.leg = leg;
        this.instructions = instructions;
        this.type = type;
        this.pocket = pocket;
        this.vneck = vneck;
    }

    public suiteDimensions() {
    }

    public String getArm() {
        return arm;
    }

    public void setArm(String arm) {
        this.arm = arm;
    }

    public String getShoulder() {
        return shoulder;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getShirt() {
        return shirt;
    }

    public void setShirt(String shirt) {
        this.shirt = shirt;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPocket() {
        return pocket;
    }

    public void setPocket(boolean pocket) {
        this.pocket = pocket;
    }

    public boolean isVneck() {
        return vneck;
    }

    public void setVneck(boolean vneck) {
        this.vneck = vneck;
    }
}
