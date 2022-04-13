package com.shivan.oauthdemo.dto;

import java.util.UUID;

public class TokenDetails {
    private String access_token;
    private String type;
    private String forUserId;
    public TokenDetails() {
    }

    public TokenDetails(String access_token, String type, String forUserId) {
        this.access_token = access_token;
        this.type = type;
        this.forUserId = forUserId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    @Override
    public String toString() {
        return "TokenDetails{" +
                "access_token='" + access_token + '\'' +
                ", type='" + type + '\'' +
                ", forUserId='" + forUserId + '\'' +
                '}';
    }
}
