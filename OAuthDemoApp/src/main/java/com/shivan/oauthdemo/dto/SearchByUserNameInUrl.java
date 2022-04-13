package com.shivan.oauthdemo.dto;

import java.util.Arrays;
import java.util.List;

public class SearchByUserNameInUrl {

    private String totalResults;
    private String[] schemas;
    private List<Object> Resources;

    public SearchByUserNameInUrl() {
    }

    public SearchByUserNameInUrl(String totalResults, String[] schemas, List<Object> resources) {
        this.totalResults = totalResults;
        this.schemas = schemas;
        Resources = resources;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String[] getSchemas() {
        return schemas;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas;
    }

    public List<Object> getResources() {
        return Resources;
    }

    public void setResources(List<Object> resources) {
        Resources = resources;
    }

    @Override
    public String toString() {
        return "SearchByUserNameInUrl{" +
                "totalResults='" + totalResults + '\'' +
                ", schemas=" + Arrays.toString(schemas) +
                ", Resources=" + Resources +
                '}';
    }
}
