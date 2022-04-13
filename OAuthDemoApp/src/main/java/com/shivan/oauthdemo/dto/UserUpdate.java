package com.shivan.oauthdemo.dto;

import com.shivan.oauthdemo.model.Emails;
import com.shivan.oauthdemo.model.Name;

import java.util.Arrays;
import java.util.List;

public class UserUpdate {
    private String[] schemas;

    private Name name;

    private String userName;

    private List<Emails> emails;

    public UserUpdate() {
    }

    public UserUpdate(String[] schemas, Name name, String userName, List<Emails> emails) {
        this.schemas = schemas;
        this.name = name;
        this.userName = userName;
        this.emails = emails;
    }

    public String[] getSchemas() {
        return schemas;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Emails> getEmails() {
        return emails;
    }

    public void setEmails(List<Emails> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "UserUpdate{" +
                "schemas=" + Arrays.toString(schemas) +
                ", name=" + name +
                ", username='" + userName + '\'' +
                ", emails=" + emails +
                '}';
    }
}
