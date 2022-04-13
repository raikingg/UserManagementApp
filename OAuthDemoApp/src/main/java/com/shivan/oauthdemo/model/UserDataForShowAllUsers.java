package com.shivan.oauthdemo.model;

public class UserDataForShowAllUsers {
    private Meta meta;
    private String id;
    private String userName;

    public UserDataForShowAllUsers() {
    }

    public UserDataForShowAllUsers(Meta meta, String id, String userName) {
        this.meta = meta;
        this.id = id;
        this.userName = userName;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
