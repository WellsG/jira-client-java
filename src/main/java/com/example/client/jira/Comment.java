package com.example.client.jira;

public class Comment {

    private String self;

    private IssueUser author;

    private IssueUser updateAuthor;

    private String body;

    private String created;

    private String updated;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public IssueUser getAuthor() {
        return author;
    }

    public void setAuthor(IssueUser author) {
        this.author = author;
    }

    public IssueUser getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(IssueUser updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
}
