package com.shivan.oauthdemo.model;

import java.util.List;

public class Groups {
    private List<Members> members;
    private List<String> schemas;
    private String displayName;

    public Groups() {
    }

    public Groups(List<Members> members, List<String> schemas, String displayName) {
        this.members = members;
        this.schemas = schemas;
        this.displayName = displayName;
    }

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
