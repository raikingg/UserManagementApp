package com.shivan.oauthdemo.model;

public class Members {
    private String display;
    private String value;

    public Members() {
    }

    public Members(String display, String value) {
        this.display = display;
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
