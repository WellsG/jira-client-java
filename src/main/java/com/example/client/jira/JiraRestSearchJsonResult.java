package com.example.client.jira;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraRestSearchJsonResult {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraRestSearchJsonResult.class);

    private Integer startAt;
    private Integer maxResults;
    private Integer total;
    private List<JiraRestSearchJsonIssue> issues;

    private JiraRestSearchJsonResult() {}

    public static JiraRestSearchJsonResult create(String jsonResponse) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonResponse, JiraRestSearchJsonResult.class);
        } catch (JsonSyntaxException e) {
            LOGGER.error("Error: ", e);
            throw e;
        }
    }

    public Integer getStartAt() {
        return startAt;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public Integer getTotal() {
        return total;
    }

    public Set<Issue> getIssues() {
        Set<Issue> _issues = new HashSet<>();
        if (CollectionUtils.isNotEmpty(issues)) {
            for (JiraRestSearchJsonIssue jsonIssue : issues) {
                Issue issue = new Issue();
                issue.setId(jsonIssue.getId());
                issue.setKey(jsonIssue.getKey());
                issue.setUrl(jsonIssue.getSelf());
                issue.setProject(jsonIssue.fields.project);
                issue.setIssuetype(jsonIssue.fields.issuetype);
                issue.setReporter(jsonIssue.fields.reporter);
                issue.setCreator(jsonIssue.fields.creator);
                issue.setAssignee(jsonIssue.fields.assignee);
                issue.setPriority(jsonIssue.fields.priority);
                issue.setResolution(jsonIssue.fields.resolution);
                issue.setComments(jsonIssue.fields.comment != null ? jsonIssue.fields.comment.comments : null);
                issue.setSummary(jsonIssue.fields.summary);
                issue.setDescription(jsonIssue.fields.description);
                issue.setCreated(jsonIssue.fields.created);
                issue.setUpdated(jsonIssue.fields.updated);
                issue.setStatus(jsonIssue.fields.status);
                issue.setEnvironment(jsonIssue.fields.environment);
                issue.setLastViewed(jsonIssue.fields.lastViewed);
                issue.setResolutionDate(jsonIssue.fields.resolutiondate);
                issue.setDueDate(jsonIssue.fields.duedate);

                _issues.add(issue);
            }
        }
        return _issues;
    }

    private class JiraRestSearchJsonIssue {

        private Integer id;
        private String key;
        private String self;
        private JsonIssueFields fields;

        private Integer getId() {
            return id;
        }

        private String getKey() {
            return key;
        }

        private String getSelf() {
            return self;
        }

        private class JsonIssueFields {

            private String summary;//
            private IssueType issuetype;//
            private IssueUser reporter;
            private String updated;
            private String created;
            private Priority priority;
            private String description;//
            private Status status;
            private Project project;//
            private String environment;
            private String lastViewed;
            private JsonIssueFieldsComments comment;
            private Resolution resolution;
            private String resolutiondate;
            private IssueUser creator;
            private String duedate;
            private IssueUser assignee;//

            private class JsonIssueFieldsComments {
                private Integer total;
                private List<Comment> comments;
            }
        }
    }
}
