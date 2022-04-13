package com.shivan.oauthdemo.dto;

import com.shivan.oauthdemo.model.Members;

import java.util.Arrays;
import java.util.List;

public class CreateGroupPayload {
    private String[] schemas;
    private String displayName;
    private List<Members> members;

    public CreateGroupPayload() {
    }

    public CreateGroupPayload(String[] schemas, String displayName, List<Members> members) {
        this.schemas = schemas;
        this.displayName = displayName;
        this.members = members;
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

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "CreateGroupPayload{" +
                "schemas=" + Arrays.toString(schemas) +
                ", displayName='" + displayName + '\'' +
                ", members=" + members +
                '}';
    }
}
