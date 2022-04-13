package com.shivan.oauthdemo.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Roles {
    private String displayName;
    private List<String> permissions;
    private List<String> schemas;
    private UUID id;
    private List<Map<String,String>> users;

    public Roles() {
    }

    public Roles(String displayName, List<String> permissions, List<String> schemas, UUID id, List<Map<String,String>> users) {
        this.displayName = displayName;
        this.permissions = permissions;
        this.schemas = schemas;
        this.id = id;
        this.users = users;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, String>> users) {
        this.users = users;
    }
}
