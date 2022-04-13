package com.shivan.oauthdemo.dto;

public class LoginRequest {
    private String clientId;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private String clientSecret;
    private String scope;

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public LoginRequest(String clientId, String userName, String password, String phoneNumber, String email, String clientSecret) {
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.clientSecret = clientSecret;
    }

    public LoginRequest() {
    }

    public LoginRequest(String clientId, String userName, String password, String phoneNumber, String email) {
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
