package com.shivan.oauthdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Emails {

    private boolean primary;

    @JsonProperty
    private String value;
    @JsonProperty
    private String type;


    public Emails(boolean primary, String value, String type) {
        this.primary = primary;
        this.value = value;
        this.type = type;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Emails() {
    }
}
