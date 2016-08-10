package com.example.client.jira;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Issue implements Serializable {
    private Integer id;
    private String key;
    private String url;
    private Project project;
    private List<Component> components;
    private IssueType issuetype;
    private IssueUser reporter;
    private IssueUser creator;
    private IssueUser assignee;
    private Priority priority;
    private Resolution resolution;
    private List<Comment> comments;

    private String summary;
    private String description;
    private String created;
    private String updated;
    private Status status;
    private String environment;
    private String lastViewed;
    private String resolutionDate;
    private String dueDate;
    private Issue parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public IssueType getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    public IssueUser getReporter() {
        return reporter;
    }

    public void setReporter(IssueUser reporter) {
        this.reporter = reporter;
    }

    public IssueUser getCreator() {
        return creator;
    }

    public void setCreator(IssueUser creator) {
        this.creator = creator;
    }

    public IssueUser getAssignee() {
        return assignee;
    }

    public void setAssignee(IssueUser assignee) {
        this.assignee = assignee;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(String lastViewed) {
        this.lastViewed = lastViewed;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Issue getParent() {
		return parent;
	}

	public void setParent(Issue parent) {
		this.parent = parent;
	}

}
