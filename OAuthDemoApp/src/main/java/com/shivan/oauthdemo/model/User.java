package com.shivan.oauthdemo.model;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String[] schemas = null;


    private UUID id;

    private String userName;

    private String password;

    private Name name;


    private Emails[] emails;

    public User() {
    }

    public User(String[] schemas, UUID id, String userName, String password, Name name, Emails[] emails) {
        this.schemas = schemas;
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "User{" +
                "schemas=" + Arrays.toString(schemas) +
                ", id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name=" + name +
                ", emails=" + emails +
                '}';
    }

    public String[] getSchemas() {
        return schemas;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Emails[] getEmails() {
        return emails;
    }

    public void setEmails(Emails[] emails) {
        this.emails = emails;
    }
}

