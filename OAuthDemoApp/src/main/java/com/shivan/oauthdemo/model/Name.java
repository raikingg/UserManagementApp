package com.shivan.oauthdemo.model;

public class Name {

    private String familyName;

    private String givenName;

    public Name() {
    }

    public Name(String familyName, String givenName) {
        this.familyName = familyName;
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public String toString() {
        return "Name{" +
                "familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                '}';
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
