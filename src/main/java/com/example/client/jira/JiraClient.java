package com.example.client.jira;

import java.util.List;
import java.util.Set;

public interface JiraClient {

    public Issue createIssue(Issue issue) throws Exception;

    public void assignIssue(String issueId, String assignee) throws Exception;

    public void closeIssue(String issueId) throws Exception;

    public Set<Issue> getIssuesByIds(List<String> ids, List<JiraField> fields) throws Exception;

}
