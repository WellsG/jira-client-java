package com.example.client.jira;

public class IssueUser {

    private final String name;
    private final String emailAddress;
    private final String displayName;
    private boolean active = true;

    public IssueUser(String name, String emailAddress, String displayName, boolean active) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.displayName = displayName;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
