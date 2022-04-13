package com.shivan.oauthdemo.model;

import java.util.Date;

public class Meta {
    private String location;
    private Date createdOn;
    private Date modifiedOn;

    public Meta() {
    }

    public Meta(String location, Date createdOn, Date modifiedOn) {
        this.location = location;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
