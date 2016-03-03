package com.example.client.jira;

import org.apache.commons.lang.StringUtils;

// See https://confluence.atlassian.com/jira062/advanced-searching-fields-reference-588581768.html
public enum JiraField {
    AFFECTED_VERSION("affectedVersion"),
    ASSIGNEE("assignee"),
    ATTACHMENTS("attachments"),
    CATEGORY("category"),
        COMMENT("comment"),
    COMPONENT("component"),
        CREATED("created"),
        CREATOR("creator"),
    CUSTOM_FIELD_ID("cf"),          // Safer to use custom field id; syntax: cf[fieldId],
        DESCRIPTION("description"),
        DUE("due"),
        ENVIRONMENT("environment"),
    EPIC_LINK("epic link"),
    FILTER("filter"),
    FIX_VERSION("fixVersion"),
    ISSUE_KEY("id"),                // Using alias
        LASTVIEWED("lastViewed"),
    LEVEL("level"),
    ORIGINAL_ESTIMATE("originalEstimate"),
    PARENT("parent"),
        PRIORITY("priority"),
        PROJECT("project"),
    REMAINING_ESTIMATE("remainingEstimate"),
        REPORTER("reporter"),
        RESOLUTION("resolution"),
    RESOLVED("resolved"),
    SPRINT("sprint"),
        STATUS("status"),
        SUMMARY("summary"),
    TEXT("text"),                   // 'Master' text search field
        TYPE("type"),
    TIME_SPENT("timeSpent"),
        UPDATED("updated"),
    VOTER("voter"),
//    VOTES("votes"),
//    WATCHER("watcher"),
//    WATCHERS("watchers"),
    WORK_RATIO("workRatio"),

    // Special cases for searching purposes
    ALL("*all"),
    NAVIGABLE("*navigable");

    private String fieldId;
    private JiraField(String fieldId) {
        this.fieldId = fieldId;
    }
    public String fieldId() {
        return fieldId;
    }
    public static JiraField getById(String fieldId) {
        if (StringUtils.isBlank(fieldId)) return null;
        for(JiraField jf : values()) {
            if (fieldId.toLowerCase().equals(jf.fieldId())) return jf;
        }
        throw new IllegalArgumentException(
                "No enum constant with Id " + fieldId + "!");
    }
}
