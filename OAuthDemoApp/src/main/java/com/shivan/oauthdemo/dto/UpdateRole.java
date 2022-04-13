package com.shivan.oauthdemo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UpdateRole {
    private String[] schemas;
    private String displayName;
    private List<Map<String,String>> users;
    private List<String> permissions;

    public UpdateRole() {
    }

    public UpdateRole(String[] schemas, String displayName, List<Map<String, String>> users, List<String> permissions) {
        this.schemas = schemas;
        this.displayName = displayName;
        this.users = users;
        this.permissions = permissions;
    }

    public String[] getSchemas() {
        return schemas;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, String>> users) {
        this.users = users;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UpdateRole{" +
                "schemas=" + Arrays.toString(schemas) +
                ", displayName='" + displayName + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}
