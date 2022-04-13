package com.shivan.oauthdemo.model;

import java.util.List;

public class ShowAllUsers {
    private int totalResults;
    private List<String> schemas;
    private List<UserDataForShowAllUsers> Resources;

    public ShowAllUsers() {
    }

    @Override
    public String toString() {
        return "ShowAllUsers{" +
                "totalResults=" + totalResults +
                ", schemas=" + schemas +
                ", Resources=" + Resources +
                '}';
    }

    public ShowAllUsers(int totalResults, List<String> schemas, List<UserDataForShowAllUsers> resources) {
        this.totalResults = totalResults;
        this.schemas = schemas;
        Resources = resources;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public List<UserDataForShowAllUsers> getResources() {
        return Resources;
    }

    public void setResources(List<UserDataForShowAllUsers> resources) {
        Resources = resources;
    }
}
