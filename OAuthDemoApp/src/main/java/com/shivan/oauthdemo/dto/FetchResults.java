package com.shivan.oauthdemo.dto;

import com.shivan.oauthdemo.model.Meta;

public class FetchResults {

    private Meta meta;
    private String id;
    private String userName;

    public FetchResults() {
    }

    public FetchResults(Meta meta, String id, String userName) {
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

    @Override
    public String toString() {
        return "FetchResults{" +
                "meta=" + meta +
                ", id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
