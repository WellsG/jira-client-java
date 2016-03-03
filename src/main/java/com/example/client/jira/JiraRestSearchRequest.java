package com.example.client.jira;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class JiraRestSearchRequest {

    @Expose private String jql; // HAS TO BE A STRING!
    @Expose private Integer startAt;
    @Expose private Integer maxResults;
    @Expose private Boolean validateQuery;
    @Expose private List<String> fields;
    @Expose private List<String> expand;

    public JiraRestSearchRequest(String jql, Integer startAt, Integer maxResults, Boolean validateQuery, List<String> fields,
            List<String> expand) {
        this.jql = jql;
        this.startAt = startAt;
        this.maxResults = maxResults;
        this.validateQuery = validateQuery;
        this.fields = fields;
        this.expand = expand;
    }

    public JiraRestSearchRequest(String jql, Integer startAt, Integer maxResults, List<String> fields, List<String> expand) {
        this(jql, startAt, maxResults, null, fields, expand);
    }

    public JiraRestSearchRequest(String jql, Integer startAt, Integer maxResults, List<String> fields) {
        this(jql, startAt, maxResults, fields, null);
    }

    public JiraRestSearchRequest(String jql, Integer startAt, List<String> fields) {
        this(jql, startAt, null, fields);
    }

    public JiraRestSearchRequest(String jql, List<String> fields) {
        this(jql, null, fields);
    }

    public JiraRestSearchRequest(String jql) {
        this(jql, null);
    }

    public String getEncodedRestSearchString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public static void main(String[] args) {
        List<String> fields = new ArrayList<>();
        fields.add(JiraField.ASSIGNEE.fieldId());
        fields.add(JiraField.DESCRIPTION.fieldId());
        JiraRestSearchRequest jrsr = new JiraRestSearchRequest("id in (JTP-1, JTP-3)", fields);
        String json = jrsr.getEncodedRestSearchString();
        System.out.println("JSON: " + json);
    }
}
